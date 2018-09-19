package com.pi.stroop.model.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class StroopTaskRecordPostForm {
  @NotNull(message="STROOP_TASK.TASK_USER_ID_IS_EMPTY")
  Long userId;
  @NotNull(message="STROOP_TASK.TASK_ID_IS_EMPTY")
  private Integer           taskId;             // 任务序号（单次诊断的任务序号） 
  @NotNull(message="STROOP_TASK.TASK_START_TIME_IS_EMPTY")
  private Long              taskStartTime;      // 任务开始时间 
  @NotBlank(message="STROOP_TASK.TASK_RESULT_PARAM_IS_EMPTY")
  private String            taskResultParam;    // 任务参量 
  @NotNull(message="STROOP_TASK.TASK_CORRENT_COUNT_IS_EMPTY")
  private Integer           taskCorrectCount;   // 正确数 
  @NotNull(message="STROOP_TASK.TASK_ERROR_COUNT_IS_EMPTY")
  private Integer           taskErrorCount;     // 错误数 
  @NotNull(message="STROOP_TASK.TASK_MIS_COUNT_IS_EMPTY")
  private Integer           taskMisCount;       // 未应答数 
  public Long getUserId() {
    return userId;
  }
  public void setUserId(Long userId) {
    this.userId = userId;
  }
  public Integer getTaskId() {
    return taskId;
  }
  public void setTaskId(Integer taskId) {
    this.taskId = taskId;
  }
  public Long getTaskStartTime() {
    return taskStartTime;
  }
  public void setTaskStartTime(Long taskStartTime) {
    this.taskStartTime = taskStartTime;
  }
  public String getTaskResultParam() {
    return taskResultParam;
  }
  public void setTaskResultParam(String taskResultParam) {
    this.taskResultParam = taskResultParam;
  }
  public Integer getTaskCorrectCount() {
    return taskCorrectCount;
  }
  public void setTaskCorrectCount(Integer taskCorrectCount) {
    this.taskCorrectCount = taskCorrectCount;
  }
  public Integer getTaskErrorCount() {
    return taskErrorCount;
  }
  public void setTaskErrorCount(Integer taskErrorCount) {
    this.taskErrorCount = taskErrorCount;
  }
  public Integer getTaskMisCount() {
    return taskMisCount;
  }
  public void setTaskMisCount(Integer taskMisCount) {
    this.taskMisCount = taskMisCount;
  }
}
