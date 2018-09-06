package com.pi.stroop.service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.pi.base.contants.WxConstants;
import com.pi.base.dto.result.respcode.stroop.StroopResp;
import com.pi.base.enumerate.record.RecordState;
import com.pi.base.enumerate.redis.RedisCacheEnum;
import com.pi.base.exception.ServiceException;
import com.pi.base.util.cache.RedisUtil;
import com.pi.base.util.http.v2.HttpPostUtil;
import com.pi.config.model.PartnerAppConfig;
import com.pi.config.service.ConfigService;
import com.pi.stroop.dao.entity.StroopInvitationEntity;
import com.pi.stroop.dao.mapper.StroopInvitationMapper;
import com.pi.stroop.dao.param.StroopInvitationParam;
import com.pi.stroop.model.wx.WxAccessTokenInfo;
import com.pi.uc.dao.entity.UserEntity;
import com.pi.uc.service.UcUserService;

@Validated
@Service
public class StroopInvitationService {
  @Autowired
  private StroopInvitationMapper invitationMapper;
  @Autowired
  private UcUserService userService;
  @Autowired
  private ConfigService configService;
  
  public void share(
      @NotNull(message="STROOP.INVITER_ID_EMPTY") Long inviterId, 
      @NotNull(message="STROOP.USER_ID_EMPTY") Long loginUserId){
    List<StroopInvitationEntity> list = getInvitationInfo(loginUserId);
    StroopInvitationEntity matched = getExistInvite(loginUserId, inviterId, list);
    if(null == matched){
      StroopInvitationEntity invitationInfo = new StroopInvitationEntity();
      invitationInfo.setInviterId(inviterId);
      invitationInfo.setUserId(loginUserId);
      invitationInfo.setUserPv(1);
      if(null == list || list.isEmpty()){        
        invitationInfo.setInviteState(RecordState.STATE_NORMAL.getCode());
      }else{
        invitationInfo.setInviteState(RecordState.STATE_DELETE.getCode());
      }
      invitationMapper.insert(invitationInfo);
    }else{
      matched.setUserPv(matched.getUserPv() + 1);
      invitationMapper.updateById(matched);
    }
  }
  
  public String getShareQrcode(
      @NotNull(message="STROOP.USER_ID_EMPTY") Long loginUserId) 
          throws Exception{
    UserEntity userEntity = userService.queryUserInfo(loginUserId);
    //查询app配置
    PartnerAppConfig config = configService.queryPartnerAppConfig(userEntity.getSourceId());
    String accessToken = getWxAccessToken(config);
    return getWxMiniQrcode(accessToken, loginUserId);
  }
  
  private List<StroopInvitationEntity> getInvitationInfo(Long loginUserId){
    StroopInvitationParam param = new StroopInvitationParam();
    param.setUserId(loginUserId);
    List<StroopInvitationEntity> list = invitationMapper.findList(param);
    return list;
  }
  private StroopInvitationEntity getExistInvite(Long loginUserId, Long inviterId, 
      List<StroopInvitationEntity> list){
    if(null == list || list.isEmpty()){
      return null;
    }
    for(StroopInvitationEntity invite:list){      
      if(invite.getUserId().intValue() == loginUserId 
          && invite.getInviterId().intValue() == inviterId){
        return invite;
      }
    }
    return null;
  }
  
  private String getWxAccessToken(PartnerAppConfig config) throws Exception{
    String token = RedisUtil.get(RedisCacheEnum.WX_ACCESS_TOKEN);
    if(StringUtils.isBlank(token)){
      WxAccessTokenInfo tokenInfo = HttpPostUtil.getRestResult(
          MessageFormat.format(
              WxConstants.WCHAT_APP_TOKEN_URL, 
              config.getAppId(), 
              config.getAppSecret()), 
          null, WxAccessTokenInfo.class);
      checkWxAccessTokenInfo(tokenInfo);
      token = tokenInfo.getAccess_token();
    }
    return token;
  }
  
  private String getWxMiniQrcode(String token, long loginUserId) throws Exception{
    Map<String, String> paramMap = new HashMap<String,String>();
    paramMap.put("scene", "SHARE_BY_QR");
    paramMap.put("page", "pages/index?userId=" + loginUserId);
    paramMap.put("width", "430");
    String qrCode = HttpPostUtil.postJSON(MessageFormat.format(
            WxConstants.WCHAT_MINI_QRCODE_URL,
            token), paramMap);
    return qrCode;
    
  }
  
  private void checkWxAccessTokenInfo(WxAccessTokenInfo tokenInfo){
    if(null == tokenInfo || StringUtils.isBlank(tokenInfo.getAccess_token())){
      throw new ServiceException(
          StroopResp.RESP_WX_TOKEN_FAILED.getTag(),
          StroopResp.RESP_WX_TOKEN_FAILED.getErrorCode());
    }
  }
}
