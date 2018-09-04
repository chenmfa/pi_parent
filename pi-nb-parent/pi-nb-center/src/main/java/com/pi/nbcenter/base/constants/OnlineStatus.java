package com.pi.nbcenter.base.constants;

/**
 * @description 在线状态
 * @author chenmfa
 */
public enum OnlineStatus {
  online(1), offline(0);//在线online, 不在线 offline
  
  private int status;
  
  private OnlineStatus(int status){
    this.status = status;
  }
  public int value(){
    return status;
  }
}
