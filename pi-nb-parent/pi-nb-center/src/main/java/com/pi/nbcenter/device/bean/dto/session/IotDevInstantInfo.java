package com.pi.nbcenter.device.bean.dto.session;

public class IotDevInstantInfo {
  
  private String iotDevId;
  private Integer nbState;
  private Integer nbBattery;
  private Integer batteryLevel;
  private Integer nbRssi;
  private String imsi;
  public String getIotDevId() {
    return iotDevId;
  }
  public void setIotDevId(String iotDevId) {
    this.iotDevId = iotDevId;
  }
  public Integer getNbState() {
    return nbState;
  }
  public void setNbState(Integer nbState) {
    this.nbState = nbState;
  }
  public Integer getNbBattery() {
    return nbBattery;
  }
  public void setNbBattery(Integer nbBattery) {
    this.nbBattery = nbBattery;
  }
  public Integer getBatteryLevel() {
    return batteryLevel;
  }
  public void setBatteryLevel(Integer batteryLevel) {
    this.batteryLevel = batteryLevel;
  }
  public Integer getNbRssi() {
    return nbRssi;
  }
  public void setNbRssi(Integer nbRssi) {
    this.nbRssi = nbRssi;
  }
  public String getImsi() {
    return imsi;
  }
  public void setImsi(String imsi) {
    this.imsi = imsi;
  }
}
