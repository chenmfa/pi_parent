package com.pi.nbcenter.base.config;

public enum PartnerConfig {
  CONFIG_NOTIFY_TYPE("CONFIG_NOTIFY_TYPE", "通知类型"),
  CONFIG_NOTIFY_URL("CONFIG_NOTIFY_URL", "通知地址");
  private String configName;//编号
  private String configVal;//描述
  private PartnerConfig(String configName, String configVal) {
    this.configName = configName;
    this.configVal = configVal;
  }
  public String getConfigName() {
    return configName;
  }
  public String getConfigVal() {
    return configVal;
  }
}
