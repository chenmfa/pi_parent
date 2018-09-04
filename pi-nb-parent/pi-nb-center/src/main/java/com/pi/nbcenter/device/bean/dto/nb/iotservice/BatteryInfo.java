package com.pi.nbcenter.device.bean.dto.nb.iotservice;

public class BatteryInfo {
  private int batteryLevel;
  private int batteryVoltage;
  private int batteryState;
  public int getBatteryLevel() {
    return batteryLevel;
  }
  public void setBatteryLevel(int batteryLevel) {
    this.batteryLevel = batteryLevel;
  }
  public int getBatteryVoltage() {
    return batteryVoltage;
  }
  public void setBatteryVoltage(int batteryVoltage) {
    this.batteryVoltage = batteryVoltage;
  }
  public int getBatteryState() {
    return batteryState;
  }
  public void setBatteryState(int batteryState) {
    this.batteryState = batteryState;
  }
}
