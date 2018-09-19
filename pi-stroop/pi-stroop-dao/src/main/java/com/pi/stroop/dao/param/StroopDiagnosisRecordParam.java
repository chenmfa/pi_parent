package com.pi.stroop.dao.param;

import com.pi.comm.query.MapperQuery;

/**
 * @description 表stroop_diagnosis_record的实体类
 * @author chenmfa
 * @date 2018-09-19 08:06:13
 */
public class StroopDiagnosisRecordParam extends MapperQuery{
	private static final long serialVersionUID = -4972683369271453960L;
	private Long              userId;             // 测试用户序号 
	private Long              inviteId;           // 测试用户推荐序号(用于绑定历史推荐人) 
	private Long              inviteUserId;       // 绑定历史推荐人 
	private String            diagnosisInfo;      // 诊断原始数据 
	private String            diagnosisResult;    // 反应时间数据 
	private Integer           diagnosisScore;     // 分数 
	private Integer           diagnosisLevel;     // 评级 
	private Integer           diagnosisDelay;     // 响应延迟 
	private Integer           diagnosisReactPeriod;// 反应时间 
	private Integer           diagnosisCorrectInterference;// 正确数干扰量 

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId=userId;
	}

	public Long getInviteId() {
		return this.inviteId;
	}

	public void setInviteId(Long inviteId) {
		this.inviteId=inviteId;
	}

	public Long getInviteUserId() {
		return this.inviteUserId;
	}

	public void setInviteUserId(Long inviteUserId) {
		this.inviteUserId=inviteUserId;
	}

	public String getDiagnosisInfo() {
		return this.diagnosisInfo;
	}

	public void setDiagnosisInfo(String diagnosisInfo) {
		this.diagnosisInfo=diagnosisInfo;
	}

	public String getDiagnosisResult() {
		return this.diagnosisResult;
	}

	public void setDiagnosisResult(String diagnosisResult) {
		this.diagnosisResult=diagnosisResult;
	}

	public Integer getDiagnosisScore() {
		return this.diagnosisScore;
	}

	public void setDiagnosisScore(Integer diagnosisScore) {
		this.diagnosisScore=diagnosisScore;
	}

	public Integer getDiagnosisLevel() {
		return this.diagnosisLevel;
	}

	public void setDiagnosisLevel(Integer diagnosisLevel) {
		this.diagnosisLevel=diagnosisLevel;
	}

	public Integer getDiagnosisDelay() {
		return this.diagnosisDelay;
	}

	public void setDiagnosisDelay(Integer diagnosisDelay) {
		this.diagnosisDelay=diagnosisDelay;
	}

	public Integer getDiagnosisReactPeriod() {
		return this.diagnosisReactPeriod;
	}

	public void setDiagnosisReactPeriod(Integer diagnosisReactPeriod) {
		this.diagnosisReactPeriod=diagnosisReactPeriod;
	}

	public Integer getDiagnosisCorrectInterference() {
		return this.diagnosisCorrectInterference;
	}

	public void setDiagnosisCorrectInterference(Integer diagnosisCorrectInterference) {
		this.diagnosisCorrectInterference=diagnosisCorrectInterference;
	}

}