package com.pi.mybatis.page.model;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;

public class BoundSqlSource implements SqlSource{
  private BoundSql sql;
  public BoundSqlSource(BoundSql sql){
    this.sql = sql;
  }

  @Override
  public BoundSql getBoundSql(Object parameterObject) {
    return sql;
  }
}
