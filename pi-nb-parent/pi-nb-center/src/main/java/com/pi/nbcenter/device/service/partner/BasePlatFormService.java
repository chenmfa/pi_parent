package com.pi.nbcenter.device.service.partner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pi.base.exception.ServiceException;
import com.pi.nbcenter.base.errorcode.iot.ErrorCenterPlatform;
import com.pi.nbcenter.device.entity.auto.BasePlatformConfig;
import com.pi.nbcenter.device.entity.auto.BasePlatformConfigExample;
import com.pi.nbcenter.device.entity.auto.BasePlatformInfo;
import com.pi.nbcenter.device.mapper.auto.BasePlatformConfigMapper;
import com.pi.nbcenter.device.mapper.auto.BasePlatformInfoMapper;

@Service("platFormService")
public class BasePlatFormService {
  @Autowired
  BasePlatformInfoMapper platformInfoMapper;
  @Autowired
  BasePlatformConfigMapper platformConfigMapper;
  
  public List<BasePlatformConfig> queryPlatFormConfig(Integer platformId, Integer configVersion){
    checkPlatformConfigQuery(platformId, configVersion);
    BasePlatformInfo info = platformInfoMapper.selectByPrimaryKey(platformId);
    if(null == info){
      throw new ServiceException("PLATFORM_CONFIG.PLATFORM_NOT_EXIST");
    }
    BasePlatformConfigExample example = new BasePlatformConfigExample();
    example.createCriteria().andConfigVersionEqualTo(configVersion)
    .andPlatformIdEqualTo(platformId).andConfigStateEqualTo(1);
    List<BasePlatformConfig> list= platformConfigMapper.selectByExample(example);
    if(null == list || list.size() == 0){
      throw new ServiceException(
          ErrorCenterPlatform.PLATFORM_CONFIG_NOT_EXIST.getDesc(),
          ErrorCenterPlatform.PLATFORM_CONFIG_NOT_EXIST.getCode());
    }
    return list;
  }
  
  
  public Map<String, Map<String,String>> queryAllPlatformConfig(){
    BasePlatformConfigExample example = new BasePlatformConfigExample();
    example.createCriteria().andConfigStateEqualTo(1);
    List<BasePlatformConfig> list = platformConfigMapper.selectByExample(example);
    Map<String, Map<String,String>> platformConfigMap= new HashMap<String, Map<String,String>>();
    if(null != list && list.size() > 0 ){
      for(BasePlatformConfig config: list){
        String key = config.getPlatformId() + "_" + config.getConfigVersion();
        if(!platformConfigMap.containsKey(key)){
          Map<String,String> singleMap = new HashMap<String,String>();
          singleMap.put(config.getConfigKey(), config.getConfigValue());
          platformConfigMap.put(key, singleMap);
        }else{
          platformConfigMap.get(key).put(config.getConfigKey(), config.getConfigValue());
        }
      }
    }
    return platformConfigMap;
  }
  
  public void checkPlatformConfigQuery(Integer platformId, Integer configVersion){
    if(null == platformId || platformId <= 0){
      throw new ServiceException("PLATFORM_CONFIG.UNKNOWN_PLATFORM");
    }
    if(null == configVersion || configVersion <= 0){
      throw new ServiceException("PLATFORM_CONFIG.INVALID_CONFIG_VERSION");
    }
  }
}
