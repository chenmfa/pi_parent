package com.pi.nbcenter.device.bean.dto.nb.iotservice;

public class LockState {
  private String lockVersion;
  private String lockId;
  private int drxRPeriod;
  private int psmRPeriod;
  private int allowUnlockTime;
  private Integer lockState;
  public String getLockVersion() {
    return lockVersion;
  }
  public void setLockVersion(String lockVersion) {
    this.lockVersion = lockVersion;
  }
  public String getLockId() {
    return lockId;
  }
  public void setLockId(String lockId) {
    this.lockId = lockId;
  }
  public int getDrxRPeriod() {
    return drxRPeriod;
  }
  public void setDrxRPeriod(int drxRPeriod) {
    this.drxRPeriod = drxRPeriod;
  }
  public int getPsmRPeriod() {
    return psmRPeriod;
  }
  public void setPsmRPeriod(int psmRPeriod) {
    this.psmRPeriod = psmRPeriod;
  }
  public int getAllowUnlockTime() {
    return allowUnlockTime;
  }
  public void setAllowUnlockTime(int allowUnlockTime) {
    this.allowUnlockTime = allowUnlockTime;
  }
  public Integer getLockState() {
    return lockState;
  }
  public void setLockState(Integer lockState) {
    this.lockState = lockState;
  }
}
