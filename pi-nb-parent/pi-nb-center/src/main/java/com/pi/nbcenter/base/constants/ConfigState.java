package com.pi.nbcenter.base.constants;

public enum ConfigState {
  CONFIG_ADDED(10, " 已添加"),
  CONFIG_DELETED(20," 已删除"),
  CONFIG_EXPIRED(50," 已失效"),
  ;
  private int state;
  private String desc;
  private ConfigState(int state, String desc) {
    this.state = state;
    this.desc = desc;
  }
  public int getState() {
    return state;
  }
  public String getDesc() {
    return desc;
  }
}
