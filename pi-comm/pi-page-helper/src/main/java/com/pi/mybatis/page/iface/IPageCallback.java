package com.pi.mybatis.page.iface;

public interface IPageCallback<T, R>{
  public IEntityCallback<T, R> getCallback();
}
