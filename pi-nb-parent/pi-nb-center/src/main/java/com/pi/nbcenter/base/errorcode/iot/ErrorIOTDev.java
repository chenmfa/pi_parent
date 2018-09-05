package com.pi.nbcenter.base.errorcode.iot;

public enum ErrorIOTDev {
  DEV_IOTID_EMPTY(1, 1100101, "DEV_INFO.DEV_ID_EMPTY", "设备注册编号为空"),
  DEV_IMEI_EMPTY(1, 1100102, "DEV_INFO.DEV_IMEI_EMPTY", "请输入设备IMEI"),
  DEV_INFO_EMPTY(1, 1100103, "DEV_INFO.DEV_INFO_EMPTY", "设备信息为空"),
  DEV_CERT_EMPTY(1, 1100104, "DEV_INFO.DEV_CERT_EMPTY", "设备证书为空"),
  DEV_REG_CODE_EMPTY(1, 1100105, "DEV_INFO.DEV_REG_CODE_EMPTY", "设备注册码为空"),
  DEV_ID_EMPTY(1, 1100106, "DEV_INFO.DEV_ID_EMPTY", "设备编号为空"),
  REG_DEV_FROM_PLAT_FAILED(1, 1100111, "DEV_INFO.PLATFORM_ID_REG_FAILED", "向平台注册设备失败"),
  REG_MANUFACTURER_FAILED(1, 1100112, "DEV_INFO.PLATFORM_MANUFACTURER_REG_FAILED", "注册厂商信息失败"),
  DEV_SESSION_INFO_EMPTY(1, 1100121, "DEV_SESSION.SESSION_INFO_EMPTY", "设备状态信息为空"),
  DEV_ERROR_UNKNOWN(1, 1100100, "DEV_INFO.UNKNOW_ERROR", "未知错误")
  ;
  private int source;//错误码来源 1.系统内部 2.平台
  private int code;//错误码
  private String key;//错误主键
  private String desc;//描述
  private ErrorIOTDev(int source, int code, String key, String desc){
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
