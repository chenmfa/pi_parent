package com.pi.nbcenter.device.bean.dto.nb.lockdevice;

/**
 * @author lusj
 * @version V1.0
 * @Type LockDeviceInfo
 * @Desc 设备监测实体
 * @date 2018/6/24
 */
public class LockDeviceInfo {



    /**
     * 设备串号,必填
     */
    private String imei;

    /**
     * 型号
     */
    private String model;

    /**
     * 区域
     */
    private String region;

    /**
     * 安装地址
     */
    private String installAddress;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 设备心跳间隔
     */
    private String heartbeat;


    /**
     * Sim卡卡号
     */
    private String iccid;


    /**
     * 供应商id, 2.2.1接口获得，必填
     */
    private String supportCompanyId;

    /**
     * 管理单位id（对接方）
     */
    private String manageCompanyId;


    /**
     * 管理员id（对接方）
     */
    private String managerId;


    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getInstallAddress() {
        return installAddress;
    }

    public void setInstallAddress(String installAddress) {
        this.installAddress = installAddress;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(String heartbeat) {
        this.heartbeat = heartbeat;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getSupportCompanyId() {
        return supportCompanyId;
    }

    public void setSupportCompanyId(String supportCompanyId) {
        this.supportCompanyId = supportCompanyId;
    }

    public String getManageCompanyId() {
        return manageCompanyId;
    }

    public void setManageCompanyId(String manageCompanyId) {
        this.manageCompanyId = manageCompanyId;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }
}
