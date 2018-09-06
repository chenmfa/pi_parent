package com.pi.base.dto.result.respcode.stroop;

public enum StroopResp {
  RESP_UNKNOWN_ERROR(3100100,"STROOP_WX.UNKNOWN_ERROR", "未知错误"),
  RESP_WX_TOKEN_FAILED(3100101,"STROOP_WX.WX_TOKEN_FAILED", "获取微信授权失败"),
  ;
  private int errorCode;
  private String tag;
  private String desc;
  private StroopResp(int errorCode, String tag, String desc) {
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
