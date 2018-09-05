package com.pi.base.enumerate.redis;

public enum RedisCacheEnum {
  SIMPLE_PARTNER_CONFIG("pub_partner_conifg_{0}_{1}", 300, "合作商配置缓存")
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
