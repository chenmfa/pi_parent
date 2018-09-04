package com.pi.nbcenter.device.bean.dto.partner;

import com.pi.base.annotation.FieldAlia;

public class IotPartnerConfig {
  private String appId;
  private String appSecret;
  private String manufacturerId;
  private String manufacturerName;
  private String deviceType;
  private String model;
  private String protocolType;
  @FieldAlia(name = "version")
  private Integer configVersion;
  public String getAppId() {
    return appId;
  }
  public void setAppId(String appId) {
    this.appId = appId;
  }
  public String getAppSecret() {
    return appSecret;
  }
  public void setAppSecret(String appSecret) {
    this.appSecret = appSecret;
  }
  public String getManufacturerId() {
    return manufacturerId;
  }
  public void setManufacturerId(String manufacturerId) {
    this.manufacturerId = manufacturerId;
  }
  public String getManufacturerName() {
    return manufacturerName;
  }
  public void setManufacturerName(String manufacturerName) {
    this.manufacturerName = manufacturerName;
  }
  public String getDeviceType() {
    return deviceType;
  }
  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }
  public String getModel() {
    return model;
  }
  public void setModel(String model) {
    this.model = model;
  }
  public String getProtocolType() {
    return protocolType;
  }
  public void setProtocolType(String protocolType) {
    this.protocolType = protocolType;
  }
  public Integer getConfigVersion() {
    return configVersion;
  }
  public void setConfigVersion(Integer configVersion) {
    this.configVersion = configVersion;
  }
}
