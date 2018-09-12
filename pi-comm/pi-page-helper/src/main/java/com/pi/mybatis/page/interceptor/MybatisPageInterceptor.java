package com.pi.mybatis.page.interceptor;

import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi.mybatis.page.iface.IPageInfo;

@Intercepts(value = {
    @Signature(type=Executor.class, method="query", 
        args={MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class MybatisPageInterceptor implements Interceptor{
  private static final Logger logger = LoggerFactory.getLogger(MybatisPageInterceptor.class);
  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    Object[] args = invocation.getArgs();// 方法参数
    Method method = invocation.getMethod();//拦截方法
    Object target = invocation.getTarget();//拦截原始类
    return null;
  }

  @Override
  public Object plugin(Object target) {
    return Plugin.wrap(target, this);
  }

  @Override
  public void setProperties(Properties properties) {
//    do nothing
  }
  
  private IPageInfo wrapParamToPageInfo(Object args){
    return null;
  }

}
