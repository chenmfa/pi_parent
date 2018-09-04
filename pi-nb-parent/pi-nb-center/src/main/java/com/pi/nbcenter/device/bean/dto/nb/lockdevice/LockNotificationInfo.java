package com.pi.nbcenter.device.bean.dto.nb.lockdevice;

/**
 * @author lusj
 * @version V1.0
 * @Type LockNotificationInfo
 * @Desc 上报通知实体
 * @date 2018/6/25
 */
public class LockNotificationInfo {


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
     * 通知类型1:短信,2:語音
     */
    private String notificationType;

    /**
     * 发送的手机号
     */
    private String phone;

    /**
     * 发送的用户姓名
     */
    private String userName;


    /**
     * 发送内容
     */
    private String content;


    /**
     * 是否发送成功  0:失败，1：成功
     */
    private String sendState;

    /**
     * 发送时间 yyyy-MM-dd hh:mm:ss
     */
    private String sendTime;

    /**
     * 发送失败原因
     */
    private String failedReason;


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

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendState() {
        return sendState;
    }

    public void setSendState(String sendState) {
        this.sendState = sendState;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
    }
}
