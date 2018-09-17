package com.pi.stroop.enumerate;

public enum DiagnosisLevelEnum {
  GOOD(3,"优秀"),
  WELL(2,"中等"),
  OPPS(1,"不理想"),
  UNKNOWN(0,"未知"),
  ;
  private int level;
  private String desc;
  DiagnosisLevelEnum(int level, String desc) {
    this.level = level;
    this.desc = desc;
  }
  public int getLevel() {
    return level;
  }
  public String getDesc() {
    return desc;
  }
  public static String getDiagnosisLevelDesc(Integer level){
    if(null == level){
      return UNKNOWN.desc;
    }
    for(DiagnosisLevelEnum levelEnum:values()){
      if(levelEnum.getLevel() == level.intValue())
        return levelEnum.getDesc();
    }
    return UNKNOWN.desc;
  }
}
