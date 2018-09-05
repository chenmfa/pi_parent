package com.pi.base.enumerate.record;

public enum RecordState {
  STATE_VIEW_INEFFEVTIVE(0, "前端或旧版本的已删除或失效"),
  STATE_VIEW_EFFEVTIVE(1, "前端或旧版本的有效或正常"),
  STATE_NORMAL(10, "正常"),
  STATE_DELETE(20, "删除")
  ;
  private int code;
  private String desc;
  private RecordState(int code, String desc) {
    this.code = code;
    this.desc = desc;
  }
  public int getCode() {
    return code;
  }
  public String getDesc() {
    return desc;
  }
  
  public static Integer transLateIntoViewState(Integer state){
    if(null == state){
      return null;
    }
    if(RecordState.STATE_NORMAL.getCode() == state){
      return RecordState.STATE_VIEW_EFFEVTIVE.getCode();
    }
    if(RecordState.STATE_DELETE.getCode() == state){
      return RecordState.STATE_VIEW_INEFFEVTIVE.getCode();
    }
    return state;
  }
  public static Integer transLateIntoBusinessState(Integer state){
    if(null == state){
      return null;
    }
    if(RecordState.STATE_VIEW_EFFEVTIVE.getCode() == state){
      return RecordState.STATE_NORMAL.getCode();
    }
    if(RecordState.STATE_VIEW_INEFFEVTIVE.getCode() == state){
      return RecordState.STATE_DELETE.getCode();
    }
    return state;
  }
}
