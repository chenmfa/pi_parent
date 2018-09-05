package com.pi.config.service;

import java.text.MessageFormat;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.pi.base.enumerate.record.RecordState;
import com.pi.base.enumerate.redis.RedisCacheEnum;
import com.pi.base.util.cache.RedisUtil;
import com.pi.config.dao.entity.BasePartnerConfigEntity;
import com.pi.config.dao.mapper.BasePartnerConfigMapper;
import com.pi.config.dao.param.BasePartnerConfigParam;
import com.pi.config.enumerate.PartnerConfig;

@Validated
@Service
public class ConfigService {

  @Autowired
  BasePartnerConfigMapper configMapper;
  
  public String queryPartnerConfig(
      @NotNull(message="CONFIG_PARTNER.SOURCE_ID_EMPTY") Long sourceId, 
      @NotNull(message="CONFIG_PARTNER.CONFIG_NAME_EMPTY") PartnerConfig config) 
          throws Exception{
    String configVal = RedisUtil.get(
        MessageFormat.format(
            RedisCacheEnum.SIMPLE_PARTNER_CONFIG.getKey(), sourceId, config.getConfigName()));
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
    RedisUtil.directset(
        MessageFormat.format(RedisCacheEnum.SIMPLE_PARTNER_CONFIG.getKey(), sourceId, config.getConfigName()), 
        configList.get(0).getPartnerConfigValue(), 
        RedisCacheEnum.SIMPLE_PARTNER_CONFIG.getExpired());
    return configList.get(0).getPartnerConfigValue();
  }
}
