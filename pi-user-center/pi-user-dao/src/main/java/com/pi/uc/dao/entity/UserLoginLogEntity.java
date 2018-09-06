package com.pi.uc.dao.entity;

import com.pi.comm.entity.BaseEntity;

import java.util.Date;

/**
 * @description 表user_login_log的实体类
 * @author chenmfa
 * @date 2018-09-06 23:38:23
 */
public class UserLoginLogEntity extends BaseEntity{
	private static final long serialVersionUID = -4972683369271453960L;
	private Date              loginDate;          // 登陆时间 
	private String            loginArea;          // 登陆地区 
	private String            loginCity;          // 登陆城市 
	private String            loginDevice;        // 登陆设备 
	private Integer           loginSource;        // 登陆的合作商来源 
	private String            loginChannel;       // 登陆的通道 
	private String            appVersion;         // 软件版本 
	private Integer           loginRemindFlag;    // 否推送提醒 
	private String            loginRemindSummary; // 推送摘要 
	private String            loginIdentity;      // 登陆标志号 
	private Long              userId;             //  

	public Date getLoginDate() {
		return this.loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate=loginDate;
	}

	public String getLoginArea() {
		return this.loginArea;
	}

	public void setLoginArea(String loginArea) {
		this.loginArea=loginArea;
	}

	public String getLoginCity() {
		return this.loginCity;
	}

	public void setLoginCity(String loginCity) {
		this.loginCity=loginCity;
	}

	public String getLoginDevice() {
		return this.loginDevice;
	}

	public void setLoginDevice(String loginDevice) {
		this.loginDevice=loginDevice;
	}

	public Integer getLoginSource() {
		return this.loginSource;
	}

	public void setLoginSource(Integer loginSource) {
		this.loginSource=loginSource;
	}

	public String getLoginChannel() {
		return this.loginChannel;
	}

	public void setLoginChannel(String loginChannel) {
		this.loginChannel=loginChannel;
	}

	public String getAppVersion() {
		return this.appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion=appVersion;
	}

	public Integer getLoginRemindFlag() {
		return this.loginRemindFlag;
	}

	public void setLoginRemindFlag(Integer loginRemindFlag) {
		this.loginRemindFlag=loginRemindFlag;
	}

	public String getLoginRemindSummary() {
		return this.loginRemindSummary;
	}

	public void setLoginRemindSummary(String loginRemindSummary) {
		this.loginRemindSummary=loginRemindSummary;
	}

	public String getLoginIdentity() {
		return this.loginIdentity;
	}

	public void setLoginIdentity(String loginIdentity) {
		this.loginIdentity=loginIdentity;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId=userId;
	}

}