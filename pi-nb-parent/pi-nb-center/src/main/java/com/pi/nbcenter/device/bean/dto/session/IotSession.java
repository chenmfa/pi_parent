package com.pi.nbcenter.device.bean.dto.session;

import java.util.Date;

public class IotSession extends IotDevInstantInfo{
  private Date latestActiveTime;
  private Integer iotDevState;
  public Date getLatestActiveTime() {
    return latestActiveTime;
  }
  public void setLatestActiveTime(Date latestActiveTime) {
    this.latestActiveTime = latestActiveTime;
  }
  public Integer getIotDevState() {
    return iotDevState;
  }
  public void setIotDevState(Integer iotDevState) {
    this.iotDevState = iotDevState;
  }
}
