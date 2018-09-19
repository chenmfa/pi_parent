package com.pi.stroop.enumerate;

public enum DiagnosisStageEnum {
  STAGE_A(1, "测试A"),
  STAGE_B(2, "测试B"),
  STAGE_C(3, "测试C"),
  ;
  private int stage;
  private String desc;
  private DiagnosisStageEnum(int stage, String desc) {
    this.stage = stage;
    this.desc = desc;
  }
  public int getStage() {
    return stage;
  }
  public String getDesc() {
    return desc;
  }
  public static DiagnosisStageEnum getDiagnosisStage(Integer stage){
    if(null == stage){
      return null;
    }
    for(DiagnosisStageEnum diagnosisStage:values()){
      if(diagnosisStage.getStage() == stage.intValue()){
        return diagnosisStage;
      }
    }
    return null;
  }
  public static int getMaxStage(){
    return STAGE_C.getStage();
  }
}
