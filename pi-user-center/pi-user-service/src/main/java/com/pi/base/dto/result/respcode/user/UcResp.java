package com.pi.base.dto.result.respcode.user;

public enum UcResp {
  RESP_UNKNOWN_ERROR(2100100,"UC_USER.UNKNOWN_ERROR", "未知错误"),
  RESP_SOURCE_ID_EMPTY(2100101,"UC_USER.SOURCE_ID_EMPTY", "来源为空"),
  RESP_WECHAT_ID_EMPTY(2100102,"UC_USER.WECHAT_ID_EMPTY", "来源为空")
  ;
  private int errorCode;
  private String tag;
  private String desc;
  private UcResp(int errorCode, String tag, String desc) {
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
