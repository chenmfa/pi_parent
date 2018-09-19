package com.pi.stroop.enumerate;

public enum DiagnosisResponseState {
  NO_RESPONSE(0, "未应答"),
  RESPONSE_CORRECT(1, "应答正确"),
  RESPONSE_WRONG(2, "应答错误");
  ;
  private int code;
  private String desc;
  private DiagnosisResponseState(int code, String desc) {
    this.code = code;
    this.desc = desc;
  }
  public static DiagnosisResponseState getDiagnosisResponseState(Integer code){
    if(null == code){
      return null;
    }
    for(DiagnosisResponseState diagnosisState:values())
      if(diagnosisState.getCode() == code.intValue())
        return diagnosisState;
    return null;
  }
  public int getCode() {
    return code;
  }
  public String getDesc() {
    return desc;
  }
}
