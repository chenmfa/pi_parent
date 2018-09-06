package com.pi.config.dao.entity;

import com.pi.comm.entity.BaseEntity;

/**
 * @description 表base_partner_config的实体类
 * @author chenmfa
 * @date 2018-09-06 14:24:46
 */
public class BasePartnerConfigEntity extends BaseEntity{
	private static final long serialVersionUID = -4972683369271453960L;
	private Long              partnerCode;        // 来源(合作伙伴)id 
	private String            partnerConfig;      // 来源(合作伙伴)配置 
	private String            partnerConfigValue; // 配置值 
	private Integer           configState;        // 配置状态 

	public Long getPartnerCode() {
		return this.partnerCode;
	}

	public void setPartnerCode(Long partnerCode) {
		this.partnerCode=partnerCode;
	}

	public String getPartnerConfig() {
		return this.partnerConfig;
	}

	public void setPartnerConfig(String partnerConfig) {
		this.partnerConfig=partnerConfig;
	}

	public String getPartnerConfigValue() {
		return this.partnerConfigValue;
	}

	public void setPartnerConfigValue(String partnerConfigValue) {
		this.partnerConfigValue=partnerConfigValue;
	}

	public Integer getConfigState() {
		return this.configState;
	}

	public void setConfigState(Integer configState) {
		this.configState=configState;
	}

}