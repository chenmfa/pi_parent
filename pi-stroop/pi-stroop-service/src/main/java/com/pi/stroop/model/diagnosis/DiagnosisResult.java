package com.pi.stroop.model.diagnosis;

import java.util.List;

public class DiagnosisResult {
  private int diagnosisLevel;//成绩(1. 优 2. 中 3. 差) 
  private List<DiagnosisTask> taskList;//任务列表
  private int delayPeriod;//响应延迟
  private Long diagnosisId;//诊断流水号
  private int reactPeriod;//反应时间
  private int interference;//正确数干扰量  
  private String diagnosisDate;//诊断日期
  public int getDiagnosisLevel() {
    return diagnosisLevel;
  }
  public void setDiagnosisLevel(int diagnosisLevel) {
    this.diagnosisLevel = diagnosisLevel;
  }
  public List<DiagnosisTask> getTaskList() {
    return taskList;
  }
  public void setTaskList(List<DiagnosisTask> taskList) {
    this.taskList = taskList;
  }
  public int getDelayPeriod() {
    return delayPeriod;
  }
  public void setDelayPeriod(int delayPeriod) {
    this.delayPeriod = delayPeriod;
  }
  public Long getDiagnosisId() {
    return diagnosisId;
  }
  public void setDiagnosisId(Long diagnosisId) {
    this.diagnosisId = diagnosisId;
  }
  public int getReactPeriod() {
    return reactPeriod;
  }
  public void setReactPeriod(int reactPeriod) {
    this.reactPeriod = reactPeriod;
  }
  public int getInterference() {
    return interference;
  }
  public void setInterference(int interference) {
    this.interference = interference;
  }
  public String getDiagnosisDate() {
    return diagnosisDate;
  }
  public void setDiagnosisDate(String diagnosisDate) {
    this.diagnosisDate = diagnosisDate;
  }
}
