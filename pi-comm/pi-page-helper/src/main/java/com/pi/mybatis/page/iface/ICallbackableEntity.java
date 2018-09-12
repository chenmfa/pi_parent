package com.pi.mybatis.page.iface;

public interface ICallbackableEntity<T, R> {
  public R doGetRequestEntity(R r);
}
