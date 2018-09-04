package com.pi.nbcenter.device.controller.iot.facade;

import java.text.ParseException;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pi.base.util.time.DateTools;
import com.pi.nbcenter.base.constants.IotNotifyType;
import com.pi.nbcenter.base.constants.base.RecordState;
import com.pi.nbcenter.base.constants.iot.IotService;
import com.pi.nbcenter.device.bean.dto.nb.iotservice.BatteryInfo;
import com.pi.nbcenter.device.bean.dto.nb.iotservice.LockState;
import com.pi.nbcenter.device.bean.dto.nb.iotservice.SignalInfo;
import com.pi.nbcenter.device.bean.dto.session.IotSession;

/**
 * @description 处理设备消息订阅
 * @author Administrator
 *
 */
@Service
public class DeviceCallBackFacade {
  private static final Logger logger = LoggerFactory.getLogger(DeviceCallBackFacade.class);
  public IotSession callbackSession(JSONObject callback){
    if(null == callback){
      return null;
    }
    String notifyType = callback.getString("notifyType");
    IotNotifyType iotNotifyType = IotNotifyType.getIotNotifyType(notifyType);
    if(null == iotNotifyType){
      return null;
    }
    IotSession session;
    switch(iotNotifyType){
      case DEVICE_DATA_CHANGED://批量变化和单个上传是一起的,所以暂时忽略单个的, 看后续反馈
        session = null;
        break;
      case DEVICE_DATAS_CHANGED:
        session = extractSessionInfo(callback.getJSONArray("services"));
        break;        
      default:
        session = null;
        break;
    }
    if(null != session && null != callback.getString("deviceId")){
      session.setIotDevId(callback.getString("deviceId"));
    }
    return session;
  }
  
  public IotSession extractSessionInfo(JSONArray serviceCallBackArray){
    if(null == serviceCallBackArray){
      return null;
    }
    IotSession session = new IotSession();
    for(int i =0, size = serviceCallBackArray.size(); i < size; i++){
      JSONObject singleService = serviceCallBackArray.getJSONObject(i);
      IotService iotService = IotService.getIotService(singleService.getString("serviceId"));
      if(null == iotService){
        continue;
      }
      switch(iotService){
        case CONNECTIVITY:
          wrapSignl(session, singleService);
          break;
        case BATTERY:
          wrapBattery(session, singleService);
          break;
        case LOCK_STATE:
          wrapLockState(session, singleService);
          break;
        default:
          break;
      }
    }
    return session;
  }
  
  private void wrapSignl(IotSession session, JSONObject singleService){
    SignalInfo info = singleService.getObject("data", SignalInfo.class);
    //兼容新旧版本的信号强度
    if(null != info && info.getSignalStrength() == 0){
      info.setSignalStrength(singleService.getJSONObject("data").getIntValue("signalPower"));
    }
    session = wrapSession(session);
    refreshServiceTime(singleService, session);
    session.setNbRssi(info.getSignalStrength());
    if(null != info.getIMSI()){      
      session.setImsi(info.getIMSI().trim());
    }
  }
  
  private void refreshServiceTime(JSONObject service, IotSession session){
    String dateTime = service.getString("eventTime");
    if(null != dateTime){
      try {
        Date d = DateTools.parserDate(
            dateTime, "yyyyMMdd'T'HHmmss'Z'", TimeZone.getTimeZone("GMT+0:00"));
        if(null == session.getLatestActiveTime() || 
            session.getLatestActiveTime().getTime() < d.getTime()){
          session.setLatestActiveTime(d);
        }
      } catch (ParseException e) {
        logger.error("时间格式不正确:{}", dateTime);
      }
    }
  }
  private void wrapBattery(IotSession session, JSONObject singleService){
    BatteryInfo info = singleService.getObject("data", BatteryInfo.class);
    session = wrapSession(session);
    refreshServiceTime(singleService, session);
    session.setNbBattery(info.getBatteryVoltage());
    session.setBatteryLevel(info.getBatteryLevel());
  }
  
  private void wrapLockState(IotSession session, JSONObject singleService){
    LockState info = singleService.getObject("data", LockState.class);
    session = wrapSession(session);
    refreshServiceTime(singleService, session);
    session.setIotDevState(
        (null == info.getLockState() || 
        info.getLockState() == RecordState.STATE_VIEW_INEFFEVTIVE.getCode())
        ?null:RecordState.STATE_VIEW_EFFEVTIVE.getCode());
  }
  
  private IotSession wrapSession(IotSession session){
    if(null == session){
      session =  new IotSession();
    }
    return session;
  }
  
  public static void main(String[] args) {
    String str = "{\"notifyType\":\"deviceDataChanged\",\"requestId\":null,\"deviceId\":\"f514aa2a-b848-4e8d-906d-2b7da67b4b28\",\"gatewayId\":\"f514aa2a-b848-4e8d-906d-2b7da67b4b28\",\"services\":[{\"serviceId\":\"Connectivity\",\"serviceType\":\"Connectivity\",\"data\":{\"signalStrength\":-748,\"linkQuality\":-147,\"signalECL\":1,\"cellId\":78751515,\"signalPCI\":199,\"signalSNR\":-12,\"mode\":2},\"eventTime\":\"20180605T110355Z\"},{\"serviceId\":\"Battery\",\"serviceType\":\"Battery\",\"data\":{\"batteryLevel\":84,\"batteryVoltage\":4041,\"batteryState\":1},\"eventTime\":\"20180605T110355Z\"},{\"serviceId\":\"Lock\",\"serviceType\":\"Lock\",\"data\":{\"lockState\":1},\"eventTime\":\"20180605T110355Z\"}]}";
    
    System.out.println(JSON.toJSONString(new DeviceCallBackFacade().callbackSession(JSON.parseObject(str))));
  }
}
