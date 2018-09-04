package com.pi.nbcenter.device.service.base;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.pi.base.exception.ServiceException;
import com.pi.base.util.http.HttpPostUtil;
import com.pi.nbcenter.base.constants.base.PartnerNotifyType;
import com.pi.nbcenter.base.constants.base.RecordState;
import com.pi.nbcenter.base.constants.text.InternalTextTemplate;
import com.pi.nbcenter.base.errorcode.base.ErrorPartnerSubscribe;
import com.pi.nbcenter.device.entity.auto.BasePartnerSubscription;
import com.pi.nbcenter.device.entity.auto.BasePartnerSubscriptionExample;
import com.pi.nbcenter.device.entity.auto.IotDeviceInfo;
import com.pi.nbcenter.device.mapper.auto.BasePartnerSubscriptionMapper;
import com.pi.nbcenter.device.service.iot.IotDevService;
import com.pi.nbcenter.util.comm.ObjectUtil;

@Service
public class BasePartnerSubscriptionService {
  private static final Logger logger = LoggerFactory.getLogger(BasePartnerSubscriptionService.class);
  @Autowired
  private BasePartnerSubscriptionMapper subscriptionMapper;
  @Autowired
  private IotDevService devService;
//  @Autowired
//  private HttpClientUtil httpClientUtil;
  
  /**
   * @description 订阅消息, 理论上需要支持多地址订阅(一个地址只能有一种订阅方式)
   * @param notifyUrl 订阅地址
   * @param notifyType 订阅类型
   */
  public void addPartnerSubscription(Long partnerCode, Integer notifyType, String notifyUrl){
    addPartnerSubscription(partnerCode, notifyType, notifyUrl, null);
  }
  /**
   * @description 订阅消息, 理论上需要支持多地址订阅(一个地址只能有一种订阅方式)
   * @param partnerCode 合作商编号
   * @param notifyUrl 订阅地址
   * @param notifyType 订阅类型
   * @param remark 备注
   */
  public void addPartnerSubscription(Long partnerCode, Integer notifyType, String notifyUrl, String remark){
    checkSubscription(partnerCode, notifyType, notifyUrl);
    createIfNotExist(partnerCode, notifyType, notifyUrl, remark);
  }
  /**
   * @description 查询地址是否已订阅
   * @param partnerCode 合作商编号
   * @param notifyUrl 订阅地址
   */
  public BasePartnerSubscription queryPartnerSubscription(Long partnerCode, String notifyUrl){
    BasePartnerSubscriptionExample example = new BasePartnerSubscriptionExample();
    example.createCriteria().andPartnerCodeEqualTo(partnerCode)
    .andNotifyUrlEqualTo(notifyUrl).andStateEqualTo(RecordState.STATE_NORMAL.getCode());
    return ObjectUtil.getOne(subscriptionMapper.selectByExample(example));
  }
  
  /**
   * @description 查询地址是否已订阅
   * @param partnerCode 合作商编号
   * @param notifyUrl 订阅地址
   */
  public List<BasePartnerSubscription> queryPartnerSubscription(Long partnerCode){
    BasePartnerSubscriptionExample example = new BasePartnerSubscriptionExample();
    example.createCriteria().andPartnerCodeEqualTo(partnerCode)
    .andStateEqualTo(RecordState.STATE_NORMAL.getCode());
    return subscriptionMapper.selectByExample(example);
  }
  
  /**
   * @description 取消订阅
   * @param partnerCode 合作商编号
   * @param notifyUrl 订阅地址
   */
  public void deletePartnerSubscription(Long partnerCode, String notifyUrl){
    BasePartnerSubscription recordCheck = 
        queryPartnerSubscription(partnerCode, notifyUrl);
    if(null == recordCheck){
      throw new ServiceException(
          ErrorPartnerSubscribe.ERR_SUBSCRIPTION_NOT_FOUND.getKey(), 
          ErrorPartnerSubscribe.ERR_SUBSCRIPTION_NOT_FOUND.getCode());
    }
    BasePartnerSubscription record = new BasePartnerSubscription();
    record.setId(recordCheck.getId());
    record.setUpdateDate(new Date());
    record.setState(RecordState.STATE_WASTE.getCode());
    subscriptionMapper.updateByPrimaryKeySelective(record);
  }
  
  public void pushToPartner(String deviceId, JSONObject data) {
    if(StringUtils.isBlank(deviceId) || null == data){
      logger.error("订阅推送数据为空, 设备编号：{}- 透传数据： {}", deviceId, data);
      return;
    }
    IotDeviceInfo info = devService.queryDBDeviceInfo(deviceId);
    if(null == info){
      logger.error("设备{}的注册信息不存在, 暂不推送订阅消息, 设备编号：{}- 透传数据： {}", deviceId, data);
      return;
    }
    data.put("imei", info.getIotDevImei());
    data.put("deviceId", info.getNbDevId());
    List<BasePartnerSubscription> subscribList = queryPartnerSubscription(info.getPartnerCode());
    pushDataToRemote(subscribList, data);
  }
  
  private void createIfNotExist(Long partnerCode, Integer notifyType, String notifyUrl, String notifyRemark){
    BasePartnerSubscription record = new BasePartnerSubscription();
    record.setPartnerCode(partnerCode);
    record.setNotifyType(notifyType);
    record.setNotifyUrl(notifyUrl);
    record.setNotifyRemark(null == notifyRemark?InternalTextTemplate.DEFAULT_SUBSCRIPTION_REMARK:notifyRemark);
    //查询通知是否已存在, 已存在则覆盖
    BasePartnerSubscription recordCheck = 
        queryPartnerSubscription(record.getPartnerCode(), record.getNotifyUrl());
    Calendar cal = Calendar.getInstance();
    record.setUpdateDate(cal.getTime());
    if(null == recordCheck){
      record.setState(RecordState.STATE_NORMAL.getCode());
      record.setCreateDate(cal.getTime());
      subscriptionMapper.insertSelective(record);
    }else{
      record.setCreateDate(null);
      record.setVersion(recordCheck.getVersion() + 1);
      subscriptionMapper.updateByPrimaryKeySelective(recordCheck);
    }
  }
  
  private void pushDataToRemote(List<BasePartnerSubscription> subscribList, JSONObject data){
    if(null != subscribList && !subscribList.isEmpty() && null != data){
      for(BasePartnerSubscription subscribe:subscribList){
        String result;
        try {
          result = HttpPostUtil.postByHttpClient(subscribe.getNotifyUrl(), data, HttpPostUtil.JSON_CONTENT_TYPE);
          logger.debug(result);
        } catch (Exception e) {
          logger.error("推送订阅失败, 网络错误, 推送地址:{}", subscribe.getNotifyUrl());
        }
      }
    }
  }
  
  private void checkNotifyType(Integer notifyCode){
    if(null == notifyCode){
      throw new ServiceException(
          ErrorPartnerSubscribe.ERR_NOTIFY_TYPE_EMPTY.getKey(),
          ErrorPartnerSubscribe.ERR_NOTIFY_TYPE_EMPTY.getCode());
    }
    PartnerNotifyType notifyType = PartnerNotifyType.getNotifyType(notifyCode);
    if(null == notifyType)
      throw new ServiceException(
          ErrorPartnerSubscribe.ERR_NOTIFY_TYPE_NOT_SUPPORT.getKey(),
          ErrorPartnerSubscribe.ERR_NOTIFY_TYPE_NOT_SUPPORT.getCode());
    if(notifyType.getCode() != PartnerNotifyType.HTTP_POST.getCode())
      throw new ServiceException(
          ErrorPartnerSubscribe.ERR_NOTIFY_TYPE_NOT_FINISHED.getKey(),
          ErrorPartnerSubscribe.ERR_NOTIFY_TYPE_NOT_FINISHED.getCode());
  }
  
  private void checkNotifyPartner(Long partnerCode){
    if(null == partnerCode){
      throw new ServiceException(
          ErrorPartnerSubscribe.ERR_PARTNER_EMPTY.getKey(),
          ErrorPartnerSubscribe.ERR_PARTNER_EMPTY.getCode());
    }
  }
  
  private void checkNotifyUrl(String notifyUrl){
    if(null == notifyUrl){
      throw new ServiceException(
          ErrorPartnerSubscribe.ERR_NOTIFY_URL_EMPTY.getKey(),
          ErrorPartnerSubscribe.ERR_NOTIFY_URL_EMPTY.getCode());
    }
  }
  private void checkSubscription(Long partnerCode, Integer notifyType, String notifyUrl){
    checkNotifyType(notifyType);
    checkNotifyPartner(partnerCode);
    checkNotifyUrl(notifyUrl);
  }
}
