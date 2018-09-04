package com.pi.nbcenter.device.entity.auto;

import java.util.Date;

public class IotDeviceInfo {
    private Long id;

    private String iotDevId;

    private String nbDevId;

    private Long partnerCode;

    private Integer iotProtocolVersion;

    private String iotDevImsi;

    private String iotDevImei;

    private String iotDevCert;

    private Integer iotDevRegcode;

    private Date createDate;

    private Date updateDate;

    private Integer version;

    private Byte state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIotDevId() {
        return iotDevId;
    }

    public void setIotDevId(String iotDevId) {
        this.iotDevId = iotDevId == null ? null : iotDevId.trim();
    }

    public String getNbDevId() {
        return nbDevId;
    }

    public void setNbDevId(String nbDevId) {
        this.nbDevId = nbDevId == null ? null : nbDevId.trim();
    }

    public Long getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(Long partnerCode) {
        this.partnerCode = partnerCode;
    }

    public Integer getIotProtocolVersion() {
        return iotProtocolVersion;
    }

    public void setIotProtocolVersion(Integer iotProtocolVersion) {
        this.iotProtocolVersion = iotProtocolVersion;
    }

    public String getIotDevImsi() {
        return iotDevImsi;
    }

    public void setIotDevImsi(String iotDevImsi) {
        this.iotDevImsi = iotDevImsi == null ? null : iotDevImsi.trim();
    }

    public String getIotDevImei() {
        return iotDevImei;
    }

    public void setIotDevImei(String iotDevImei) {
        this.iotDevImei = iotDevImei == null ? null : iotDevImei.trim();
    }

    public String getIotDevCert() {
        return iotDevCert;
    }

    public void setIotDevCert(String iotDevCert) {
        this.iotDevCert = iotDevCert == null ? null : iotDevCert.trim();
    }

    public Integer getIotDevRegcode() {
        return iotDevRegcode;
    }

    public void setIotDevRegcode(Integer iotDevRegcode) {
        this.iotDevRegcode = iotDevRegcode;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }
}