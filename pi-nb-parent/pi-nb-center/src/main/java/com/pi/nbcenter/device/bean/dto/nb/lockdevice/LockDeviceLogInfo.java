package com.pi.nbcenter.device.bean.dto.nb.lockdevice;

/**
 * @author lusj
 * @version V1.0
 * @Type LockDeviceLogInfo
 * @Desc 上报日志请求参数
 * @date 2018/6/25
 */
public class LockDeviceLogInfo {


    /**
     * 设备串号,必填
     */
    private String imei;

    /**
     * 供应商id,必填，2.1.1接口获取
     */
    private String supportCompanyId;

    /**
     * 信号强度
     */
    private String signalIntensity;

    /**
     * 信号覆盖等级
     */
    private String ecl;

    /**
     * 信噪比
     */
    private String snr;

    /**
     *位置信息
     */
    private String location;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getSupportCompanyId() {
        return supportCompanyId;
    }

    public void setSupportCompanyId(String supportCompanyId) {
        this.supportCompanyId = supportCompanyId;
    }

    public String getSignalIntensity() {
        return signalIntensity;
    }

    public void setSignalIntensity(String signalIntensity) {
        this.signalIntensity = signalIntensity;
    }

    public String getEcl() {
        return ecl;
    }

    public void setEcl(String ecl) {
        this.ecl = ecl;
    }

    public String getSnr() {
        return snr;
    }

    public void setSnr(String snr) {
        this.snr = snr;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
