package com.pi.nbcenter.device.service.comm;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.pi.base.exception.ServiceException;
import com.pi.base.util.clz.ClassUtil;
import com.pi.nbcenter.base.annotation.Partner;
import com.pi.nbcenter.base.consistency.SpringContextHolder;
import com.pi.nbcenter.base.errorcode.base.ErrorPartner;
import com.pi.nbcenter.base.errorcode.iot.ErrorIOTDev;
import com.pi.nbcenter.device.entity.auto.BasePartnerInfo;
import com.pi.nbcenter.device.entity.auto.IotDeviceInfo;
import com.pi.nbcenter.device.service.partner.PartnerService;
import com.pi.nbcenter.device.service.pi.internal.PiIotDevService;

public class BaseServiceProvoder {
  @Autowired
  private PiIotDevService iotDevService;
  @Autowired
  PartnerService partnerService;
  
  protected <T>T queryDeviceServiceProvider(String imei, Long partnerCode, Class<T> tClz){
    if(StringUtils.isBlank(imei)){
      throw new ServiceException(
          ErrorIOTDev.DEV_IMEI_EMPTY.getKey(),
          ErrorIOTDev.DEV_IMEI_EMPTY.getCode());
    }
    BasePartnerInfo partnerInfo;
    if(null == partnerCode){
      IotDeviceInfo iotDevInfo = iotDevService.queryDbDeviceInfoByImei(imei);
      partnerCode = iotDevInfo.getPartnerCode();
    }
    partnerInfo = partnerService.queryPartnerInfo(partnerCode);
    Map<String, T> providers = 
        SpringContextHolder.getApplicationContext().getBeansOfType(tClz);
    for(Map.Entry<String, T> entry:providers.entrySet()){
      T provider = entry.getValue();
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
