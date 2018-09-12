package com.pi.mybatis.page.interceptor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi.mybatis.page.iface.IPageableCallback;
import com.pi.mybatis.page.iface.IPageInfo;
import com.pi.mybatis.page.model.BoundSqlSource;
import com.pi.mybatis.page.model.PageResult;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectVisitorAdapter;

@Intercepts(value = {
    @Signature(type=Executor.class, method="query", 
        args={MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class MybatisPageInterceptor implements Interceptor{
  private static final Logger logger = LoggerFactory.getLogger(MybatisPageInterceptor.class);
  private static final int DEFAULT_PAGE_NUMBER = 1, DEFAULT_PAGE_SIZE =1;
  private static final Pattern FROM_PATTERN = Pattern.compile("\\sfrom\\s", Pattern.CASE_INSENSITIVE);
  private static final Pattern LIMIT_PATTERN = Pattern.compile("\\slimit\\s", Pattern.CASE_INSENSITIVE);
  
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public Object intercept(Invocation invocation) throws Throwable {
 // 方法参数, 通过上面的@Signature参数预先定义了入参有四个顺序为MappedStatement, Object(原始参数), RowBounds, ResultHandler
//    Method method = invocation.getMethod();//拦截方法
//    Object target = invocation.getTarget();//拦截原始类
    Object[] args = invocation.getArgs();
    if(null == args || null == args[1]){
      return invocation.proceed();
    }
//    尝试获取代理对象IPageInfo, 如果没有, 则尝试
    IPageInfo pageInfo = wrapParamToPageInfo(args[1]);
    if(null == pageInfo){
      return invocation.proceed();
    }
//    获取页码与也大小
    Integer pageNumber = pageInfo.getPageNumber();
    Integer pageSize = pageInfo.getPageSize();
    if(null == pageNumber){
      pageNumber = DEFAULT_PAGE_NUMBER;
    }
    if(null == pageSize){
      pageSize = DEFAULT_PAGE_SIZE;
    }
    //获取拦截的对象和参数
    Executor executor = (Executor) invocation.getTarget();
    MappedStatement statement = (MappedStatement) invocation.getArgs()[0];
    RowBounds rowBounds = (RowBounds) invocation.getArgs()[2];
    ResultHandler<?> rsHandler = (ResultHandler<?>) invocation.getArgs()[3];
    //获取总条数
    int count = getTotalCount(args[1], executor, statement, rowBounds, rsHandler);
    List<?> rows = getRequestedPage(args[1], executor, statement, rowBounds, 
        rsHandler, pageNumber, pageSize);
    
  //获取拦截的返回对象(没有则返回标准对象)
    IPageableCallback callback = wrapParamToPageCallback(args[1]);
    if(null == callback || null == callback.getCallbackableEntity()){
      return new PageResult(pageNumber, pageSize, count, rows);
    }
    List<Object> newCallBackList = new ArrayList();
    if(null != rows && !rows.isEmpty()){
      for(Object obj:rows){
        newCallBackList.add(callback.getCallbackableEntity().doGetRequestEntity(obj));
      }
    }
    return new PageResult(pageNumber, pageSize, count, newCallBackList);
  }

  @Override
  public Object plugin(Object target) {
    return Plugin.wrap(target, this);
  }

  @Override
  public void setProperties(Properties properties) {
//    do nothing
  }
  
  private IPageInfo wrapParamToPageInfo(Object param){
    return wrapParamToInstance(param, IPageInfo.class);
  }
  private IPageableCallback<?, ?> wrapParamToPageCallback(Object param){
    return wrapParamToInstance(param, IPageableCallback.class);
  }
  /**
   * @description 判断原始参数中是否可以提取类对象tClz
   * @param param 原始参数
   * @param tClz 类对象
   * {@link com.pi.mybatis.page.AssignableTest#main}
   * @return tClz的实例对象
   */
  @SuppressWarnings("unchecked")
  private <T>T wrapParamToInstance(Object param, Class<T> tClz){
    if(null == param || null == tClz){
      return null;
    }
    //instanceOf 只能用于isAssignableFrom
    if(tClz.isAssignableFrom(param.getClass())){
      return (T)param;
    }
    Iterator<?> it = null;
    if(param instanceof Map){
      it = ((Map<?, ?>)param).values().iterator();
    }else if(Iterable.class.isAssignableFrom(param.getClass())){
      it = ((Iterable<?>)param).iterator();
    }
    T promisedInstance = searchInstanceFormIterable(it, tClz);
    return null != promisedInstance?promisedInstance:null;
  }
  
  /**
   * @description 递归获取迭代器内元素, 判断是否包含目标对象
   * @param it 迭代器对象
   * @param tClz
   * @return
   */
  private <T>T  searchInstanceFormIterable(Iterator<?> it, Class<T> tClz){
    if(null == it || null == tClz){
      return null;
    }
    while(it.hasNext()){
      Object next = it.next();
      if(next == null){
        continue;
      }
      T promisedInstance = wrapParamToInstance(next, tClz);
      if(null != promisedInstance){
        return promisedInstance;
      }
    }
    return null;
  }
  
  private int getTotalCount(Object param, Executor target, MappedStatement statement, RowBounds rowBounds,
      ResultHandler<?> rsHandler) throws SQLException {
    BoundSqlSource boundSqlSqlSource = getBoundSqlSqlSource(statement, param);
    if(null == boundSqlSqlSource){      
      return 0;
    }
    MappedStatement clonedMappedStatement = getClonedMappedStatement(statement, Long.class);
    CacheKey cacheKey = target.createCacheKey(
        clonedMappedStatement, param, rowBounds, 
        boundSqlSqlSource.getBoundSql(param));
    List<Long> result = target.query(
        clonedMappedStatement, param, rowBounds, 
        rsHandler, cacheKey, boundSqlSqlSource.getBoundSql(param));
    return (null == result || result.isEmpty())?0:(result.size() > 1?result.size():result.get(0).intValue());
  }
  
  private List<?> getRequestedPage(
      Object param, Executor target, MappedStatement statement, RowBounds rowBounds,
      ResultHandler<?> rsHandler, int pageNumber, int pageSize) throws SQLException {
    BoundSql boundSql = statement.getBoundSql(param);
    String sql = boundSql.getSql();
    Matcher matcherLimit = LIMIT_PATTERN.matcher(sql);
    int offset = (pageNumber - 1) * pageSize;
    String requestedPageSql = sql + (!matcherLimit.find()?" limit " + offset + "  , " + pageSize:"");
//    Mybatis 自带的反射注入类，修改sql
    MetaObject boundSqlObject = SystemMetaObject.forObject(boundSql);
    boundSqlObject.setValue("sql", requestedPageSql);

    CacheKey cacheKey = target.createCacheKey(statement, param, rowBounds, boundSql);
    return target.query(statement, param, rowBounds, rsHandler, cacheKey, boundSql);
  }
  
  private MappedStatement getClonedMappedStatement(MappedStatement statement, Class<?> resultClass){
    if(logger.isDebugEnabled()){
      
    }
    MappedStatement.Builder statementBuilder = new MappedStatement.Builder(
        statement.getConfiguration(), statement.getId(), 
        statement.getSqlSource(), statement.getSqlCommandType());
    //生成Statement的原始ResultMap集合
    ResultMap.Builder resultMapBuilder = new ResultMap.Builder(
        statement.getConfiguration(), statement.getId(), 
        resultClass, new ArrayList<ResultMapping>());
    //将克隆的ResultMap添加到集合中
    List<ResultMap> resultMaps = new ArrayList<>();
    resultMaps.add(resultMapBuilder.build());
    
    statementBuilder.resource(statement.getResource())
    .parameterMap(statement.getParameterMap())
    .resultMaps(resultMaps)
    .fetchSize(statement.getFetchSize())
    .timeout(statement.getTimeout())
    .statementType(statement.getStatementType())
    .resultSetType(statement.getResultSetType())
    .cache(statement.getCache())
    .flushCacheRequired(statement.isFlushCacheRequired())
    .useCache(statement.isUseCache())
    .resultOrdered(statement.isResultOrdered())
    .keyGenerator(statement.getKeyGenerator())
    .keyProperty(Arrays.toString(statement.getKeyProperties()))
    .keyColumn(Arrays.toString(statement.getKeyColumns()))//Mark-Source
    .databaseId(statement.getDatabaseId())
    .lang(statement.getLang())
    .resultSets(Arrays.toString(statement.getResultSets()));
    
    return statementBuilder.build();
  }
  /**
   * @description 如果需要对sql做特殊的处理, 可以通过修改boundSql来实现
   * @description 这里用来重新生成统计的sql
   */
  private BoundSqlSource getBoundSqlSqlSource(MappedStatement statement, Object param){
    BoundSql origin = statement.getBoundSql(param);
    String sql = origin.getSql();
    Matcher matcherFrom = FROM_PATTERN.matcher(sql);
    if (!matcherFrom.find()) {
      logger.error("没有找到语句中的FROM关键字, 分页失败, 原始sql:{}", sql);
      return null;
    }

    //封装统计的sql
    String statisticalSql = null;
    try{
      Select selectSql = (Select)CCJSqlParserUtil.parse(sql);
      SelectBody selectBody = selectSql.getSelectBody();
      selectBody.accept(new SelectVisitorAdapter(){
        @Override
        public void visit(PlainSelect plainSelect) {
          //默认sql的查询是count(1)
          List<Expression> sqlColumnList = new ArrayList<>();
          sqlColumnList.add(new Column("1"));
          //添加count()函数
          Function functionCount = new Function();
          functionCount.setName("COUNT");
          functionCount.setParameters(new ExpressionList(sqlColumnList));
          
          List<SelectItem> selectItems = new ArrayList<>();
          selectItems.add(new SelectExpressionItem(functionCount));
          plainSelect.setSelectItems(selectItems);//用set这个方法相对于add少了判断与初始化和数组集合的转换
          
          List<OrderByElement> orderByElements = plainSelect.getOrderByElements();
          if(null != orderByElements){
            boolean hasPreparedOrderBy = false;
            for(OrderByElement element: orderByElements){
              if(null != element.getExpression() 
                  && element.getExpression().toString().contains("?")){
                hasPreparedOrderBy = true;
                break;
              }
            }
            if(!hasPreparedOrderBy){
              plainSelect.setOrderByElements(null);
            }
          }
          //去除limit条件
          plainSelect.setLimit(null);
        }
      });
      statisticalSql = selectBody.toString();
    }catch(Exception e){
      logger.error("拼接统计总数Sql失败", e);
      Matcher matcherLimit = LIMIT_PATTERN.matcher(sql);
      statisticalSql = "SELECT count(1) " 
          + sql.substring(matcherFrom.start(),
          !matcherLimit.find()?sql.length():matcherLimit.start()) + ";";
    }
    //封装统计的sql结束
    BoundSql newBoundSql = new BoundSql(
        statement.getConfiguration(), statisticalSql, origin.getParameterMappings(), param);
    for (ParameterMapping mapping : origin.getParameterMappings()) {
      String prop = mapping.getProperty();
      if (origin.hasAdditionalParameter(prop)) {
        newBoundSql.setAdditionalParameter(prop, origin.getAdditionalParameter(prop));
      }
    }
    return new BoundSqlSource(newBoundSql);
  }
}
