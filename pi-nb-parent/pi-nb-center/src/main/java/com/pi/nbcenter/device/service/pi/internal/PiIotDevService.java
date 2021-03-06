package com.pi.nbcenter.device.service.pi.internal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pi.base.contants.SymbolConstants;
import com.pi.base.dto.result.AppResult;
import com.pi.base.exception.ServiceException;
import com.pi.base.util.lang.IntegerUtil;
import com.pi.base.util.time.DateTools;
import com.pi.nbcenter.base.annotation.Partner;
import com.pi.nbcenter.base.constants.BatteryStatus;
import com.pi.nbcenter.base.constants.DevState;
import com.pi.nbcenter.base.constants.OnlineStatus;
import com.pi.nbcenter.base.constants.RssiStrength;
import com.pi.nbcenter.base.errorcode.base.ErrorPartner;
import com.pi.nbcenter.base.errorcode.iot.ErrorIOTDev;
import com.pi.nbcenter.base.errorcode.iot.ErrorIOTPlatform;
import com.pi.nbcenter.device.bean.dto.nb.IotDeviceInfoDTO;
import com.pi.nbcenter.device.bean.dto.partner.IotPartnerConfig;
import com.pi.nbcenter.device.bean.dto.session.IotDevInstantInfo;
import com.pi.nbcenter.device.bean.dto.session.IotSession;
import com.pi.nbcenter.device.controller.iot.facade.DeviceCallBackFacade;
import com.pi.nbcenter.device.entity.auto.IotDeviceInfo;
import com.pi.nbcenter.device.entity.auto.IotDeviceInfoExample;
import com.pi.nbcenter.device.entity.auto.IotDeviceSession;
import com.pi.nbcenter.device.entity.auto.IotDeviceSessionExample;
import com.pi.nbcenter.device.entity.auto.IotPlatformDevEntry;
import com.pi.nbcenter.device.mapper.auto.IotDeviceInfoMapper;
import com.pi.nbcenter.device.mapper.auto.IotDeviceSessionMapper;
import com.pi.nbcenter.device.service.partner.PartnerService;
import com.pi.nbcenter.device.service.provider.CommonDeviceService;
import com.pi.nbcenter.util.comm.ObjectUtil;
import com.pi.nbcenter.util.nb.HuaWeiIotService;


/**
 * @description 这个是设备中心的公共基础类，当调用其他平台操作完成时, 调用此接口回填数据库
 * @author chenmfa
 */
@Partner(name={"HDU", "SNAIL", "JINHUA_TELECOM", "TIANJIANG", "GUANG" })
@Service("nbService")
public class PiIotDevService implements CommonDeviceService{
  private static final Logger logger = LoggerFactory.getLogger(PiIotDevService.class);
 
  @Autowired
  private IotDeviceInfoMapper iotDeviceInfoMapper;
  @Autowired
  private IotDeviceSessionMapper iotDeviceSessionMapper;
  @Autowired
  private PartnerService partnerService;
  @Autowired
  private DeviceCallBackFacade deviceCallBackFacade;
  @Autowired
  private IotPlatformDevEntryService iotPlatformDevEntryService;
  @Autowired
  private HuaWeiIotService iotPlatService;
  /**
   * @description 设备添加
   * @param imei 设备IMEI
   * @param partnerCode 设备来源
   * @param nbDevId 设备编号
   * @throws Exception 
   */
  @Transactional
  public AppResult registerDevice(String imei, Long partnerCode, String nbDevId) throws Exception{
    AppResult result;
    checkRegisterDevice(imei, partnerCode, nbDevId);
    partnerService.checkPartnerCode(partnerCode);
    checkIotDevId(nbDevId);
    partnerService.queryPartnerInfo(partnerCode);
    String iotDeviceId = null;
    Integer configVersion;
    //查询设备是否注册
    IotDeviceInfo info =  queryDbDeviceInfoByImeiIfExist(imei);
    if(null != info){
      //注销平台设备
      cleanDeviceInfo(info.getIotDevId());
      configVersion = info.getIotProtocolVersion();
      if(info.getPartnerCode().longValue() != partnerCode){
        configVersion = null;
      }
    }else{
      info = new IotDeviceInfo();
      configVersion = null;
    }
    IotPartnerConfig config = 
        partnerService.queryPartnerPlatformConfig(partnerCode, configVersion, IotPartnerConfig.class);
    try {
      iotDeviceId = iotPlatService.register(imei, config.getAppId(), config.getAppSecret());
      if(StringUtils.isEmpty(iotDeviceId)){
        result = AppResult.newFailResult(
            ErrorIOTDev.REG_DEV_FROM_PLAT_FAILED.getKey(),
            ErrorIOTDev.REG_DEV_FROM_PLAT_FAILED.getCode());
      }else{
        info.setIotDevId(iotDeviceId);
        //注册厂商信息
        boolean succeed = iotPlatService.updateDevice(iotDeviceId, "", config);
        if(!succeed){
          result = AppResult.newFailResult(ErrorIOTDev.REG_MANUFACTURER_FAILED.getKey(),
              ErrorIOTDev.REG_MANUFACTURER_FAILED.getCode());
        }else{
          //设置证书
          info.setIotDevRegcode(Integer.parseInt(IntegerUtil.generateRandomCode(9))+1000000000);
          iotPlatService.setCert(iotDeviceId, info.getIotDevRegcode(), "ge/ad8XT1takmr4STDqV5A==",
              config.getAppId(), config.getAppSecret());
          info.setIotDevImei(imei);
          info.setCreateDate(new Date());
          info.setNbDevId(nbDevId);
  //            info.setOnlineStatus(OnlineStatus.online.value());
          info.setPartnerCode(partnerCode);
          info.setState(DevState.DEV_ADDED.getState());
          info.setIotDevCert("ge/ad8XT1takmr4STDqV5A==");
          info.setIotProtocolVersion(config.getConfigVersion());
          addDeviceSmart(info);
          
          //注册在线信息
          IotDeviceSession session = new IotDeviceSession();
          session.setIotDevBattery(0);
          session.setIotDevId(iotDeviceId);
          session.setIotDevIp("");
          session.setIotDevPort(0);
          session.setIotDevRssi(RssiStrength.ZERO.value());
          session.setIotDevSeckey(null);
          session.setIotDevState(OnlineStatus.online.value());
  //        在线信息添加开始
          addDeviceSession(session);
          
          result = AppResult.OK;
        }
      }
    } catch (Exception e) {
      //对于注册成功但是添加失败或者其他异常情况，需要清空设备表数据
      if(null != iotDeviceId){
        try {
          iotPlatService.deleteDevice(iotDeviceId, config.getAppId(), config.getAppSecret());
        } catch (Exception e1) {
          logger.error("注销平台设备失败",e);
        }
      }
      throw e;
    }
    return result;
  }
  
  public Long addDeviceSmart(IotDeviceInfo info){
    checkIotDevIngo(info);
    Calendar cal = Calendar.getInstance();
    info.setUpdateDate(cal.getTime());
    if(null != info.getId()){
      info.setVersion(info.getVersion() + 1);
      iotDeviceInfoMapper.updateByPrimaryKeySelective(info);
      return info.getId();
    }
    IotDeviceInfo deviceInfo = queryDbDeviceInfoByImeiIfExist(info.getIotDevImei());
    if(null != deviceInfo){
      //需要更新
      info.setId(deviceInfo.getId());
      info.setVersion(info.getVersion() + 1);
      iotDeviceInfoMapper.updateByPrimaryKeySelective(info);
    }else{
      //重新添加
      info.setCreateDate(cal.getTime());
      iotDeviceInfoMapper.insertSelective(info);
    }
    return info.getId();
  }
  
  /**
   * @description 添加设备状态信息
   * @param iotDevId
   * @param status
   */
  public Long addDeviceSession(IotDeviceSession session){
    checkSession(session);
    IotDeviceSession querySession = queryDbDeviceSession(session.getIotDevId());
    if(null != querySession){
      updateSession(session);
      return querySession.getId();
    }else{
      Calendar cal = Calendar.getInstance();
      session.setLatestActiveTime(cal.getTime());
      session.setCreateDate(cal.getTime());
      session.setUpdateDate(cal.getTime());
      iotDeviceSessionMapper.insertSelective(session);
      return session.getId();
    }
  }
  
  /**
   * @description 更新设备状态(如果没有,则新增-用于消息返回时更新设备状态)
   * @param iotDevId
   * @param status
   */
  public void updateDeviceSession(String iotDevId, int status){
    if(StringUtils.isNotEmpty(iotDevId)){
      if(status != OnlineStatus.online.value()){
        status =  OnlineStatus.offline.value();
      }
      IotDeviceSession updated = new IotDeviceSession();
      updated.setIotDevId(iotDevId);
      updated.setIotDevState(status);
      updateDeviceSession(updated);
    }
  }
  /**
   * @description 更新设备状态(如果没有,则新增-用于消息返回时更新设备状态)
   * @param iotDevId
   * @param status
   */
  public void updateDeviceSession(IotDeviceSession updated){
    checkSession(updated);
    IotDeviceSession session = queryDbDeviceSession(updated.getIotDevId());
    updated.setIotDevRssi(null != updated.getIotDevRssi()?updated.getIotDevRssi():null);
    updated.setIotDevBattery(null != updated.getIotDevBattery()?updated.getIotDevBattery():null);
    updated.setIotDevIp("");
    updated.setIotDevPort(0);
    updated.setIotDevSeckey(null);
    Calendar cal = Calendar.getInstance();
    updated.setUpdateDate(cal.getTime());
    if(null == session){
      //未知设备，添加一条记录
      if(null == updated.getIotDevState()){
        updated.setIotDevState(OnlineStatus.online.value());
      }
      if(null == updated.getIotDevRssi()){
        updated.setIotDevRssi(RssiStrength.ZERO.value());
      }
      if(null == updated.getIotDevBattery()){
        updated.setIotDevBattery(BatteryStatus.NONE.value());
      }
      if(null == updated.getLatestActiveTime()){
        updated.setLatestActiveTime(cal.getTime());
      }
      updated.setCreateDate(cal.getTime());
      iotDeviceSessionMapper.insertSelective(updated);
    }else{
      //修改设备的记录
      updated.setId(session.getId());
      iotDeviceSessionMapper.updateByPrimaryKeySelective(updated);
    }
  }
  /**
   * @description 查询在线状态
   * @param imei
   * @return
   */
  public IotDevInstantInfo queryDeviceStatus(String imei){
    //根据imei查询设备的平台id
    IotDeviceInfo info = queryDbDeviceInfoByImei(imei);
    IotDevInstantInfo instant = queryIotDevInstantInfoByDevId(info.getIotDevId());
    instant.setImsi(info.getIotDevImsi());
    return instant;
  }
  /**
   * @description 根据iotDevId查询在线信息(只有电量, 信号强度 和在线状态 )
   * @param iotDevId 平台设备编号
   * @return
   */  
  private IotDevInstantInfo queryIotDevInstantInfoByDevId(String iotDevId){
    checkIotDevId(iotDevId);
    IotDeviceSession session = queryDbDeviceSession(iotDevId);
    checkSession(session);
    IotDevInstantInfo sessionIot = new IotDevInstantInfo();
    sessionIot.setNbBattery(session.getIotDevBattery());
    sessionIot.setNbRssi(session.getIotDevRssi());
    sessionIot.setNbState(session.getIotDevState());
    sessionIot.setBatteryLevel(session.getIotDevBatteryPercent());
    sessionIot.setIotDevId(iotDevId);
    return sessionIot;
  }
  /**
   * @description 定时同步两小时还没有状态返回的设备信息(这个主要是用来判断掉线的，因为设备上线之后，订阅的设备消息里面会有这个信息)
   */
  public void sessionSyncronize() {
    //查询最近没有更新的设备
    Calendar cal = Calendar.getInstance();
    long start = cal.getTimeInMillis();
    String startDate = DateTools.getSimpleDateTime(cal.getTime());
    cal.set(Calendar.HOUR, cal.get(Calendar.HOUR)-1);
    String endDate = DateTools.getSimpleDateTime(cal.getTime());
    IotDeviceSessionExample example = new IotDeviceSessionExample();
    example.createCriteria().andUpdateDateLessThan(cal.getTime());
    List<IotDeviceSession> list = iotDeviceSessionMapper.selectByExample(example);
    int i = 0;
    if(null != list && list.size()>0){
      for(IotDeviceSession session:list){
        IotDeviceInfo dbDevice = queryDBDeviceInfo(session.getIotDevId());
        if(null == dbDevice){
          logger.debug("在线设备信息{}已失效", session.getIotDevId());
          clearDevSessionInfo(session.getIotDevId());
          continue;
        }
        //查询设备信息
        IotDeviceInfoDTO deviceInfo = queryPlatDeviceInfo(dbDevice);
        if(null != deviceInfo){
          IotDeviceSession updateRecord = new IotDeviceSession();
          updateRecord.setIotDevId(session.getIotDevId());
          updateRecord.setUpdateDate(new Date());
          
          //查询IOT平台的设备信息
          if(null != deviceInfo && null != deviceInfo.getDeviceInfo()){
            updateRecord.setIotDevState(getOnlineStatus(deviceInfo.getDeviceInfo().getStatus()).value());
//            if(null != deviceInfo.getDeviceInfo().get){              
//              updateRecord.setIotDevBattery(deviceInfo.getDeviceInfo().getBatteryLevel());
//            }
            if(null != deviceInfo.getDeviceInfo().getSignalStrength()){              
              updateRecord.setIotDevRssi(deviceInfo.getDeviceInfo().getSignalStrength());
            }
          }
          
          if(null != deviceInfo.getServices()){
            IotSession extractSessionInfo = deviceCallBackFacade.extractSessionInfo(
                JSONObject.parseArray(JSON.toJSONString(deviceInfo.getServices())));
            if(null != extractSessionInfo){
              if(StringUtils.isNotBlank(extractSessionInfo.getImsi()) 
                  && !dbDevice.getIotDevImsi().equalsIgnoreCase(extractSessionInfo.getImsi())){
                //更新设备
                IotDeviceInfo infoUpdated = new IotDeviceInfo();
                infoUpdated.setIotDevId(dbDevice.getIotDevId());
                infoUpdated.setIotDevImsi(extractSessionInfo.getImsi());
                updateDeviceInfoByDevId(infoUpdated);
              }
              //由于上面的设备信息带有这个参数，这边需要加入二次判断
              if(null == updateRecord.getIotDevState() && null != extractSessionInfo.getIotDevState()){
                updateRecord.setIotDevState(extractSessionInfo.getIotDevState());
              }
              //设备信息里面暂时是没有电量和设备信号强度的，会默认带在服务里面, 所以如果连续两小时没有反应的设备, 校验下最后的信号强度和电量
              if(null != extractSessionInfo.getNbBattery()){              
                updateRecord.setIotDevBattery(extractSessionInfo.getNbBattery());
              }
              if(null != extractSessionInfo.getNbRssi()){              
                updateRecord.setIotDevRssi(extractSessionInfo.getNbRssi());
              }
              if(null != extractSessionInfo.getLatestActiveTime()){
                updateRecord.setLatestActiveTime(extractSessionInfo.getLatestActiveTime());
              }
            }
          }
          if(null != updateRecord.getIotDevState()
              || null != updateRecord.getIotDevBattery()
              || null != updateRecord.getIotDevRssi()){
            int updated = updateSession(updateRecord);
            i += updated;
          }else{
            logger.error("本次同步未查询到任何可更新的状态, 设备id：{}", session.getIotDevId());
          }
        }
      }
    }
    logger.info("本次同步耗时：{}, 更新记录{}条, 查询开始时间：{}, 查询到期时间：{}",
        System.currentTimeMillis() - start, i, startDate, endDate);
  }

  /**
   * @description 根据iotDevId查询在线信息(完整对象)
   * @param iotDevId 平台设备编号
   * @return
   */
  public IotDeviceSession queryDbDeviceSession(String iotDevId){
    //查询在线状态
    checkIotDevId(iotDevId);
    IotDeviceSessionExample sessionExample = new IotDeviceSessionExample();
    sessionExample.createCriteria().andIotDevIdEqualTo(iotDevId);
    return ObjectUtil.getOne(iotDeviceSessionMapper.selectByExample(sessionExample));
  }  
  
  public AppResult remoteOpenLock(String imei) throws Exception {
    AppResult result;
    IotDeviceInfo info = queryDbDeviceInfoByImeiIfExist(imei);
    IotPartnerConfig config = 
        partnerService.queryPartnerPlatformConfig(info.getPartnerCode(), 
            info.getIotProtocolVersion(), IotPartnerConfig.class);
    String response;
    try {
      response = iotPlatService.openLock(info.getIotDevId(), "ge/ad8XT1takmr4STDqV5A==",
          config.getAppId(), config.getAppSecret());
      if(StringUtils.isEmpty(response)){
        result = AppResult.newFailResult("远程开门失败");
      }else{
        JSONObject object = JSON.parseObject(response);
        if(null == object.getInteger("error_code") 
            || object.getInteger("error_code").intValue() == 0){
          result = AppResult.newSuccessResult("远程开门指令发送",null);
        }else{
          result = AppResult.newFailResult(
              null == object.getString("error_desc")?"未知的远程开门错误":object.getString("error_desc"));
        }
      }
    } catch (Exception e) {
      response = null;
      result = AppResult.newFailResult("发送远程开门命令失败");
      logger.error("发送远程开门命令失败",e);
    }
    return result;
  }
  
  /**
   * @description 根据imei查询设备信息
   * @param imei
   * @return
   */
  public IotDeviceInfo queryDbDeviceInfoByImei(String imei){
    IotDeviceInfo deviceInfo = queryDbDeviceInfoByImeiIfExist(imei);
    checkDevInfo(deviceInfo);
    return deviceInfo;
  }
  /**
   * @description 根据imei查询设备信息
   * @param imei
   * @return
   */
  public IotDeviceInfo queryDbDeviceInfoByImeiIfExist(String imei){
    checkImei(imei);
    IotDeviceInfoExample example = new IotDeviceInfoExample();
    example.createCriteria().andIotDevImeiEqualTo(imei);
    return ObjectUtil.getOne(iotDeviceInfoMapper.selectByExample(example));
  }
  /**
   * @description 根据imei查询设备信息
   * @param imei
   * @return
   */
  public IotDeviceInfo queryDbDeviceInfoByImei(String imei, Long sourceId){
    IotDeviceInfo deviceInfo = queryDbDeviceInfoByImeiIfExist(imei, sourceId);
    checkDevInfo(deviceInfo);
    return deviceInfo;
  }
  /**
   * @description 根据imei查询设备信息
   * @param imei
   * @return
   */
  public IotDeviceInfo queryDbDeviceInfoByImeiIfExist(String imei, Long sourceId){
    checkImei(imei);
    IotDeviceInfoExample example = new IotDeviceInfoExample();
    example.createCriteria().andIotDevImeiEqualTo(imei).andPartnerCodeEqualTo(sourceId);
    return ObjectUtil.getOne(iotDeviceInfoMapper.selectByExample(example));
  }
  /**
   * @description 根据deviceId查询设备信息
   * @param deviceId
   * @return
   */
  public IotDeviceInfo queryDBDeviceInfo(String iotDevId){
    if(StringUtils.isEmpty(iotDevId)){
      return null;
    }
    IotDeviceInfoExample example = new IotDeviceInfoExample();
    example.createCriteria().andIotDevIdEqualTo(iotDevId);
    return ObjectUtil.getOne(iotDeviceInfoMapper.selectByExample(example));
  }
  
  public IotDeviceInfoDTO queryPlatDeviceInfoByImei(String imei){
    checkImei(imei);
    IotDeviceInfo info = queryDbDeviceInfoByImei(imei);
    return queryPlatDeviceInfo(info);
  }
  /**
   * @description 查询IOT平台的设备信息,如果不存在,则清除设备状态信息
   * @param iotDevId
   * @return
   */
  private IotDeviceInfoDTO queryPlatDeviceInfo(IotDeviceInfo info){
    try {
      IotPartnerConfig config = 
          partnerService.queryPartnerPlatformConfig(
              info.getPartnerCode(), info.getIotProtocolVersion(), IotPartnerConfig.class);
      AppResult result = iotPlatService.getDeviceInfo(info.getIotDevId(), config.getAppId(), config.getAppSecret());
      if(null != result && result.equalsFail() && 
          result.getErrorCode() == ErrorIOTPlatform.DEVICE_NOT_EXIST.getCode()){
        testAndCleanDevSessionInfo(info.getIotDevId());//如果返回设备不存在,则删除设备在线信息
      }else if(null != result && result.equalsSuccess()){
        return (IotDeviceInfoDTO)result.getData();
      }
    } catch (Exception e) {
      logger.info("查询设备信息失败",e);
    }
    return null;
  }
  /**
   * @description 注销设备
   * @param deviceId 设备编号
   * @return
   * @throws Exception
   */
  @Transactional
  public boolean cleanDeviceInfo(String deviceId) throws Exception{
    checkIotDevId(deviceId);
    IotDeviceInfo dbDevice = queryDBDeviceInfo(deviceId);
    if(null != dbDevice){
      IotPartnerConfig config = 
          partnerService.queryPartnerPlatformConfig(
              dbDevice.getPartnerCode(), dbDevice.getIotProtocolVersion(), IotPartnerConfig.class);
      AppResult result = iotPlatService.deleteDevice(deviceId, config.getAppId(), config.getAppSecret());
      if(result.equalsSuccess() || 
          result.getErrorCode() == ErrorIOTPlatform.DEVICE_NOT_EXIST.getCode()){
        //设置数据为已删除
        IotDeviceInfo info = new IotDeviceInfo();
        info.setState(DevState.DEV_DELETED.getState());
        info.setIotDevId(deviceId);
        updateDeviceInfoByDevId(info);
        //添加日志流水
        return true;
      }
    }
    return false;
  }
  
  public int updateDeviceInfoByDevId(IotDeviceInfo info){
    if(null == info || null == info.getIotDevId()){
      return 0;
    }
    IotDeviceInfoExample exampleDev = new IotDeviceInfoExample();
    exampleDev.createCriteria().andIotDevIdEqualTo(info.getIotDevId());
    info.setUpdateDate(new Date());
    return iotDeviceInfoMapper.updateByExampleSelective(info, exampleDev);
  }
  
  /**
   * @description 升级现有设备到最新协议
   * @param partnerCode 来源
   * @param version 版本号
   * @throws Exception 
   */
  public void upgradeOldversionedDevice(Long partnerCode, Integer version) throws Exception {
    List<IotDeviceInfo> versionedDeviceList = queryVersionedIotDevice(partnerCode, version);
    if(null != versionedDeviceList && !versionedDeviceList.isEmpty()){
      StringBuilder deviceIdList = new StringBuilder(versionedDeviceList.size() * 20);
      for(IotDeviceInfo versionedDevice:versionedDeviceList){
        //获取当前设备协议(用于删除)
        IotPartnerConfig config = 
            partnerService.queryPartnerPlatformConfig(
                partnerCode, versionedDevice.getIotProtocolVersion(), IotPartnerConfig.class);
        //删除设备
        iotPlatService.deleteDevice(versionedDevice.getIotDevId(), config.getAppId(), config.getAppSecret());
      //获取最新平台协议
        IotPartnerConfig newConfig = 
            partnerService.queryPartnerPlatformConfig(partnerCode, null, IotPartnerConfig.class);
//      设备平台注册 
        String iotDeviceId = iotPlatService.register(versionedDevice.getIotDevImei(), newConfig.getAppId(), newConfig.getAppSecret());
        //      重新绑定注册信息
        IotDeviceInfo updated = new IotDeviceInfo();
        updated.setId(versionedDevice.getId());
        updated.setIotDevId(iotDeviceId);
        updated.setIotProtocolVersion(newConfig.getConfigVersion());
        updateDeviceInfoById(updated);
        deviceIdList.append(versionedDevice.getId());
        deviceIdList.append(",");
      }
      IotPlatformDevEntry entry = new IotPlatformDevEntry();
      entry.setPlatformDevEntry(deviceIdList.toString());
      entry.setPlatformDevId(0L);
      entry.setUserId(0L);
      entry.setRemark("批量升级设备");
      iotPlatformDevEntryService.insertDevEntry(entry);
      logger.info("升级了{}个设备-{}", versionedDeviceList.size(), deviceIdList.toString());
    }
  }
  

  public boolean degradeDevice(Long partnerCode, String devIdList, Integer version) throws Exception{
    if(StringUtils.isBlank(devIdList)){
      logger.error("需要降级的设备列表为空，来源：{}-版本：{}", partnerCode, version);
      return false;
    }
    checkDevPartner(partnerCode);
    chechDegradeVersion(version);
    String[] preIds = devIdList.split(SymbolConstants.SPLIT_PATTERN);
    if(null == preIds || preIds.length == 0){
      logger.error("需要降级的设备列表有误，来源：{}-设备列表数据：{}-版本：{}", partnerCode, devIdList, version);
      return false;
    }
    List<Long> ids = new ArrayList<>();
    for(String idStr : preIds){
      try{        
        Long id = Long.parseLong(idStr);
        ids.add(id);
      }catch(Exception e){
        logger.error("需要降级的设备列表有误，来源：{}-设备列表数据：{}-版本：{}", partnerCode, devIdList, version);
        return false;
      }
    }
    List<IotDeviceInfo> degradeList = queryIotDeviceByIds(partnerCode, ids);
    if(null != degradeList && !degradeList.isEmpty()){
      for(IotDeviceInfo versionedDevice:degradeList){
        //获取当前设备协议(用于删除)
        IotPartnerConfig config = 
            partnerService.queryPartnerPlatformConfig(
                partnerCode, versionedDevice.getIotProtocolVersion(), IotPartnerConfig.class);
        //删除设备
        iotPlatService.deleteDevice(versionedDevice.getIotDevId(), config.getAppId(), config.getAppSecret());
      //获取最新平台协议
        IotPartnerConfig newConfig = 
            partnerService.queryPartnerPlatformConfig(partnerCode, version, IotPartnerConfig.class);
//      设备平台注册 
        String iotDeviceId = iotPlatService.register(versionedDevice.getIotDevImei(), newConfig.getAppId(), newConfig.getAppSecret());
        //      重新绑定注册信息
        IotDeviceInfo updated = new IotDeviceInfo();
        updated.setId(versionedDevice.getId());
        updated.setIotDevId(iotDeviceId);
        updated.setIotProtocolVersion(newConfig.getConfigVersion());
        updateDeviceInfoById(updated);
      }
      IotPlatformDevEntry entry = new IotPlatformDevEntry();
      entry.setPlatformDevEntry(devIdList);
      entry.setPlatformDevId(0L);
      entry.setUserId(0L);
      entry.setRemark("批量降级设备");
      iotPlatformDevEntryService.insertDevEntry(entry);
      logger.info("降级了{}个设备-{}", degradeList.size(), devIdList);
    }
    return true;
  }
  
  private void updateDeviceInfoById(IotDeviceInfo updated){
    if(null == updated || null == updated.getId()){
      throw new ServiceException("DEV_INFO.DEV_ID_NOT_FOUND");
    }
    updated.setUpdateDate(new Date());
    iotDeviceInfoMapper.updateByPrimaryKeySelective(updated);
  }
  
  private List<IotDeviceInfo> queryVersionedIotDevice(Long partnerCode, Integer version){
    IotDeviceInfoExample exampleDev = new IotDeviceInfoExample();
    exampleDev.createCriteria()
    .andPartnerCodeEqualTo(partnerCode)
    .andIotProtocolVersionEqualTo(version);
    return iotDeviceInfoMapper.selectByExample(exampleDev);
  }
  
  private List<IotDeviceInfo> queryIotDeviceByIds(Long partnerCode, List<Long> ids){
    IotDeviceInfoExample exampleDev = new IotDeviceInfoExample();
    exampleDev.createCriteria()
    .andPartnerCodeEqualTo(partnerCode)
    .andIdIn(ids);
    return iotDeviceInfoMapper.selectByExample(exampleDev);
  }
  
  /**
   * @description 更新在线状态
   * @param session 
   * @return
   */
  private int updateSession(IotDeviceSession session){
    checkSession(session);
    IotDeviceSessionExample sessionEx = new IotDeviceSessionExample();
    sessionEx.createCriteria().andIotDevIdEqualTo(session.getIotDevId());
    if(null == session.getUpdateDate()){      
      session.setUpdateDate(new Date());
    }
    return iotDeviceSessionMapper.updateByExampleSelective(session, sessionEx);
  }
  /**
   * @description 清除设备的状态信息
   * @param iotDevId
   */
  private void testAndCleanDevSessionInfo(String iotDevId){
    checkIotDevId(iotDevId);
    IotDeviceInfo dbDeviceInfo = queryDBDeviceInfo(iotDevId);
    if(null == dbDeviceInfo){      
      // 删除在线数据
      clearDevSessionInfo(iotDevId);
    }else{
      IotDeviceSession updated = new IotDeviceSession();
      updated.setIotDevId(iotDevId);
      updated.setIotDevState(OnlineStatus.offline.value());
      updated.setIotDevRssi(RssiStrength.ZERO.value());
      updated.setIotDevBattery(BatteryStatus.NONE.value());
      updated.setIotDevId(iotDevId);
      updated.setUpdateDate(new Date());
      updateSession(updated);
    }
  }
  /**
   * @description 清除失效的设备状态信息
   * @param iotDevId
   */
  private void clearDevSessionInfo(String iotDevId){
    checkIotDevId(iotDevId);
    IotDeviceSessionExample exampleSession = new IotDeviceSessionExample();
    exampleSession.createCriteria().andIotDevIdEqualTo(iotDevId);
    iotDeviceSessionMapper.deleteByExample(exampleSession);
    IotPlatformDevEntry entry = new IotPlatformDevEntry();
    entry.setPlatformDevEntry(iotDevId);
    entry.setPlatformDevId(0l);
    entry.setRemark("在线信息失效删除");
    entry.setUserId(0l);
    iotPlatformDevEntryService.insertDevEntry(entry);
  }
  
  /**
   * @description 判断在线状态
   * @param status
   * @return
   */
  private OnlineStatus getOnlineStatus(String status){
    if("ONLINE".equalsIgnoreCase(status)){
      return OnlineStatus.online;
    }else{
      return OnlineStatus.offline;
    }
  }
  
  
  /**
   * @description 检验在线状态信息
   * @param session 
   * @return
   */
  private void checkSession(IotDeviceSession session){
    if(null == session || StringUtils.isEmpty(session.getIotDevId())){
      throw new ServiceException(
          ErrorIOTDev.DEV_SESSION_INFO_EMPTY.getKey(),
          ErrorIOTDev.DEV_SESSION_INFO_EMPTY.getCode());
    }
  }
  
  private void checkRegisterDevice(String imei, Long partnerCode, String nbDevId){
    checkImei(imei);
    checkNbDevID(nbDevId);
    checkDevPartner(partnerCode);
  }
  
  /**
   * @description 检验在线状态信息
   * @param session 
   * @return
   */
  private void checkIotDevId(String iotDevId){
    if(StringUtils.isBlank(iotDevId)){
      throw new ServiceException(
          ErrorIOTDev.DEV_IOTID_EMPTY.getKey(),
          ErrorIOTDev.DEV_IOTID_EMPTY.getCode());
    }
  }
  private void checkImei(String imei){
    if(StringUtils.isBlank(imei) || imei.length() > 50){
      throw new ServiceException(
          ErrorIOTDev.DEV_IMEI_EMPTY.getKey(),
          ErrorIOTDev.DEV_IMEI_EMPTY.getCode());
    }
  }
  private void checkNbDevID(String nbDevId){
    if(StringUtils.isBlank(nbDevId)){
      throw new ServiceException(
          ErrorIOTDev.DEV_ID_EMPTY.getKey(),
          ErrorIOTDev.DEV_ID_EMPTY.getCode());
    }
  }
  private void checkDevPartner(Long partnerCode){
    if(null == partnerCode || partnerCode <= 0){
      throw new ServiceException(
          ErrorPartner.PARTNER_IS_EMPTY.getKey(),
          ErrorPartner.PARTNER_IS_EMPTY.getCode());
    }
  }
  
  private void chechDegradeVersion(Integer version){
    if(null == version || version <= 0){
      throw new ServiceException("DEV_INFO.DEV_UPGRADE_VERSION_EMPTY");
    }
  }
  
  private void checkDevInfo(IotDeviceInfo info){
    if(null == info){
      throw new ServiceException(
          ErrorIOTDev.DEV_INFO_EMPTY.getKey(),
          ErrorIOTDev.DEV_INFO_EMPTY.getCode());
    }
  }
  /**
   * @description 检验在线状态信息
   * @param session 
   * @return
   */
  private void checkIotDevIngo(IotDeviceInfo info){
    checkDevInfo(info);
    if(StringUtils.isBlank(info.getIotDevId())){
      throw new ServiceException(
          ErrorIOTDev.DEV_IOTID_EMPTY.getKey(),
          ErrorIOTDev.DEV_IOTID_EMPTY.getCode());
    }
    if(StringUtils.isBlank(info.getIotDevCert())){
      throw new ServiceException(
          ErrorIOTDev.DEV_CERT_EMPTY.getKey(),
          ErrorIOTDev.DEV_CERT_EMPTY.getCode());
    }
    if(null == info.getIotDevRegcode()){
      throw new ServiceException(
          ErrorIOTDev.DEV_REG_CODE_EMPTY.getKey(),
          ErrorIOTDev.DEV_REG_CODE_EMPTY.getCode());
    }
    checkImei(info.getIotDevImei());
    checkNbDevID(info.getNbDevId());
    checkDevPartner(info.getPartnerCode());
  }
}
