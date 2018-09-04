package com.pi.comm.mapper;

import java.io.Serializable;
import java.util.List;

import com.pi.comm.query.MapperQuery;

public interface BaseMapper<T extends Serializable, ID extends Serializable> {

    long insert(T entity);

    int update(T entity);

    T findOne(ID id);

    List<T> findAll();

    long count();

    List<T> getByIds(Iterable<ID> ids);

    List<T> findList(MapperQuery mapperQuery);

    long countList(MapperQuery mapperQuery);

    int delete(ID id);

    int deleteByIds(Iterable<ID> ids);

}
