package com.pi.base.dto.result.respcode;

public enum CommonResponse {
  SUCCESS(1, 0, "REQ_COMM.OPT_SUCCESS", "操作成功");
  private int source;//错误码来源 1.系统内部 2.平台
  private int code;//错误码
  private String key;//错误主键
  private String desc;//描述
  private CommonResponse(int source, int code, String key, String desc) {
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
