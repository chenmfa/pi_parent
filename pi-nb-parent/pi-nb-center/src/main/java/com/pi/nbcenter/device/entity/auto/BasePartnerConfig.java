package com.pi.nbcenter.device.entity.auto;

public class BasePartnerConfig {
    private Integer id;

    private Long partnerCode;

    private String partnerConfig;

    private String partnerConfigValue;

    private Integer configState;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(Long partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getPartnerConfig() {
        return partnerConfig;
    }

    public void setPartnerConfig(String partnerConfig) {
        this.partnerConfig = partnerConfig == null ? null : partnerConfig.trim();
    }

    public String getPartnerConfigValue() {
        return partnerConfigValue;
    }

    public void setPartnerConfigValue(String partnerConfigValue) {
        this.partnerConfigValue = partnerConfigValue == null ? null : partnerConfigValue.trim();
    }

    public Integer getConfigState() {
        return configState;
    }

    public void setConfigState(Integer configState) {
        this.configState = configState;
    }
}