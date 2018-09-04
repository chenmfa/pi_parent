package com.pi.nbcenter.base.constants;

public enum IotNotifyType {
  DEVICE_DATA_CHANGED("deviceDataChanged", "实时数据变化"),
  DEVICE_DATAS_CHANGED("deviceDatasChanged", "实时数据批量变化")
  ;
  
  private String notifyType;//deviceDataChanged
  private String desc;
  private IotNotifyType(String notifyType, String desc) {
    this.notifyType = notifyType;
    this.desc = desc;
  }
  public String getNotifyType() {
    return notifyType;
  }
  public String getDesc() {
    return desc;
  }
  public static IotNotifyType getIotNotifyType(String notifyType){
    if(null != notifyType){
      for(IotNotifyType type:IotNotifyType.values()){
        if(type.getNotifyType().equalsIgnoreCase(notifyType)){
          return type;
        }
      }
    }
    return null;
  }
}
