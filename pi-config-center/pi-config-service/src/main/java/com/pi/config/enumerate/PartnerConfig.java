package com.pi.config.enumerate;

public enum PartnerConfig {
  WX_MINI_APPID("WX_MINI_APPID", "微信APPID"),
  WX_MINI_APPSECRET("WX_MINI_APPSECRET", "微信APPSECRET")
	;
	private String config;
	private String desc;
	private PartnerConfig(String config, String desc) {
		this.config = config;
		this.desc = desc;
	}
	public String getConfigName(){
		return config;
	}
	public String getDesc() {
		return desc;
	}
}
