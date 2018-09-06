package com.pi.uc.model.wx;

public class WxAuthInfo {
  private WxUserBaseInfo baseInfo;
  private WxUserInfo userInfo;
  public WxUserBaseInfo getBaseInfo() {
    return baseInfo;
  }
  public void setBaseInfo(WxUserBaseInfo baseInfo) {
    this.baseInfo = baseInfo;
  }
  public WxUserInfo getUserInfo() {
    return userInfo;
  }
  public void setUserInfo(WxUserInfo userInfo) {
    this.userInfo = userInfo;
  }
}
