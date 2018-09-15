package com.pi.nbcenter.util.nb;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pi.base.dto.result.AppResult;
import com.pi.base.exception.ServiceException;
import com.pi.base.util.lang.LongUtil;
import com.pi.nbcenter.device.bean.dto.nb.IotDeviceInfoDTO;
import com.pi.nbcenter.device.bean.dto.partner.IotPartnerConfig;

/**
 * @description IOT平台的指令工具类
 * @author chenmfa
 *
 */
@Validated
@Service
public class HuaWeiIotService {
	private static final Logger logger = LoggerFactory.getLogger(HuaWeiIotService.class);
	
  public static void main(String[] args) throws Exception {
    HuaWeiIotService nbUtil = new HuaWeiIotService();
//		openLock("937b99e4-d580-451e-8b47-d9e26bfab50f","ge/ad8XT1takmr4STDqV5A==");
//		subscribe();
//		setCert("c2ecabb6-ac0f-445f-a2c1-5e9f543811f1", 1032893737,"ge/ad8XT1takmr4STDqV5A==");
//	  AppResult deviceInfo = getDeviceInfo("f89ffcc5-3c13-43c3-8216-fc20af9609e3", "aGZa0RgUs7f0RZea2BzA7TkWLzAa", "no_bMvHqtkxEndF2JfYuRvIB3z4a");
//	  System.out.println(JSON.toJSONString(deviceInfo));
    nbUtil.queryGateway("f514aa2a-b848-4e8d-906d-2b7da67b4b28", "yEADskbTvrU0WjI1B1xIqQ9oVLAa", "7FbZDkKhbGQt3rWdRaJ7tuKzmHsa");
//	  queryGateway("f514aa2a-b848-4e8d-906d-2b7da67b4b28", "MSFPRiVvRpW9Li7OV0UJehG7AIUa", "JdMW2WfIfuvz4PqkC1g8tjAqYcga");
//	  queryGateway("f514aa2a-b848-4e8d-906d-2b7da67b4b28", "aGZa0RgUs7f0RZea2BzA7TkWLzAa", "no_bMvHqtkxEndF2JfYuRvIB3z4a");
//	  deleteDevice("872feba2-f54f-4c36-848a-012ab6949c31", "aGZa0RgUs7f0RZea2BzA7TkWLzAa", "no_bMvHqtkxEndF2JfYuRvIB3z4a");
//    register("865352031693699","aGZa0RgUs7f0RZea2BzA7TkWLzAa", "no_bMvHqtkxEndF2JfYuRvIB3z4a");
//    deleteDevice("ff5ee954-d35d-4a6e-8567-56da21c591b8","aGZa0RgUs7f0RZea2BzA7TkWLzAa", "no_bMvHqtkxEndF2JfYuRvIB3z4a");
	}
  public AppResult queryDevies(String deviceId, String appId, String appSecret) throws Exception{
    HttpsUtil httpsUtil = initializeHttpClient();
    String urlDelete = Constant.DELETE_DEVICE + "/" +deviceId+"?appId="+ appId + "&cascade=false";
  //获取服务器的token
    String accessToken = takeToken(httpsUtil, appId, appSecret);
    Map<String, String> header = getHeaderMap(accessToken, appId);
       
    StreamClosedHttpResponse responseDelete = httpsUtil.doDeleteGetStatusLine(urlDelete, header);
    
    logger.info("删除设备{}开始, 返回状态: {}, 内容：{}", deviceId, responseDelete.getStatusLine(), responseDelete.getContent());
    if(null != responseDelete.getStatusLine() 
        && responseDelete.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT){
      return AppResult.newSuccessResult(null);
    }else{
      JSONObject responseObject = JSONObject.parseObject(responseDelete.getContent());
      if(responseObject.containsKey("error_code")){
        return AppResult.newFailResult("删除失败", responseObject.get("error_code"));
      }else{
        return AppResult.newFailResult("删除失败");
      }
    }
  }
  
	public AppResult deleteDevice(
	    String deviceId, String appId, String appSecret) throws Exception{
		HttpsUtil httpsUtil = initializeHttpClient();
		String urlDelete = Constant.DELETE_DEVICE + "/" +deviceId+"?appId="+ appId + "&cascade=false";
	//获取服务器的token
    String accessToken = takeToken(httpsUtil, appId, appSecret);
		Map<String, String> header = getHeaderMap(accessToken, appId);
		   
		StreamClosedHttpResponse responseDelete = httpsUtil.doDeleteGetStatusLine(urlDelete, header);
		
		logger.info("删除设备{}开始, 返回状态: {}, 内容：{}", deviceId, responseDelete.getStatusLine(), responseDelete.getContent());
		if(null != responseDelete.getStatusLine() 
		    && responseDelete.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT){
			return AppResult.newSuccessResult(null);
		}else{
		  JSONObject responseObject = JSONObject.parseObject(responseDelete.getContent());
		  if(responseObject.containsKey("error_code")){
		    return AppResult.newFailResult("删除失败", responseObject.getIntValue("error_code"));
		  }else{
		    return AppResult.newFailResult("删除失败");
		  }
		}
	}
	public AppResult getDeviceInfo(
	    String deviceId, String appId, String appSecret) throws Exception{
	  HttpsUtil httpsUtil = initializeHttpClient();
	  String queryInfo = Constant.QUERY_DEVICES + "/" +deviceId+"?appId="+ appId;
	  //获取服务器的token
    String accessToken = takeToken(httpsUtil, appId, appSecret);
    Map<String, String> header = getHeaderMap(accessToken, appId);
    HttpResponse response = httpsUtil.doGetWithParas(
        queryInfo, new HashMap<String,String>(), header);
    String deviceInfoStr = httpsUtil.getHttpResponseBody(response);
    logger.info("查询设备信息结束, 返回状态: {}, 内容：{}", 
        response.getStatusLine(), null != deviceInfoStr?deviceInfoStr:"");
    if(null != response.getStatusLine() 
        && HttpStatus.SC_OK == response.getStatusLine().getStatusCode()){
      JSONObject responseObject = JSONObject.parseObject(deviceInfoStr);
      if(responseObject.containsKey("error_code")){
        return AppResult.newFailResult("查询失败", responseObject.getIntValue("error_code"));
      }else{        
        return AppResult.newSuccessResult(JSON.parseObject(deviceInfoStr, IotDeviceInfoDTO.class));
      }
    }else{
      return null;
    }
	}
	
	public void queryGateway(String gateway, String appId, String appSecret) throws Exception{
	  HttpsUtil httpsUtil = initializeHttpClient();
    String queryInfo = Constant.QUERY_DEVICES + "?appId="+ appId+"&deviceType=DoorLock&pageNo=0&pageSize=100";
    String accessToken = takeToken(httpsUtil, appId, appSecret);
    Map<String, String> header = getHeaderMap(accessToken, appId);
    HttpResponse response = httpsUtil.doGetWithParas(
        queryInfo, new HashMap<String,String>(), header);
    String deviceInfoStr = httpsUtil.getHttpResponseBody(response);
    System.out.println(deviceInfoStr);
	}
	
	public boolean updateDevice(
	    String deviceId, String name, IotPartnerConfig config) throws Exception{
    HttpsUtil httpsUtil = initializeHttpClient();
    String urlModify = Constant.MODIFY_DEVICE_INFO + "/" +deviceId+"?appId="+ config.getAppId();
  //获取服务器的token
    String accessToken = takeToken(httpsUtil, config.getAppId(), config.getAppSecret());
    Map<String, String> header = getHeaderMap(accessToken, config.getAppId());

    Map<String, Object> paramModifyDeviceInfo = new HashMap<String, Object>();
    paramModifyDeviceInfo.put("manufacturerId", config.getManufacturerId());
    paramModifyDeviceInfo.put("manufacturerName", config.getManufacturerName());
    paramModifyDeviceInfo.put("deviceType", config.getDeviceType());
    paramModifyDeviceInfo.put("model", config.getModel());
    paramModifyDeviceInfo.put("protocolType", config.getProtocolType());

    String jsonRequest = JsonUtil.jsonObj2Sting(paramModifyDeviceInfo);

    StreamClosedHttpResponse responseModifyDeviceInfo = 
        httpsUtil.doPutJsonGetStatusLine(urlModify,header, jsonRequest);
    logger.info("修改设备信息结束, 返回状态: {}, 内容：{}", 
        responseModifyDeviceInfo.getStatusLine(), responseModifyDeviceInfo.getContent());
    if(null != responseModifyDeviceInfo.getStatusLine() && responseModifyDeviceInfo.getStatusLine().getStatusCode() == 204){
      return true;
    }else{
      return false;
    }
  }
	
	public String setCert(
	    String deviceId, Integer regCode, String identityCode,
	    String appId, String appSecret) throws Exception{
    String serviceId = "Lock";
    String method = "SET_CERT";
    ObjectNode paras = JsonUtil.convertObject2ObjectNode("{\"identityCode\":\"" + identityCode + "\",\"regCode\":"+regCode+",\"regTime\":"+(System.currentTimeMillis()/1000)+"}");
  
    Map<String, Object> paramCommand = new HashMap<>();
    paramCommand.put("serviceId", serviceId);
    paramCommand.put("method", method);
    paramCommand.put("paras", paras);      
    
    Map<String, Object> paramPostAsynCmd = new HashMap<>();
    paramPostAsynCmd.put("deviceId", deviceId);
    paramPostAsynCmd.put("command", paramCommand);
    return sendCommand(paramCommand, paramPostAsynCmd, appId, appSecret);
	}
	
	public String sendIcCard(
      @NotNull(message = "NB_UTITY.IC_CARD_DEVICE_ID_EMPTY") String deviceId, 
      @NotNull(message = "NB_UTITY.IC_CARD_USER_NOT_EMPTY") Long userId, 
      @NotBlank(message = "NB_UTITY.IC_CARD_IS_EMPTY") 
      @Length(max=10, message = "NB_UTITY.IC_CARD_OVER_MAX") String icCard,
      String appId, String appSecret) throws Exception{
	  return operateIcCard(deviceId, userId, icCard, (byte)0x04 , appId, appSecret);
	}
	public String deleteIcCard(
      @NotNull(message = "NB_UTITY.IC_CARD_DEVICE_ID_EMPTY") String deviceId, 
      @NotNull(message = "NB_UTITY.IC_CARD_USER_NOT_EMPTY") Long userId, 
      @NotBlank(message = "NB_UTITY.IC_CARD_IS_EMPTY") 
      @Length(max=10, message = "NB_UTITY.IC_CARD_OVER_MAX") String icCard,
      String appId, String appSecret) throws Exception{
	  return operateIcCard(deviceId, userId, icCard, (byte)0x05, appId, appSecret);
	}
	
	private String operateIcCard(
	    @NotNull(message = "NB_UTITY.IC_CARD_DEVICE_ID_EMPTY") String deviceId, 
      @NotNull(message = "NB_UTITY.IC_CARD_USER_NOT_EMPTY") Long userId, 
      @NotBlank(message = "NB_UTITY.IC_CARD_IS_EMPTY") 
      @Length(max=10, message = "NB_UTITY.IC_CARD_OVER_MAX") String idCard,
      byte ins, String appId, String appSecret) throws Exception{
	  if(ins != 0x04 && ins != 0x05){
	    throw new ServiceException("NB_UTITY.IC_CARD_INS_NOT_CORRECT");
	  }
    String serviceId = "Transmission";
    String method = "RAW_DATA";
    byte[] userIdArr = LongUtil.long2Bytes(userId);
    byte[] rawData = new byte[19];
    rawData[0] = 0x04;
    if(idCard.length() < 10){
      idCard = StringUtils.leftPad(idCard, 10);
    }
    System.arraycopy(userIdArr, 0, rawData, 1, 8);
    System.arraycopy(idCard.getBytes(), 0, rawData, 9, 10);
    JSONObject param = new JSONObject();
    param.put("length", "19");
    param.put("rawData", Base64.encodeBase64(rawData));
    
    Map<String, Object> paramCommand = new HashMap<>();
    paramCommand.put("serviceId", serviceId);
    paramCommand.put("method", method);
    paramCommand.put("paras", param.toString());      
    
    Map<String, Object> paramPostAsynCmd = new HashMap<>();
    paramPostAsynCmd.put("deviceId", deviceId);
    paramPostAsynCmd.put("command", paramCommand);
    return sendCommand(paramCommand, paramPostAsynCmd, appId, appSecret);
  }
	
	public String openLock(
	    String deviceId, String identityCode,
	    String appId, String appSecret) throws Exception{
    //设备ID
//    String deviceId = "c2ecabb6-ac0f-445f-a2c1-5e9f543811f1";
    String serviceId = "Lock";
    String method = "OPEN_LOCK";
    ObjectNode paras = JsonUtil.convertObject2ObjectNode("{\"identityCode\":\"" + identityCode + "\"}");
  
    Map<String, Object> paramCommand = new HashMap<String, Object>();
    paramCommand.put("serviceId", serviceId);
    paramCommand.put("method", method);
    paramCommand.put("paras", paras);      
    
    Map<String, Object> paramPostAsynCmd = new HashMap<>();
    paramPostAsynCmd.put("deviceId", deviceId);
    paramPostAsynCmd.put("command", paramCommand);
    return sendCommand(paramCommand, paramPostAsynCmd, appId, appSecret);
	}
	public String sendCommand(
	    Map<String, Object> paramCommand,
	    Map<String, Object> paramPostAsynCmd,
	    String appId,
	    String appSecret) throws Exception {
		HttpsUtil httpsUtil = initializeHttpClient();

    //获取服务器的token
    String accessToken = takeToken(httpsUtil, appId, appSecret);

    //信令传送地址
    String urlPostAsynCmd = Constant.POST_ASYN_CMD;

    //回调地址
    paramPostAsynCmd.put("callbackUrl", Constant.DEVICE_ALL_CALLBACK);
    paramPostAsynCmd.put("expireTime", 0);
    String jsonRequest = JsonUtil.jsonObj2Sting(paramPostAsynCmd);
            
    return doPost(httpsUtil, urlPostAsynCmd, jsonRequest, getHeaderMap(accessToken,appId));
	}
	
	public List<String> subscribe(String appId, String appSecret) throws Exception{
		List<String> notifyResult = new LinkedList<String>();
		//Two-Way Authentication
    HttpsUtil httpsUtil = new HttpsUtil();
    httpsUtil.initSSLConfigForTwoWay();
    String accessToken = takeToken(httpsUtil,appId,appSecret);
    String urlSubscribe = Constant.SUBSCRIBE_NOTIFYCATION; 

    String callbackurl = Constant.DEVICE_ALL_CALLBACK;
    
    /**
     * @description 向IOT平台订阅消息
     * notifyTypes: 
     * bindDevice/serviceInfoChanged/deviceInfoChanged/deviceDataChanged/deviceAdded/deviceDeleted
     * messageConfirm/commandRsp/deviceEvent/appDeleted/ruleEvent/deviceDatasChanged
     */
    List<String> notifyTypes = NotifyType.getNotifyTypes();
    for (String notifyType : notifyTypes) {
    	//添加订阅内容和回调地址
			Map<String, Object> paramSubscribe = new HashMap<>();
			paramSubscribe.put("notifyType", notifyType);
			paramSubscribe.put("callbackurl", callbackurl);
			
			String jsonRequest = JsonUtil.jsonObj2Sting(paramSubscribe);
//			添加头信息
			Map<String, String> header = getHeaderMap(accessToken, appId);
			HttpResponse httpResponse = httpsUtil.doPostJson(urlSubscribe, header, jsonRequest);
//			HttpResponse httpResponse = doPost(httpsUtil, urlSubscribe, jsonRequest, header);
			String bodySubscribe = httpsUtil.getHttpResponseBody(httpResponse);
			notifyResult.add(String.valueOf(httpResponse.getStatusLine().getStatusCode()));
			logger.info("消息订阅开始 , 通知类型: {}, 回调地址:{}.", notifyType, callbackurl);
			logger.info("响应状态: {}, 响应内容： {}", httpResponse.getStatusLine(), bodySubscribe);
    }
    return notifyResult;
	}
	
	public void unSubScribe(String appId, String appSecret) throws Exception{
	  HttpsUtil httpsUtil = new HttpsUtil();
    httpsUtil.initSSLConfigForTwoWay();
    String accessToken = takeToken(httpsUtil,appId,appSecret);
    Map<String, String> header = getHeaderMap(accessToken, appId);
    
    List<String> notifyTypes = NotifyType.getNotifyTypes();
    for (String notifyType : notifyTypes) {
      String callBack = String.format(Constant.SUBSCRIBE_NOTIFYCATION, appId, notifyType, Constant.DEVICE_ALL_CALLBACK);
      logger.info("请求删除订阅: {}", callBack);
      HttpResponse response = httpsUtil.doDelete(callBack, header);
      String bodySubscribe = httpsUtil.getHttpResponseBody(response);
      logger.info("删除订阅响应状态: {}, 响应内容： {}", response.getStatusLine(), bodySubscribe);
    }
    
	}
	
	@SuppressWarnings("unchecked")
	public String register(String verifyCode, String appId, String appSecret) throws Exception{
		// Two-Way Authentication
    HttpsUtil httpsUtil = new HttpsUtil();
    httpsUtil.initSSLConfigForTwoWay();

    //获取服务器的token
		String accessToken = takeToken(httpsUtil, appId, appSecret);

    //please replace the verifyCode and nodeId and timeout, when you use the demo.
    String nodeId = verifyCode;
    Integer timeout = 0;

    Map<String, Object> paramReg = new HashMap<>();
//    nodeId和verifyCode必须填写相同的值(现在)
    if(null != verifyCode){
    	paramReg.put("verifyCode", verifyCode);
    }
    paramReg.put("nodeId", nodeId.toUpperCase());
    paramReg.put("timeout", timeout);

    String jsonRequest = JsonUtil.jsonObj2Sting(paramReg);

    StreamClosedHttpResponse responseReg = 
    		httpsUtil.doPostJsonGetStatusLine(
    		    Constant.REGISTER_DEVICE, getHeaderMap(accessToken, appId), jsonRequest);

    logger.info("设备注册, 返回结果：{}, 内容： {}.", responseReg.getStatusLine(), responseReg.getContent());
    if(null == responseReg.getStatusLine() || responseReg.getStatusLine().getStatusCode() != 200){
    	return null;
    }else{
    	Map<String, String> map = JsonUtil.convertJsonStringToObject(responseReg.getContent(), Map.class);
    	return map.get("deviceId");
    }
	}
	
	/**
	 * @description 获取访问的token
	 * @param httpsUtil
	 * @return token 带有效期的授权标志
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
  public String takeToken(HttpsUtil httpsUtil, String appId, String appSecret) throws Exception {

      String urlLogin = Constant.APP_AUTH;
      Map<String, String> paramLogin = new HashMap<>();
      paramLogin.put("appId", appId);
      paramLogin.put("secret", appSecret);

      StreamClosedHttpResponse responseLogin = httpsUtil.doPostFormUrlEncodedGetStatusLine(urlLogin, paramLogin);

      logger.info("app auth success,return accessToken: {}, content is: {}", 
      		responseLogin.getStatusLine(), responseLogin.getContent());

      Map<String, String> data = new HashMap<>();
      data = JsonUtil.jsonString2SimpleObj(responseLogin.getContent(), data.getClass());
      return data.get("accessToken");
  }
	
	public Map<String, String> getHeaderMap(String accessToken, String appId){
		Map<String, String> header = new HashMap<>();
    header.put(Constant.HEADER_APP_KEY, appId);
    header.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);
    return header;
	}
	
	public HttpsUtil initializeHttpClient() throws Exception{
    HttpsUtil httpsUtil = new HttpsUtil();
    httpsUtil.initSSLConfigForTwoWay();
    return httpsUtil;
	}
	/**
	 * @description 根据命令信息改造参数请求
	 * @param httpsUtil
	 * @param urlPostAsynCmd 异步请求地址
	 * @param paramPostAsynCmd 
	 * @param jsonRequest body的参数
	 * @param header
	 * @return
	 * @throws UnsupportedOperationException
	 * @throws IOException
	 */
	public String doPost(
			HttpsUtil httpsUtil,String urlPostAsynCmd,
			String jsonRequest,Map<String, String> header)
					throws UnsupportedOperationException, IOException{
		return doPost(httpsUtil, urlPostAsynCmd, jsonRequest, header, null);
	}
	/**
	 * @description 根据命令信息改造参数请求
	 * @param httpsUtil
	 * @param urlPostAsynCmd 异步请求地址
	 * @param paramPostAsynCmd 
	 * @param jsonRequest body的参数
	 * @param header
	 * @return
	 * @throws UnsupportedOperationException
	 * @throws IOException
	 */
	public String doPost(
			HttpsUtil httpsUtil,String urlPostAsynCmd,
			String jsonRequest,Map<String, String> header, String expectedStatus)
					throws UnsupportedOperationException, IOException{
    HttpResponse responsePostAsynCmd = httpsUtil.doPostJson(urlPostAsynCmd, header, jsonRequest);
    String responseBody = httpsUtil.getHttpResponseBody(responsePostAsynCmd);
    if(StringUtils.isNotEmpty(expectedStatus)){
    	
    }else{
    	
    }
    logger.info("异步指令开始, 返回状态: {}, 返回内容: {}",
    		responsePostAsynCmd.getStatusLine(),responseBody);
    return responseBody;
	}
}
