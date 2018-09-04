package com.pi.nbcenter.device.service;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pi.base.dto.result.AppResult;
import com.pi.base.exception.ServiceException;
import com.pi.base.util.clz.ClassUtil;
import com.pi.nbcenter.base.annotation.Partner;
import com.pi.nbcenter.base.consistency.SpringContextHolder;
import com.pi.nbcenter.base.errorcode.base.ErrorPartner;
import com.pi.nbcenter.base.errorcode.iot.ErrorIOTDev;
import com.pi.nbcenter.device.bean.dto.session.IotDevInstantInfo;
import com.pi.nbcenter.device.entity.auto.BasePartnerInfo;
import com.pi.nbcenter.device.entity.auto.IotDeviceInfo;
import com.pi.nbcenter.device.service.base.PartnerService;
import com.pi.nbcenter.device.service.iot.IotDevService;
import com.pi.nbcenter.device.service.provider.CommonDeviceProvider;

@Service("commonDeviceService")
public class CommonDeviceService {
  @Autowired
  private IotDevService iotDevService;
  @Autowired
  PartnerService partnerService;
  
  public AppResult registerDevice(String imei, Long partnerCode, String nbDevId)
      throws Exception{
    CommonDeviceProvider  provider = queryCommonDeviceProvider(imei, partnerCode);
    return provider.registerDevice(imei, partnerCode, nbDevId);
  }
  public IotDevInstantInfo queryDeviceStatus(String imei){
    CommonDeviceProvider  provider = queryCommonDeviceProvider(imei, null);
    return provider.queryDeviceStatus(imei);
  }
  public AppResult remoteOpenLock(String imei) throws Exception{
    CommonDeviceProvider provider = queryCommonDeviceProvider(imei, null);
    return provider.remoteOpenLock(imei);
  }
  
  private CommonDeviceProvider queryCommonDeviceProvider(String imei, Long partnerCode){
    if(StringUtils.isBlank(imei)){
      throw new ServiceException(
          ErrorIOTDev.DEV_IMEI_EMPTY.getKey(),
          ErrorIOTDev.DEV_IMEI_EMPTY.getCode());
    }
    BasePartnerInfo partnerInfo;
    if(null == partnerCode){
      IotDeviceInfo iotDevInfo = iotDevService.queryIotDeviceInfoByImei(imei);
      if(null == iotDevInfo){
        throw new ServiceException(
            ErrorIOTDev.DEV_INFO_EMPTY.getKey(),
            ErrorIOTDev.DEV_INFO_EMPTY.getCode());
      }
      partnerCode = iotDevInfo.getPartnerCode();
    }
    partnerInfo = partnerService.queryPartnerInfo(partnerCode);
    Map<String, CommonDeviceProvider> providers = 
        SpringContextHolder.getApplicationContext().getBeansOfType(CommonDeviceProvider.class);
    for(Map.Entry<String, CommonDeviceProvider> entry:providers.entrySet()){
      CommonDeviceProvider provider = entry.getValue();
      Partner partnerAnno = ClassUtil.getAnnotationOnClass(provider.getClass(), Partner.class);
      if(null != partnerAnno){
        String[] names = partnerAnno.name();
        if(null != names && names.length > 0){
          for(String name: names){
            if(name.equalsIgnoreCase(partnerInfo.getPartnerAlias()))
              return provider;
          }
        }
      }
    }
    throw new ServiceException(
        ErrorPartner.NO_PARTNER_SERVICE_PROVIDED.getKey(),
        ErrorPartner.NO_PARTNER_SERVICE_PROVIDED.getCode());
  }
}
