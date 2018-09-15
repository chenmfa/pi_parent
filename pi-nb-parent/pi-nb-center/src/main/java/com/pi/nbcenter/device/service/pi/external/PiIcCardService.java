package com.pi.nbcenter.device.service.pi.external;

import com.pi.nbcenter.device.service.comm.BaseServiceProvoder;
import com.pi.nbcenter.device.service.provider.IcCardService;

public class PiIcCardService extends BaseServiceProvoder{
  public Long sendIcCard(String imei, Long userId, String icCard, Long sourceId)
      throws Exception{
    IcCardService provider = queryDeviceServiceProvider(
        imei, null, IcCardService.class);
    return provider.sendIcCard(imei, userId, icCard, sourceId);
  }
  public void deleteIcCard(String imei, Long userId, String icCard, Long sourceId)
      throws Exception{
    IcCardService provider = queryDeviceServiceProvider(
        imei, null, IcCardService.class);
    provider.deleteIcCard(imei, userId, icCard, sourceId);
  }
}
