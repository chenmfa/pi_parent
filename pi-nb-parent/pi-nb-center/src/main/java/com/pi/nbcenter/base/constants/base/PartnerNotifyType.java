package com.pi.nbcenter.base.constants.base;

public enum PartnerNotifyType {
  HTTP_POST(101, "HTTP_POST", "HTTP_POST订阅"),
  TCP(102, "TCP", "TCP订阅"),
  UDP(103, "UDP", "UDP订阅"),
  ;
  private int code;
  private String name;
  private String desc;
  PartnerNotifyType(int code, String name, String desc) {
    this.code = code;
    this.name = name;
    this.desc = desc;
  }
  public int getCode() {
    return code;
  }
  public String getName() {
    return name;
  }
  public String getDesc() {
    return desc;
  }
  public static PartnerNotifyType getNotifyType(Integer code){
    if(null != code)
      for(PartnerNotifyType notifyType :PartnerNotifyType.values()){
        if(notifyType.getCode() == code)
          return notifyType;
      }
    return null;
  }
}
