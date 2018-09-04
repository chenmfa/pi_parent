package com.pi.nbcenter.device.bean.dto.nb;

public class DeviceService {
  private String serviceType;//服务类型
  private String serviceId;//服务ID标志
  private Object data;//数据
  private String eventTime;
  private String serviceInfo;//业务信息，此字段用于内部控制功能，不用关心此字段
  public String getServiceType() {
    return serviceType;
  }
  public void setServiceType(String serviceType) {
    this.serviceType = serviceType;
  }
  public String getServiceId() {
    return serviceId;
  }
  public void setServiceId(String serviceId) {
    this.serviceId = serviceId;
  }
  public Object getData() {
    return data;
  }
  public void setData(Object data) {
    this.data = data;
  }
  public String getEventTime() {
    return eventTime;
  }
  public void setEventTime(String eventTime) {
    this.eventTime = eventTime;
  }
  public String getServiceInfo() {
    return serviceInfo;
  }
  public void setServiceInfo(String serviceInfo) {
    this.serviceInfo = serviceInfo;
  }

}
