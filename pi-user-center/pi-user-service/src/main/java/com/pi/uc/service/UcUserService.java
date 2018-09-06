package com.pi.uc.service;

import java.text.MessageFormat;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.alibaba.fastjson.JSON;
import com.pi.base.enumerate.record.RecordState;
import com.pi.base.enumerate.redis.RedisCacheEnum;
import com.pi.base.exception.ServiceException;
import com.pi.base.util.cache.RedisUtil;
import com.pi.base.util.http.v2.HttpPostUtil;
import com.pi.config.model.PartnerAppConfig;
import com.pi.config.service.ConfigService;
import com.pi.uc.constants.WxConstants;
import com.pi.uc.dao.entity.UserEntity;
import com.pi.uc.dao.mapper.UserMapper;
import com.pi.uc.dao.param.UserParam;
import com.pi.uc.model.user.bo.UserForm;
import com.pi.uc.model.wx.WxAuthInfo;
import com.pi.uc.model.wx.WxUserBaseInfo;
import com.pi.uc.model.wx.WxUserInfo;

@Validated
@Service
public class UcUserService {
  private static final Logger logger =  LoggerFactory.getLogger(UcUserService.class);
  @Autowired
  private ConfigService configService;
  @Autowired
  private UserMapper userMapper;
  
  public String bindWeChat(
      @NotNull(message="UC_USER.SOURCE_ID_EMPTY") Long sourceId, 
      @NotNull(message="UC_USER.WECHAT_CODE_EMPTY") 
      @Length(min=20, max=50, message="UC_USER.WECHAT_CODE_NOT_CORRECT") String wxCode) throws Exception{
    //查询app配置
    PartnerAppConfig config = configService.queryPartnerAppConfig(sourceId);
    //校验授权
    WxUserInfo wxUserInfo = validWxAuthorization(wxCode, config);
    //获取用户信息
    UserEntity entity = createIfNotExistUser(wxUserInfo, sourceId);
    //保存登录会话
    
    return null;
  }
  
  private UserEntity createIfNotExistUser(WxUserInfo wxUserInfo, long sourceId) {
    UserParam param = new UserParam();
    param.setWxOpenid(wxUserInfo.getOpenid());
    if(StringUtils.isNotBlank(wxUserInfo.getUnionid())){
      param.setWxUnionid(wxUserInfo.getUnionid());
    }else{
      wxUserInfo.setUnionid("");
    }
    param.setState(RecordState.STATE_NORMAL.getCode());
    List<UserEntity> userList = userMapper.findList(param);
    UserEntity entity;
    if(null != userList && !userList.isEmpty()){
      entity = userList.get(0);
      entity.setNickName(wxUserInfo.getNickname());
      entity.setAvatar(wxUserInfo.getHeadimgurl());
      userMapper.updateById(entity);
    }else{
      entity = translateWxUserInfoToEntity(wxUserInfo);
      entity.setSourceId(sourceId);
      userMapper.insert(entity);      
    }
    return entity;
  }
  
  private UserEntity translateWxUserInfoToEntity(WxUserInfo wxUserInfo){
    UserEntity entity = new UserEntity();
    entity.setAge(0);
    entity.setMobile("");
    entity.setName(wxUserInfo.getNickname());
    entity.setNickName(wxUserInfo.getNickname());
    entity.setPassword("");    
    entity.setState(RecordState.STATE_NORMAL.getCode());
    entity.setWxOpenid(wxUserInfo.getOpenid());
    entity.setAvatar(wxUserInfo.getHeadimgurl());
    entity.setWxUnionid(wxUserInfo.getUnionid());
    return entity;
  }
  
  private WxUserInfo validWxAuthorization(String wxCode, PartnerAppConfig config)
      throws Exception{
    WxUserBaseInfo baseInfo = HttpPostUtil.getRestResult(
        MessageFormat.format(
            WxConstants.WCHAT_OPENID_PATTERN, 
            config.getAppId(), 
            config.getAppSecret(), 
            wxCode), 
        null, WxUserBaseInfo.class);
    checkWeChatAuth(baseInfo, new Object[]{config.getAppId(), config.getAppSecret(), wxCode}, wxCode);
    
//  刷新用户授权并尝试获取unionid
    WxUserBaseInfo refreshInfo = HttpPostUtil.getRestResult(
        String.format(
            WxConstants.WCHAT_REFRESH_TOKEN_PATTERN, 
            config.getAppId(), 
            baseInfo.getRefresh_token()), 
        null, WxUserBaseInfo.class);
    checkWeChatAuth(refreshInfo, new Object[]{config.getAppId(), config.getAppSecret(), wxCode}, wxCode);
    refreshInfo.setUnionid(baseInfo.getUnionid());
    BeanUtils.copyProperties(baseInfo, refreshInfo);
    
// 获取微信用户信息   
    WxUserInfo wxUserInfo = HttpPostUtil.getRestResult(
        String.format(WxConstants.WCHAT_USER_INFO_PATTERN, baseInfo.getAccess_token(), baseInfo.getOpenid()),
        null, WxUserInfo.class);
    checkWeChatAuth(wxUserInfo, new Object[]{baseInfo.getAccess_token(), baseInfo.getOpenid()}, wxCode);
    
//  设备缓存
    RedisUtil.directset(
        RedisCacheEnum.USER_WX_INFO, 
        JSON.toJSONString(baseInfo), 
        baseInfo.getExpires_in(), 
        new Object[]{baseInfo.getOpenid()});
    wxUserInfo.setUnionid(baseInfo.getUnionid());
    return wxUserInfo;
  }
  
  
  private void checkWeChatAuth(WxUserBaseInfo userInfo, Object[] param, String wxCode){
    if(null == userInfo || null == userInfo.getAccess_token() ||null == userInfo.getRefresh_token()){
      logger.error("获取用户授权失败, 请求参数为: {}, 授权码:{}", MessageFormat.format(
          WxConstants.WCHAT_OPENID_PATTERN, param), wxCode);
      throw new ServiceException("USERACTION.WECHAT_AUTH_FAILED");
    }
  }
  private void checkWeChatAuth(WxUserInfo userInfo, Object[] param, String wxCode){
    if(null == userInfo){
      logger.error("获取用户信息失败, 请求参数为: {}, 授权码:{}", 
          MessageFormat.format(WxConstants.WCHAT_USER_INFO_PATTERN, param), wxCode);
      throw new ServiceException("USERACTION.WECHAT_AUTH_FAILED");
    }
  }
}
