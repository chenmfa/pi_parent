package com.pi.nbcenter.device.bean.dto.nb.iotservice;


public class SignalInfo {
  private int signalStrength;
  private int linkQuality;
  private int signalECL;
  private int cellId;
  private int signalPCI;
  private int signalSNR;
  private int mode;
  private String IMSI;
  private String eventTime;
  public int getSignalStrength() {
    return signalStrength;
  }
  public void setSignalStrength(int signalStrength) {
    this.signalStrength = signalStrength;
  }
  public int getLinkQuality() {
    return linkQuality;
  }
  public void setLinkQuality(int linkQuality) {
    this.linkQuality = linkQuality;
  }
  public int getSignalECL() {
    return signalECL;
  }
  public void setSignalECL(int signalECL) {
    this.signalECL = signalECL;
  }
  public int getCellId() {
    return cellId;
  }
  public void setCellId(int cellId) {
    this.cellId = cellId;
  }
  public int getSignalPCI() {
    return signalPCI;
  }
  public void setSignalPCI(int signalPCI) {
    this.signalPCI = signalPCI;
  }
  public int getSignalSNR() {
    return signalSNR;
  }
  public void setSignalSNR(int signalSNR) {
    this.signalSNR = signalSNR;
  }
  public int getMode() {
    return mode;
  }
  public void setMode(int mode) {
    this.mode = mode;
  }
  public String getEventTime() {
    return eventTime;
  }
  public void setEventTime(String eventTime) {
    this.eventTime = eventTime;
  }
  public String getIMSI() {
    return IMSI;
  }
  public void setIMSI(String iMSI) {
    IMSI = iMSI;
  }
}
