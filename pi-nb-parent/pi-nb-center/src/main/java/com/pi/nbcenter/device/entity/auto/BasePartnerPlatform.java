package com.pi.nbcenter.device.entity.auto;

import java.util.Date;

public class BasePartnerPlatform {
    private Integer id;

    private Date createDate;

    private Date updateDate;

    private Integer version;

    private Long partnerCode;

    private Integer platformId;

    private Integer platformVersion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Long getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(Long partnerCode) {
        this.partnerCode = partnerCode;
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    public Integer getPlatformVersion() {
        return platformVersion;
    }

    public void setPlatformVersion(Integer platformVersion) {
        this.platformVersion = platformVersion;
    }
}