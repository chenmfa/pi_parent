package com.pi.nbcenter.device.service.provider;

import com.pi.base.dto.result.AppResult;
import com.pi.nbcenter.device.bean.dto.session.IotDevInstantInfo;

public interface CommonDeviceService {
  public AppResult registerDevice(String imei, Long partnerCode, String nbDevId) throws Exception;
  public IotDevInstantInfo queryDeviceStatus(String imei);
  public AppResult remoteOpenLock(String imei) throws Exception;
}
