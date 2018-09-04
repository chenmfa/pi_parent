package com.pi.nbcenter.base.errorcode.base;

public enum ErrorPartnerSubscribe {
  ERR_SUBSCRIPTION_EMPTY(1, 100401, "PARTNER_SUBSCRIPTION.SUBSCRIPTION_EMPTY", "合作商订阅为空"),
  ERR_NOTIFY_TYPE_EMPTY(1, 100402, "PARTNER_SUBSCRIPTION.NOTIFY_TYPE_EMPTY", "订阅类型为空"),
  ERR_NOTIFY_TYPE_NOT_SUPPORT(1, 100403, "PARTNER_SUBSCRIPTION.NOTIFY_TYPE_NOT_SUPPORT", "不支持的订阅方式"),
  ERR_NOTIFY_TYPE_NOT_FINISHED(1, 100404, "PARTNER_SUBSCRIPTION.NOTIFY_TYPE_NOT_FINISHED", "该订阅方式暂时未开放"),
  ERR_NOTIFY_URL_EMPTY(1, 100405, "PARTNER_SUBSCRIPTION.NOTIFY_URL_EMPTY", "订阅地址为空"),
  ERR_PARTNER_EMPTY(1, 100406, "PARTNER_SUBSCRIPTION.PARTNER_EMPTY", "订阅来源为空"),
  ERR_SUBSCRIPTION_NOT_FOUND(1, 100407, "PARTNER_SUBSCRIPTION.SUBSCRIPTION_NOT_FOUND", "订阅地址不存在")
  ;
  private int source;//错误码来源 1.系统内部 2.平台
  private int code;//错误码
  private String key;//错误主键
  private String desc;//描述
  private ErrorPartnerSubscribe(int source, int code, String key, String desc) {
    this.source = source;
    this.code = code;
    this.key = key;
    this.desc = desc;
  }
  public int getSource() {
    return source;
  }
  public int getCode() {
    return code;
  }
  public String getKey() {
    return key;
  }
  public String getDesc() {
    return desc;
  }
}
