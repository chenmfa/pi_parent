package com.pi.base.enumerate.redis;

public enum RedisCacheEnum {
  SIMPLE_PARTNER_CONFIG("pub_partner_conifg_{0}_{1}", 300, "合作商配置缓存"),//0. sourceId 1. configName
  USER_WX_INFO("user_wx_info_{0}", 3600, "用户微信登录后的微信信息缓存"),//0. openId
  USER_LOGIN_TOKEN("user_login_token_{0}", 7200, "用户微信登录后的信息缓存"),//0. userId
  USER_LOGIN_SESSION("user_login_session_{0}", 7200, "用户微信登录后的信息缓存"),//0. token
  STROOP_STAGE_DATA("STROOP_STAGE_DATA:{0}", 300, "用户阶段性测试数据"),
  STROOP_STAGE_RESULT("STROOP_STAGE_RESULT:{0}", 300, "用户阶段性测试结果数据"),
  WX_ACCESS_TOKEN("WX_ACCESS_{0}", 7200, "微信小程序的accesstoken")
  ;
  private String key;
  private int expired;
  private String desc;
  private RedisCacheEnum(String key, int expired, String desc){
    this.key = key;
    this.expired = expired;
    this.desc = desc;
  }
  public String getKey() {
    return key;
  }
  public int getExpired() {
    return expired;
  }
  public String getDesc() {
    return desc;
  }
}
