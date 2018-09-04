package com.pi.nbcenter.device.bean.dto.nb.lockdevice;

/**
 * @author lusj
 * @version V1.0
 * @Type LockDeviceAlarmInfo
 * @Desc  告警处理
 * @date 2018/6/25
 */
public class LockDeviceAlarmInfo {


    /**
     * 设备串号,必填
     */
    private String imei;

    /**
     * 供应商id,必填，2.1.1接口获取
     */
    private String supportCompanyId;

    /**
     * 告警事件id(对接方)
     */
    private String eventId;

    /**
     * 告警类型 1：长期未关门报警，2：低电压报警，3：防撬报警
     */
    private String type;

    /**
     * 告警名称（对于type）
     */
    private String alarmName;

    /**
     * 告警时间
     yyyyy-MM-dd hh:mm:ss
     */
    private String alarmTime;


    /**
     *
     *
     *  告警处理
     *
     *
     */


    /**
     * 告警事件状态，1:未解决，2:已处理
     */
    private String alarmState;

    /**
     * 1：长期未关门报警，2：低电压报警，3：防撬报警
     */
    private String confirmType;

    /**
     * 处理结果
     */
    private String result;


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

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getAlarmState() {
        return alarmState;
    }

    public void setAlarmState(String alarmState) {
        this.alarmState = alarmState;
    }

    public String getConfirmType() {
        return confirmType;
    }

    public void setConfirmType(String confirmType) {
        this.confirmType = confirmType;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
