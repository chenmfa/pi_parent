package com.pi.comm.mapper;

import java.io.Serializable;
import java.util.List;

import com.pi.comm.query.MapperQuery;

public interface BaseMapper<T extends Serializable, ID extends Serializable> {
  /**
   * @description插入一条实体，注意：
   * @description除了id, create_date, update_date, version外，所有的值都会从entity中取，即使这个值是Null，
   * @description因此，如果有默认值，请注意设置默认值。
   * @description插入成功后，记录的id会写入entity的id属性中。
   * @description插入成功后，返回插入的条数
   *
   */
    long insert(T entity);
    /**
     * @description根据id，更新对应实体；
     * @description其中id为entity中的id，被更新的内容为entity中其他所有非空的内容；
     * @description（id, create_date, update_date, version会被忽略）
     * @return 返回更新条数
     */
    int updateById(T entity);
    /**
     * @description 根据id，查找对应的记录,没找着返回Null
     */
    T findOne(ID id);
    /**
     * @description 根据查询条件，查询所有的符合条件的记录。
     * @description 查询对象中所有的非空字段都会作为查询条件的一部分
     * @return 返回最大1000条记录集
     */
    List<T> findAll();
    /**
     * @description 统计总记录数
     */
    long count();
    
    /**
     * @description 根据条件查询记录总数
     * @param mapperQuery 查询条件
     */
    long countList(MapperQuery mapperQuery);
    
    /**
     * @description 根据id列表查询结果集
     */
    List<T> getByIds(Iterable<ID> ids);
    
    /**
     * @description 根据条件查询结果集
     * @param mapperQuery 查询条件
     */
    List<T> findList(MapperQuery mapperQuery);
    
    /**
     * @description 根据id删除数据(硬删除,对于非重要数据)
     */
    int delete(ID id);

    /**
     * @description 根据id列表删除数据(硬删除,对于非重要数据)
     */
    int deleteByIds(Iterable<ID> ids);

}
