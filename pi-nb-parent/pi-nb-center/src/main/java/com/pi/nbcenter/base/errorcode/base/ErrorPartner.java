package com.pi.nbcenter.base.errorcode.base;

public enum ErrorPartner {
  PARTNER_IS_EMPTY(1, 100201, "PARTNER_INFO.PARTNER_IS_EMPTY", "合作商编号为空"),
  PARTNER_INFO_NOT_FOUND(1, 100202, "PARTNER_INFO.PARTNER_INFO_NOT_FOUND", "合作商信息不存在"),
  NO_PARTNER_SERVICE_PROVIDED(1, 100203, "PARTNER_INFO.NO_PARTNER_SERVICE_PROVIDED", "该合作商不存在此功能")
  ;
  private int source;//错误码来源 1.系统内部 2.平台
  private int code;//错误码
  private String key;//错误主键
  private String desc;//描述
  private ErrorPartner(int source, int code, String key, String desc) {
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
