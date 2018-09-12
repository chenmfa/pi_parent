package com.pi.mybatis.page.iface;

public interface IPageableCallback<T, R>{
  public ICallbackableEntity<T, R> getCallbackableEntity();
}
