package com.pi.nbcenter.device.entity.auto;

import java.util.Date;

public class IotDeviceSession {
    private Long id;

    private String iotDevId;

    private String iotDevIp;

    private Integer iotDevPort;

    private Date createDate;

    private Date updateDate;

    private Date latestActiveTime;

    private Integer iotDevState;

    private Integer iotDevBattery;

    private Integer iotDevBatteryPercent;

    private Integer iotDevRssi;

    private byte[] iotDevSeckey;

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

    public String getIotDevIp() {
        return iotDevIp;
    }

    public void setIotDevIp(String iotDevIp) {
        this.iotDevIp = iotDevIp == null ? null : iotDevIp.trim();
    }

    public Integer getIotDevPort() {
        return iotDevPort;
    }

    public void setIotDevPort(Integer iotDevPort) {
        this.iotDevPort = iotDevPort;
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

    public Date getLatestActiveTime() {
        return latestActiveTime;
    }

    public void setLatestActiveTime(Date latestActiveTime) {
        this.latestActiveTime = latestActiveTime;
    }

    public Integer getIotDevState() {
        return iotDevState;
    }

    public void setIotDevState(Integer iotDevState) {
        this.iotDevState = iotDevState;
    }

    public Integer getIotDevBattery() {
        return iotDevBattery;
    }

    public void setIotDevBattery(Integer iotDevBattery) {
        this.iotDevBattery = iotDevBattery;
    }

    public Integer getIotDevBatteryPercent() {
        return iotDevBatteryPercent;
    }

    public void setIotDevBatteryPercent(Integer iotDevBatteryPercent) {
        this.iotDevBatteryPercent = iotDevBatteryPercent;
    }

    public Integer getIotDevRssi() {
        return iotDevRssi;
    }

    public void setIotDevRssi(Integer iotDevRssi) {
        this.iotDevRssi = iotDevRssi;
    }

    public byte[] getIotDevSeckey() {
        return iotDevSeckey;
    }

    public void setIotDevSeckey(byte[] iotDevSeckey) {
        this.iotDevSeckey = iotDevSeckey;
    }
}