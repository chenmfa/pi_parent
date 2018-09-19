package com.pi.stroop.dao.param;

import com.pi.comm.query.MapperQuery;

import java.util.Date;

/**
 * @description 表stroop_task_record的实体类
 * @author chenmfa
 * @date 2018-09-19 23:22:36
 */
public class StroopTaskRecordParam extends MapperQuery{
	private static final long serialVersionUID = -4972683369271453960L;
	private Long              diagnosisId;        // 诊断流水号(diagnosis_record_id) 
	private Integer           taskId;             // 任务序号（单次诊断的任务序号） 
	private Long              taskUserId;         //  
	private Date              taskStartTime;      // 任务开始时间 
	private String            taskResultParam;    // 任务参量 
	private Integer           taskCorrectCount;   // 正确数 
	private Integer           taskErrorCount;     // 错误数 
	private Integer           taskMisCount;       // 未应答数 

	public Long getDiagnosisId() {
		return this.diagnosisId;
	}

	public void setDiagnosisId(Long diagnosisId) {
		this.diagnosisId=diagnosisId;
	}

	public Integer getTaskId() {
		return this.taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId=taskId;
	}

	public Long getTaskUserId() {
		return this.taskUserId;
	}

	public void setTaskUserId(Long taskUserId) {
		this.taskUserId=taskUserId;
	}

	public Date getTaskStartTime() {
		return this.taskStartTime;
	}

	public void setTaskStartTime(Date taskStartTime) {
		this.taskStartTime=taskStartTime;
	}

	public String getTaskResultParam() {
		return this.taskResultParam;
	}

	public void setTaskResultParam(String taskResultParam) {
		this.taskResultParam=taskResultParam;
	}

	public Integer getTaskCorrectCount() {
		return this.taskCorrectCount;
	}

	public void setTaskCorrectCount(Integer taskCorrectCount) {
		this.taskCorrectCount=taskCorrectCount;
	}

	public Integer getTaskErrorCount() {
		return this.taskErrorCount;
	}

	public void setTaskErrorCount(Integer taskErrorCount) {
		this.taskErrorCount=taskErrorCount;
	}

	public Integer getTaskMisCount() {
		return this.taskMisCount;
	}

	public void setTaskMisCount(Integer taskMisCount) {
		this.taskMisCount=taskMisCount;
	}

}