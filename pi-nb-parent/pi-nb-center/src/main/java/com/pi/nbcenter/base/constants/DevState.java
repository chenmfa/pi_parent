package com.pi.nbcenter.base.constants;

public enum DevState {
  DEV_ADDED((byte)10, " 已添加"),
  DEV_DELETED((byte)20," 已删除"),
  DEV_EXPIRED((byte)50," 已失效"),
  ;
  private byte state;
  private String desc;
  private DevState(byte state, String desc) {
    this.state = state;
    this.desc = desc;
  }
  public byte getState() {
    return state;
  }
  public String getDesc() {
    return desc;
  }
}
