package com.pi.nbcenter.base.errorcode.iot;

public enum ErrorIOTPlatform {
  DEVICE_NOT_EXIST(100403, "设备不存在");
  private int code;
  private String desc;
  private ErrorIOTPlatform(int code, String desc){
    this.code = code;
    this.desc = desc;
  }
  public int getCode() {
    return code;
  }
  public String getDesc() {
    return desc;
  }
}
