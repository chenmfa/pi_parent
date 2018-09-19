package com.pi.stroop.model.diagnosis;

public class DiagnosisTask {
  //请求param的参数格式为  时间-结果 其中结果： 0 未应答 1. 正常  2. 选择错误
  private int errorCount;//错误数
  private String taskName;//任务名称  
  private int correctCount;//正确数目  
  private int misCount;//未应答数  
  public int getErrorCount() {
    return errorCount;
  }
  public void setErrorCount(int errorCount) {
    this.errorCount = errorCount;
  }
  public String getTaskName() {
    return taskName;
  }
  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }
  public int getCorrectCount() {
    return correctCount;
  }
  public void setCorrectCount(int correctCount) {
    this.correctCount = correctCount;
  }
  public int getMisCount() {
    return misCount;
  }
  public void setMisCount(int misCount) {
    this.misCount = misCount;
  }
}
