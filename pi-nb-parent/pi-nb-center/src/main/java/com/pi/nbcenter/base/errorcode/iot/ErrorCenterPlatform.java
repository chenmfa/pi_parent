package com.pi.nbcenter.base.errorcode.iot;

public enum ErrorCenterPlatform {
  UNKNOWN_PLATFORM(1, 100301, "PLATFORM_CONFIG.UNKNOWN_PLATFORM", "无效的平台编号"),
  PLATFORM_NOT_EXIST(1, 100302, "PLATFORM_CONFIG.PLATFORM_NOT_EXIST", "平台信息不存在"),
  INVALID_CONFIG_VERSION(1, 100303, "PLATFORM_CONFIG.INVALID_CONFIG_VERSION", "无效的平台配置版本"),
  PLATFORM_CONFIG_NOT_EXIST(1, 100304, "PLATFORM_CONFIG.PLATFORM_CONFIG_NOT_EXIST", "平台配置不存在")
  
  ;
  private int source;//错误码来源 1.系统内部 2.平台
  private int code;//错误码
  private String key;//错误主键
  private String desc;//描述
  private ErrorCenterPlatform(int source, int code, String key, String desc) {
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
