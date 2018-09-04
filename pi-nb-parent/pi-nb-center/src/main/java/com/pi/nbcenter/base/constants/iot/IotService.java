package com.pi.nbcenter.base.constants.iot;

public enum IotService {
  CONNECTIVITY("Connectivity", "信号"),
  BATTERY("Battery", "电量"),
  LOCK_STATE("Lock", "锁具信息")
  ;
  private String serviceId;
  private String serviceName;
  
  private IotService(String serviceId, String serviceName){
    this.serviceId = serviceId;
    this.serviceName = serviceName;
  }
  public String getServiceId() {
    return serviceId;
  }
  public String getServiceName() {
    return serviceName;
  }
  public static IotService getIotService(String serviceId){
    if(null != serviceId){
      for(IotService service: IotService.values()){
        if(service.getServiceId().equalsIgnoreCase(serviceId)){
          return service;
        }
      }
    }
    return null;
  }
}
