package com.pi.config.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.pi.base.enumerate.record.RecordState;
import com.pi.base.util.cache.RedisUtil;
import com.pi.config.constant.ConfigConstant;
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
    RedisUtil.get(sourceId + config.getConfigName());
    return null;
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
        sourceId + config.getConfigName(), 
        configList.get(0).getPartnerConfigValue(), 
        ConfigConstant.DEFAULT_CONFIG_EXPIRE);
    return configList.get(0).getPartnerConfigValue();
  }
  
  @PostConstruct
  private void init(){
    List<BasePartnerConfigEntity> configList = queryPartnerConfigFromDb();
    if(null == configList || configList.isEmpty()){
      return;
    }
    for(BasePartnerConfigEntity entity: configList){
      RedisUtil.directset(entity.getPartnerConfig(), value, expried)
    }
  }
}
