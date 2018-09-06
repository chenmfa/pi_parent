package com.pi.config.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.pi.base.dto.result.respcode.config.ConfigResp;
import com.pi.base.enumerate.record.RecordState;
import com.pi.base.enumerate.redis.RedisCacheEnum;
import com.pi.base.exception.ServiceException;
import com.pi.base.util.cache.RedisUtil;
import com.pi.config.dao.entity.BasePartnerConfigEntity;
import com.pi.config.dao.mapper.BasePartnerConfigMapper;
import com.pi.config.dao.param.BasePartnerConfigParam;
import com.pi.config.enumerate.PartnerConfig;
import com.pi.config.model.PartnerAppConfig;

@Validated
@Service
public class ConfigService {
  private static final Logger logger = LoggerFactory.getLogger(ConfigService.class);
  @Autowired
  BasePartnerConfigMapper configMapper;
  
  public PartnerAppConfig queryPartnerAppConfig(@NotNull(message="CONFIG_PARTNER.SOURCE_ID_EMPTY") Long sourceId)
      throws Exception{
    String appId = queryPartnerConfig(sourceId, PartnerConfig.WX_MINI_APPID);
    if(null == appId){
      assertConfigException(ConfigResp.RESP_CONFIG_APP_ID_NOT_FOUND); 
    }
    String appSecret = queryPartnerConfig(sourceId, PartnerConfig.WX_MINI_APPSECRET);
    if(null == appSecret){
      assertConfigException(ConfigResp.RESP_CONFIG_APP_SECRET_NOT_FOUND); 
    }
    PartnerAppConfig config = new PartnerAppConfig();
    config.setAppId(appId);
    config.setAppSecret(appSecret);
    return config;
  }
  
  public String queryPartnerConfig(
      @NotNull(message="CONFIG_PARTNER.SOURCE_ID_EMPTY") Long sourceId, 
      @NotNull(message="CONFIG_PARTNER.CONFIG_NAME_EMPTY") PartnerConfig config) 
          throws Exception{
    String configVal = RedisUtil.get(
        RedisCacheEnum.SIMPLE_PARTNER_CONFIG,sourceId, config.getConfigName());
    if(StringUtils.isBlank(configVal)){
      return queryPartnerConfigFromDb(sourceId, config);
    }
    return configVal;
  }
  
  public String queryPartnerConfigFromDb(
      @NotNull(message="CONFIG_PARTNER.SOURCE_ID_EMPTY") Long sourceId, 
      @NotNull(message="CONFIG_PARTNER.CONFIG_NAME_EMPTY") PartnerConfig config){
    BasePartnerConfigParam param = new BasePartnerConfigParam();
    param.setConfigState(RecordState.STATE_NORMAL.getCode());
    param.setPartnerCode(sourceId);
    param.setPartnerConfig(config.getConfigName());
    List<BasePartnerConfigEntity> configList = configMapper.findList(param);
    if(null == configList || configList.isEmpty()){
      return null;
    }
    wrapConfigIntoCache(configList.get(0));
    return configList.get(0).getPartnerConfigValue();
  }
  
  public List<BasePartnerConfigEntity> queryAllConfig(){
    BasePartnerConfigParam param = new BasePartnerConfigParam();
    param.setConfigState(RecordState.STATE_NORMAL.getCode());
    List<BasePartnerConfigEntity> configList = configMapper.findList(param);
    return configList;
  }
  
  @PostConstruct
  private void init(){
    List<BasePartnerConfigEntity> configList = queryAllConfig();
    if(null == configList || configList.isEmpty()){
      logger.error("未查询到全局配置信息");
      return;
    }
    for(BasePartnerConfigEntity entity:configList){
      wrapConfigIntoCache(entity);
    }
  }
  
  private void wrapConfigIntoCache(BasePartnerConfigEntity entity){
    RedisUtil.directset(
        RedisCacheEnum.SIMPLE_PARTNER_CONFIG, 
        entity.getPartnerConfigValue(),
        entity.getPartnerCode(), entity.getPartnerConfig()
        );
  }
  
  private void assertConfigException(ConfigResp resp){
    throw new ServiceException(resp.getTag(), resp.getErrorCode());
  }
}
