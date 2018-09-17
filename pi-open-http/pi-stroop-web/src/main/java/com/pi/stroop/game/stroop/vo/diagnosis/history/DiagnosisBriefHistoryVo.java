package com.pi.stroop.game.stroop.vo.diagnosis.history;

public class DiagnosisBriefHistoryVo {
  private String diagnosisLevel;//1. 优 2. 中 3. 差
  private String diagnosisDate;
  private Long diagnosisId;
  private String name;//测试人名称
  public String getDiagnosisLevel() {
    return diagnosisLevel;
  }
  public void setDiagnosisLevel(String diagnosisLevel) {
    this.diagnosisLevel = diagnosisLevel;
  }
  public String getDiagnosisDate() {
    return diagnosisDate;
  }
  public void setDiagnosisDate(String diagnosisDate) {
    this.diagnosisDate = diagnosisDate;
  }
  public Long getDiagnosisId() {
    return diagnosisId;
  }
  public void setDiagnosisId(Long diagnosisId) {
    this.diagnosisId = diagnosisId;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
}
