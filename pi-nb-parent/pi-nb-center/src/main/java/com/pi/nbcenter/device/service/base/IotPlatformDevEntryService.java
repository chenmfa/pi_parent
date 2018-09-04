package com.pi.nbcenter.device.service.base;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pi.base.exception.ServiceException;
import com.pi.nbcenter.device.entity.auto.IotPlatformDevEntry;
import com.pi.nbcenter.device.mapper.auto.IotPlatformDevEntryMapper;

@Service
public class IotPlatformDevEntryService {
  @Autowired
  private IotPlatformDevEntryMapper iotPlatformDevEntryMapper;
  /**
   * @description 添加平台设备记录
   * @param entry
   * @return
   */
  public long insertDevEntry(IotPlatformDevEntry entry){
    checkEntry(entry);
    Calendar cal = Calendar.getInstance();
    entry.setCreateDate(cal.getTime());
    entry.setUpdateDate(cal.getTime());
    entry.setVersion(200);
    iotPlatformDevEntryMapper.insertSelective(entry);
    return entry.getId();
  }
  
  
  private void checkEntry(IotPlatformDevEntry entry){
    if(null == entry){
      throw new ServiceException("PLATFORM_DEV_ENTRY.ENTRY_IS_EMPTY");
    }
    if(null == entry.getPlatformDevEntry()){
      throw new ServiceException("PLATFORM_DEV_ENTRY.PLATFORM_DEV_ENTRY_IS_EMPTY");
    }
    if(null == entry.getPlatformDevId()){
      throw new ServiceException("PLATFORM_DEV_ENTRY.PLATFORM_ID_IS_EMPTY");
    }
    if(null == entry.getRemark()){
      throw new ServiceException("PLATFORM_DEV_ENTRY.REMARK_IS_EMPTY");
    }
    if(null == entry.getUserId()){
      throw new ServiceException("PLATFORM_DEV_ENTRY.USER_IS_EMPTY");
    }
  }
}
