package com.pi.nbcenter.device.entity.auto;

import java.util.Date;

public class IotPlatformDevEntry {
    private Long id;

    private Date createDate;

    private Date updateDate;

    private Integer version;

    private Long platformDevId;

    private String platformDevEntry;

    private Long userId;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getPlatformDevId() {
        return platformDevId;
    }

    public void setPlatformDevId(Long platformDevId) {
        this.platformDevId = platformDevId;
    }

    public String getPlatformDevEntry() {
        return platformDevEntry;
    }

    public void setPlatformDevEntry(String platformDevEntry) {
        this.platformDevEntry = platformDevEntry == null ? null : platformDevEntry.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}