package com.pi.base.dto.result.respcode.config;

public enum ConfigResp {
  RESP_UNKNOW_ERROR(2100100, "CONFIG_PARTNER.UNKNOW_ERROR", "来源配置不存在"),
  RESP_SOURCE_ID_EMPTY(2100101, "CONFIG_PARTNER.SOURCE_ID_EMPTY", "来源为空"),
  RESP_CONFIG_NAME_EMPTY(2100102, "CONFIG_PARTNER.CONFIG_NAME_EMPTY", "配置名称为空"),
  RESP_CONFIG_NOT_FOUND(2100103, "CONFIG_PARTNER.CONFIG_NOT_FOUND", "来源配置不存在")
  ;
  private int errorCode;
  private String tag;
  private String desc;
  private ConfigResp(int errorCode, String tag, String desc) {
    this.errorCode = errorCode;
    this.tag = tag;
    this.desc = desc;
  }
  public int getErrorCode() {
    return errorCode;
  }
  public String getTag() {
    return tag;
  }
  public String getDesc() {
    return desc;
  }
}
