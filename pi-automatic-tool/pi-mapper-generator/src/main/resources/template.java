package ${mapperPackage};

import java.util.List;

import ${fullEntityClassName};
import ${fullParamClassName};

/**
 * 表${tableName}的mapper类
 *
 */
public interface ${mapperClassName}{
	/**
	 * 根据id，查找对应的记录，没找着返回Null
	 */
	public ${entityClassName} findOne(Long id);
	
	/**
	 * 根据id，更新对应实体；
	 * 其中id为entity中的id，被更新的内容为entity中其他所有非空的内容；
	 * （id, create_date, update_date, version会被忽略）
	 * @return 返回更新条数
	 */
	public int updateById(${entityClassName} entity);
	
	/**
	 * 插入一条实体，注意：
	 * 除了id, create_date, update_date, version外，所有的值都会从entity中取，即使这个值是Null，
	 * 因此，如果有默认值，请注意设置默认值。
	 * 
	 * 插入成功后，记录的id会写入entity的id属性中。
	 * 插入成功后，返回插入的条数
	 *
	 */
	public int insertEntity(${entityClassName} entity);
	
	/**
	 * 根据查询条件，查询所有的符合条件的记录。
	 * 查询对象中所有的非空字段都会作为查询条件的一部分
	 *
	 */
	public List<${entityClassName}> findByParam(${paramClassName} entity);

}