package com.pi.nbcenter.device.service.pi.external;

import org.springframework.stereotype.Service;

import com.pi.base.dto.result.AppResult;
import com.pi.nbcenter.device.bean.dto.session.IotDevInstantInfo;
import com.pi.nbcenter.device.service.comm.BaseServiceProvoder;
import com.pi.nbcenter.device.service.provider.CommonDeviceService;

@Service("globalDeviceService")
public class PiGlobalDeviceService extends BaseServiceProvoder{
  public AppResult registerDevice(String imei, Long partnerCode, String nbDevId)
      throws Exception{
    CommonDeviceService  provider = queryDeviceServiceProvider(
        imei, partnerCode, CommonDeviceService.class);
    return provider.registerDevice(imei, partnerCode, nbDevId);
  }
  public IotDevInstantInfo queryDeviceStatus(String imei){
    CommonDeviceService  provider = queryDeviceServiceProvider(
        imei, null, CommonDeviceService.class);
    return provider.queryDeviceStatus(imei);
  }
  public AppResult remoteOpenLock(String imei) throws Exception{
    CommonDeviceService provider = queryDeviceServiceProvider(
        imei, null, CommonDeviceService.class);
    return provider.remoteOpenLock(imei);
  }
}
