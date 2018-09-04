package com.pi.nbcenter.device.controller.iot;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pi.base.dto.result.AppResult;
import com.pi.base.util.http.RequestUtil;
import com.pi.base.util.lang.ByteUtil;
import com.pi.base.util.lang.StringUtil;
import com.pi.nbcenter.base.constants.OnlineStatus;
import com.pi.nbcenter.device.bean.dto.nb.IotDeviceInfoDTO;
import com.pi.nbcenter.device.bean.dto.session.IotSession;
import com.pi.nbcenter.device.controller.iot.facade.DeviceCallBackFacade;
import com.pi.nbcenter.device.entity.auto.IotDeviceSession;
import com.pi.nbcenter.device.service.CommonDeviceService;
import com.pi.nbcenter.device.service.base.BasePartnerSubscriptionService;
import com.pi.nbcenter.device.service.iot.IotDevService;

@RequestMapping("/device")
@RestController
public class IotDeviceController {
  private static final Logger logger = LoggerFactory.getLogger(IotDeviceController.class);
  @Autowired
  private IotDevService iotDevService;
  @Autowired
  private CommonDeviceService commonDeviceService;
  @Autowired
  private DeviceCallBackFacade deviceCallBackFacade;
  @Autowired
  private BasePartnerSubscriptionService subscriptionService;
  /**
   * @description 注册设备
   * @param imei
   * @return
   * @throws Exception 
   */
  @PostMapping("/regdevice")
  @ResponseBody
  public AppResult registerDevice(@RequestParam(required=false) String imei,
      @RequestParam(required=false) Long partnerCode,
      @RequestParam(required=false) String nbDevId) throws Exception{
    logger.info("注册：IMEI:{}, 合作商编号：{}, 设备编号：{}", imei, partnerCode, nbDevId);
    return commonDeviceService.registerDevice(imei, partnerCode, nbDevId);
  }
  
  /**
   * @description 注册设备
   * @param imei
   * @return
   */
  @PostMapping("/queryDeviceStatus")
  @ResponseBody
  public AppResult queryDeviceStatus(@RequestParam(required=false) String imei){
    AppResult result;
    logger.info("查询状态：IMEI:{}", imei);
    if(StringUtils.isEmpty(imei)){
      result = AppResult.newFailResult("设备imei号为空");
    }else{
      result = AppResult.newSuccessResult(commonDeviceService.queryDeviceStatus(imei));
    }
    return result;
  }
  
  /**
   * @description 注册设备
   * @param imei
   * @return
   * @throws Exception 
   */
  @PostMapping("/remoteOpenLock")
  @ResponseBody
  public AppResult remoteOpenLock(@RequestParam(required=false) String imei)
      throws Exception{
    logger.info("远程开门-IMEI:{}", imei);
    AppResult result = commonDeviceService.remoteOpenLock(imei);
    return result;
  }
  
  /**
   * @description 注册设备
   * @param imei
   * @return
   * @throws Exception 
   */
  @PostMapping("/queryDeviceInfo")
  @ResponseBody
  public AppResult queryDeviceInfo(@RequestParam(required=false) String imei)
      throws Exception{
    logger.info("查询设备信息-IMEI:{}", imei);
    IotDeviceInfoDTO info = iotDevService.queryPlatDeviceInfoByImei(imei);
    return AppResult.newSuccessResult(info);
  }
  
  /**
   * @description 通知回调 https://121.40.178.129:4437/nbcenter/device/callback
   */
  @PostMapping("/callback")
  @ResponseBody
  public String notification(HttpServletRequest request){
    String randomTag = StringUtil.generateNonce(5);
//    logger.info("[{}]接收到回调通知：{}", randomTag, request.getParameterMap());
    try {
//        String json = "{\"notifyType\":\"deviceDataChanged\",\"deviceId\":\"f514aa2a-b848-4e8d-906d-2b7da67b4b28\",\"gatewayId\":\"f514aa2a-b848-4e8d-906d-2b7da67b4b28\",\"requestId\":null,\"service\":{\"serviceId\":\"Connectivity\",\"serviceType\":\"Connectivity\",\"data\":{\"signalStrength\":-714,\"linkQuality\":-133,\"signalECL\":1,\"cellId\":78751515,\"signalPCI\":199,\"signalSNR\":14,\"mode\":2},\"eventTime\":\"20180602T062810Z\"}}";//RequestUtil.getJSONData(request);
        String data = RequestUtil.getJSONData(request);
        if(StringUtils.isNotEmpty(data)){
          JSONObject jsonObj = JSON.parseObject(data);
          if(null != jsonObj && null != jsonObj.getString("deviceId")){
            if("e6e11fbc-ee93-46d1-bf03-5c17810ba247".equalsIgnoreCase(jsonObj.getString("deviceId"))){
              sendInsToDessmann();
            }
            //更新设备的状态为在线
            IotSession callback = deviceCallBackFacade.callbackSession(jsonObj);
            IotDeviceSession session = new IotDeviceSession();
            session.setIotDevId(jsonObj.getString("deviceId"));
            if(null != callback){
              session.setIotDevBattery(callback.getNbBattery());
              session.setIotDevBatteryPercent(callback.getBatteryLevel());
              session.setIotDevRssi(callback.getNbRssi());
              session.setLatestActiveTime(callback.getLatestActiveTime());
            }
            session.setIotDevState(OnlineStatus.online.value());
            iotDevService.updateDeviceSession(session);
            
            //查询设备的订阅方式并推送到url
            subscriptionService.pushToPartner(jsonObj.getString("deviceId"), jsonObj);
          }
        }
        logger.info("[{}]接收到body数据： {}", randomTag, data);
    } catch (IOException e) {
      logger.error("",e);
    }
    return "success";
  }
  
  @PostMapping("/subscribe")
  public AppResult subscribe(@RequestParam(required=false) Long partnerCode, 
      @RequestParam(required=false) String remoteUri, 
      @RequestParam(required=false) Integer notifyType){
    subscriptionService.addPartnerSubscription(partnerCode, notifyType, remoteUri);
    return AppResult.newSuccessResult("订阅成功", null);
  }
  
  @PostMapping("/unsubscribe")
  public AppResult unsubscribe(@RequestParam(required=false) Long partnerCode, 
      @RequestParam(required=false) String remoteUri){
    subscriptionService.deletePartnerSubscription(partnerCode, remoteUri);
    return AppResult.newSuccessResult("订阅成功", null);
  }
  
  private void sendInsToDessmann() throws UnknownHostException, IOException{
    String ins = "FE 02 04 00 21 0B DC 7B CB 40 B6 6B 00 00 00 00 00 00 00 00 00 00 00 00 01 DE 02 00 00 00 00 00 18 05 09 17 58 00 05 2B";
    Socket socket = new Socket("118.190.98.192", 8898);
    OutputStream out = socket.getOutputStream();
    out.write(ByteUtil.hexToByte(ins));
    out.flush();
    out.close();
    socket.close();
  }
}
