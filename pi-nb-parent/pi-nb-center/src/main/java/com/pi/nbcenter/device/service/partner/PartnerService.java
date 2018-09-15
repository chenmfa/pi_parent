package com.pi.nbcenter.device.service.partner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pi.base.exception.ServiceException;
import com.pi.base.util.bean.BeanUtil;
import com.pi.nbcenter.base.config.PartnerConfig;
import com.pi.nbcenter.base.constants.ConfigState;
import com.pi.nbcenter.base.errorcode.base.ErrorPartner;
import com.pi.nbcenter.device.entity.auto.BasePartnerConfig;
import com.pi.nbcenter.device.entity.auto.BasePartnerInfo;
import com.pi.nbcenter.device.entity.auto.BasePartnerInfoExample;
import com.pi.nbcenter.device.entity.auto.BasePartnerPlatform;
import com.pi.nbcenter.device.entity.auto.BasePartnerPlatformExample;
import com.pi.nbcenter.device.entity.auto.BasePartnerPlatformExample.Criteria;
import com.pi.nbcenter.device.entity.auto.BasePlatformConfig;
import com.pi.nbcenter.device.mapper.auto.BasePartnerConfigMapper;
import com.pi.nbcenter.device.mapper.auto.BasePartnerInfoMapper;
import com.pi.nbcenter.device.mapper.auto.BasePartnerPlatformMapper;

@Service("partnerService")
public class PartnerService {
  private static final Logger logger = LoggerFactory.getLogger(PartnerService.class);
  @Autowired
  private BasePartnerInfoMapper basePartnerInfoMapper;
  @Autowired
  private BasePartnerConfigMapper basePartnerConfigMapper;
  @Autowired
  private BasePartnerPlatformMapper partnerPlatformMapper;
  @Autowired
  private BasePlatFormService platFormService;
  
  public BasePartnerInfo queryPartnerInfo(Long partnerCode){
    checkPartnerCode(partnerCode);
    BasePartnerInfoExample example = new BasePartnerInfoExample();
    example.createCriteria().andPartnerCodeEqualTo(partnerCode);
    List<BasePartnerInfo> partnerInfoList = basePartnerInfoMapper.selectByExample(example);
    if(null == partnerInfoList || partnerInfoList.size() == 0){
      throw new ServiceException(
          ErrorPartner.PARTNER_INFO_NOT_FOUND.getDesc(),
          ErrorPartner.PARTNER_INFO_NOT_FOUND.getCode());
    }
    return partnerInfoList.get(0);
  }
  
  public Map<String,String> queryPartnerPlatformConfig(
      Long partnerCode, Integer configVersion) throws Exception{
    checkPartnerCode(partnerCode);
    queryPartnerInfo(partnerCode);
    List<BasePartnerPlatform> list = queryPartnerPlatform(partnerCode, configVersion);

    List<BasePlatformConfig> configList = 
        platFormService.queryPlatFormConfig(list.get(0).getPlatformId(), configVersion);
    return wrapPartnerConfigMap(configList, partnerCode);
  }
  
  public <T>T queryPartnerPlatformConfig(
      Long partnerCode, Integer configVersion, Class<T> tClz) throws Exception{
    checkPartnerCode(partnerCode);
    queryPartnerInfo(partnerCode);
    List<BasePartnerPlatform> list = queryPartnerPlatform(partnerCode, configVersion);
    List<BasePlatformConfig> configList = 
        platFormService.queryPlatFormConfig(list.get(0).getPlatformId(), list.get(0).getPlatformVersion());
    if(null == configList || configList.isEmpty()){
      throw new ServiceException("PLATFORM_CONFIG.INVALID_CONFIG_VERSION");
    }
    //转换配置信息到指定类
    return wrapPartnerConfig(configList, tClz, partnerCode);
  }
  
  public List<BasePartnerPlatform> queryPartnerPlatform(
      Long partnerCode, Integer configVersion){
    BasePartnerPlatformExample example = new BasePartnerPlatformExample();
    Criteria ct = example.createCriteria();
    ct.andPartnerCodeEqualTo(partnerCode);
    if(null != configVersion){      
      ct.andPlatformVersionEqualTo(configVersion);
    }
//    根据平台版本排序默认选择最新的版本, 如果有指定平台版本号,则查询指定版本的配置
    example.setOrderByClause(" platform_version DESC");
    List<BasePartnerPlatform> list = partnerPlatformMapper.selectByExample(example);
    if(null == list || list.size() == 0){
      throw new ServiceException("PARTNER_INFO.PARTNER_PLATFORM_NOT_FOUND");
    }
    return list;
  }
  
  public void addPartnerConfig(Long partnerCode, String notifyType, String remoteUri){
    BasePartnerInfo partner = queryPartnerInfo(partnerCode);
    if(null == partner){
      throw new ServiceException("PARTNER_INFO.PARTNER_INFO_NOT_FOUND");
    }
    List<BasePartnerConfig> list = new ArrayList<BasePartnerConfig>();
    BasePartnerConfig conifg = new BasePartnerConfig();
    conifg.setPartnerCode(partnerCode);
    conifg.setPartnerConfig(PartnerConfig.CONFIG_NOTIFY_TYPE.getConfigName());
    conifg.setPartnerConfigValue(notifyType);
    conifg.setConfigState(ConfigState.CONFIG_ADDED.getState());
    BasePartnerConfig conifg2 = new BasePartnerConfig();
    conifg2.setPartnerCode(partnerCode);
    conifg2.setPartnerConfig(PartnerConfig.CONFIG_NOTIFY_TYPE.getConfigName());
    conifg2.setPartnerConfigValue(notifyType);
    conifg2.setConfigState(ConfigState.CONFIG_ADDED.getState());
    for(BasePartnerConfig setting:list){
      basePartnerConfigMapper.insertSelective(setting);
    }
  }
  
  public void checkPartnerCode(Long partnerCode){
    if(null == partnerCode || partnerCode <= 0){
      throw new ServiceException("PARTNER_INFO.PARTNER_CODE_EMPTY");
    }
  }
  
  private <T> T wrapPartnerConfig(
      List<BasePlatformConfig> configList, Class<T> tClz, long partnerCode)
      throws Exception{
    if(null == configList || configList.size() == 0){
      logger.error("合作商{}的配置信息为空", partnerCode);
      return null;
    }
    if(null != tClz){
      T newInstance = tClz.newInstance();
      for(BasePlatformConfig config:configList){
        BeanUtil.setProperty(newInstance, config.getConfigKey(), config.getConfigValue());
      }
      BeanUtil.setProperty(newInstance, "version", 
          configList.get(0).getConfigVersion());
      return newInstance;
    }
    return null;
  }
  private Map<String, String> wrapPartnerConfigMap(
      List<BasePlatformConfig> configList, long partnerCode)
      throws Exception{
    if(null == configList || configList.size() >= 0){
      logger.error("合作商{}的配置信息为空", partnerCode);
      return null;
    }
    Map<String, String> configMap = new HashMap<String, String>();
    for(BasePlatformConfig config:configList){
      configMap.put(config.getConfigKey(), config.getConfigValue());
    }
    return configMap;
  }
}
