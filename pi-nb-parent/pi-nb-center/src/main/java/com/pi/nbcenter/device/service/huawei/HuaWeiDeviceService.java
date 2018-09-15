package com.pi.nbcenter.device.service.huawei;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.pi.base.dto.result.AppResult;
import com.pi.nbcenter.device.bean.dto.partner.IotPartnerConfig;
import com.pi.nbcenter.device.bean.dto.session.IotDevInstantInfo;
import com.pi.nbcenter.device.entity.auto.IotDeviceInfo;
import com.pi.nbcenter.device.service.partner.PartnerService;
import com.pi.nbcenter.device.service.pi.internal.PiIotDevService;
import com.pi.nbcenter.device.service.provider.CommonDeviceService;
import com.pi.nbcenter.device.service.provider.IcCardService;
import com.pi.nbcenter.util.nb.HuaWeiIotService;

@Validated
@Service("HuaWeiIot")
public class HuaWeiDeviceService implements CommonDeviceService,IcCardService{
  @Autowired
  private HuaWeiIotService huaWeiPlatformService;
  @Autowired
  private PiIotDevService piIotDevService;
  @Autowired
  private PartnerService partnerService;
  @Override
  public Long sendIcCard(
      @NotNull(message = "HUAWEI_DEVICE.IC_CARD_DEVICE_ID_EMPTY") String imei, 
      @NotNull(message = "HUAWEI_DEVICE.IC_CARD_USER_NOT_EMPTY") Long userId, 
      @Length(max=10, message = "HUAWEI_DEVICE.IC_CARD_OVER_MAX") String icCard, 
      @NotNull(message = "HUAWEI_DEVICE.IC_CARD_SOURCE_EMPTY") Long sourceId) 
          throws Exception {
    IotDeviceInfo iotDevice = piIotDevService.queryDbDeviceInfoByImei(imei, sourceId);
    IotPartnerConfig partnerConfig = 
        partnerService.queryPartnerPlatformConfig(sourceId, 
            iotDevice.getIotProtocolVersion(), IotPartnerConfig.class);
    //查询ic卡是否已绑定
    
    huaWeiPlatformService.sendIcCard(iotDevice.getIotDevId(), userId, icCard,
        partnerConfig.getAppId(), partnerConfig.getAppSecret());
    return null;
  }

  @Override
  public void deleteIcCard(
      @NotNull(message = "HUAWEI_DEVICE.IC_CARD_DEVICE_ID_EMPTY") String imei, 
      @NotNull(message = "HUAWEI_DEVICE.IC_CARD_USER_NOT_EMPTY") Long userId, 
      @Length(max=10, message = "HUAWEI_DEVICE.IC_CARD_OVER_MAX") String icCard,
      @NotNull(message = "HUAWEI_DEVICE.IC_CARD_SOURCE_EMPTY") Long sourceId)
      throws Exception {
    IotDeviceInfo iotDevice = piIotDevService.queryDbDeviceInfoByImei(imei, sourceId);
    IotPartnerConfig partnerConfig = 
        partnerService.queryPartnerPlatformConfig(sourceId, 
            iotDevice.getIotProtocolVersion(), IotPartnerConfig.class);
    //查询ic卡是否已绑定
    
    huaWeiPlatformService.deleteIcCard(iotDevice.getIotDevId(), userId, icCard,
        partnerConfig.getAppId(), partnerConfig.getAppSecret());
  }

  @Override
  public AppResult registerDevice(String imei, Long partnerCode, String nbDevId) throws Exception {
    return null;
  }

  @Override
  public IotDevInstantInfo queryDeviceStatus(String imei) {
    return null;
  }

  @Override
  public AppResult remoteOpenLock(String imei) throws Exception {
    return null;
  }

}
