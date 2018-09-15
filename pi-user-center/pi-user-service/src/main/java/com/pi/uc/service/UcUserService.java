package com.pi.uc.service;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.pi.base.contants.WxConstants;
import com.pi.base.dto.result.respcode.error.ErrorServer;
import com.pi.base.dto.result.respcode.user.UcResp;
import com.pi.base.enumerate.file.FileSource;
import com.pi.base.enumerate.record.RecordState;
import com.pi.base.enumerate.redis.RedisCacheEnum;
import com.pi.base.exception.ServiceException;
import com.pi.base.util.cache.RedisUtil;
import com.pi.base.util.http.v2.HttpPostUtil;
import com.pi.base.util.transfer.windows.FileUtil;
import com.pi.config.model.PartnerAppConfig;
import com.pi.config.service.ConfigService;
import com.pi.uc.dao.entity.UserEntity;
import com.pi.uc.dao.mapper.UserMapper;
import com.pi.uc.dao.param.UserParam;
import com.pi.uc.model.user.UserPostForm;
import com.pi.uc.model.userlogin.UserLoginPostForm;
import com.pi.uc.model.wx.mini.WxMiniAuthInfo;
import com.pi.uc.util.TokenUtil;

@Validated
@Service
public class UcUserService {
  private static final Logger logger =  LoggerFactory.getLogger(UcUserService.class);
  @Autowired
  private ConfigService configService;
  @Autowired
  private UserMapper userMapper;
  @Value("${server.ftp.host}")
  private String ftpHost;
  @Value("${server.ftp.password}")
  private String ftpPass;
  @Value("${server.ftp.user}")
  private String ftpUserName;
  @Value("${server.ftp.parentFolder}")
  private String ftpParentFolder;
  
  public String bindWeChat(@Valid UserLoginPostForm userInfo) throws Exception{
    //查询app配置
    PartnerAppConfig config = configService.queryPartnerAppConfig(userInfo.getSourceId());
    //校验授权
    WxMiniAuthInfo wxUserInfo = validWxAuthorization(userInfo.getWxCode(), config);
    //获取用户信息
    UserEntity entity = createIfNotExistUser(wxUserInfo, userInfo);
    //保存登录会话
    String token  = TokenUtil.getUserToken(entity);
    return token;
  }
  
  public String updateUserAvatar(MultipartFile file,
      @NotNull(message="UC_USER.USER_ID_EMPTY") Long userId) throws Exception{
    validFileSource(file);
    //查询用户信息
    UserEntity entity = queryUserInfo(userId);
    String url = transferAvatar(file);
    //更新用户信息
    entity.setAvatar(url);
    userMapper.updateById(entity);
    return url;
  }
  
  public void updateUserInfo(@Valid UserPostForm form){
    UserEntity entity = queryUserInfo(form.getLoginUserId());
    entity.setAge(form.getAge());
    entity.setMobile(form.getMobile());
    entity.setName(form.getName());
    entity.setSex(form.getSex());
    entity.setEducation(form.getEducation());
    int i = userMapper.updateById(entity);
    if(i != 1){
      throw new ServiceException(
          ErrorServer.OPERATE_FAILED.getTag(),
          ErrorServer.OPERATE_FAILED.getErrorCode());
    }
  }
  
  public UserEntity queryUserInfo(@NotNull(message="UC_USER.USER_ID_EMPTY") Long userId){
    UserEntity entity = userMapper.findOne(userId);
    if(null == entity){
      throw new ServiceException(
          UcResp.RESP_USER_NOT_FOUND.getTag(),
          UcResp.RESP_USER_NOT_FOUND.getErrorCode());
    }
    return entity;
  }
  
  private void validFileSource(MultipartFile file){
    if(file.isEmpty()){
      throw new ServiceException("UC_USER.AVATAR_STREAM_IS_EMPTY");
    }
    int size = (int) file.getSize();
    if(size > 2*1024*1024){
      throw new ServiceException("UC_USER.AVATAR_OVER_MAX");
    }
  }
  
  private String transferAvatar(MultipartFile file) throws Exception{
    File dest = new File("temp.png");
    file.transferTo(dest);
    String parentFplder = ftpParentFolder + "/" + FileSource.USER_PIC.name().toLowerCase();
    String resultFile = FileUtil.transferToRemote(
        ftpHost, ftpUserName, ftpPass, 
        parentFplder, dest);
    String url = ftpHost + parentFplder + "/" + resultFile;
    return url;
  }
  
  private UserEntity createIfNotExistUser(WxMiniAuthInfo wxUserInfo, UserLoginPostForm user) {
    UserParam param = new UserParam();
    param.setWxOpenid(wxUserInfo.getOpenid());
    if(StringUtils.isNotBlank(wxUserInfo.getUinionid())){
      param.setWxUnionid(wxUserInfo.getUinionid());
    }else{
      wxUserInfo.setUinionid("");;
    }
    param.setState(RecordState.STATE_NORMAL.getCode());
    List<UserEntity> userList = userMapper.findList(param);
    UserEntity entity;
    if(null != userList && !userList.isEmpty()){
      entity = userList.get(0);
      if((user.getGender() != entity.getSex() 
          && user.getGender()>0 && user.getGender()<3)){
        entity.setSex(user.getGender());
      }
      if(StringUtils.isNotBlank(user.getAvatarUrl()) 
          && entity.getAvatar().equals(user.getAvatarUrl())){
        entity.setAvatar(user.getAvatarUrl());
      }
      userMapper.updateById(entity);
    }else{
      entity = translateWxUserInfoToEntity(wxUserInfo, user);
      entity.setSourceId(user.getSourceId());
      userMapper.insert(entity);      
    }
    return entity;
  }
  
  private UserEntity translateWxUserInfoToEntity(WxMiniAuthInfo wxUserInfo, UserLoginPostForm user){
    UserEntity entity = new UserEntity();
    entity.setAge(0);
    entity.setSex((user.getGender()>0 && user.getGender()<3)? 
        user.getGender():1);
    entity.setMobile("");
    entity.setName("");
    entity.setNickName(null != user.getNickName()?user.getNickName():"");
    entity.setPassword("");    
    entity.setState(RecordState.STATE_NORMAL.getCode());
    entity.setWxOpenid(wxUserInfo.getOpenid());
    entity.setAvatar(null != user.getAvatarUrl() ? user.getAvatarUrl():"");
    entity.setEducation("");
    entity.setWxUnionid(wxUserInfo.getUinionid());
    return entity;
  }
  
  private WxMiniAuthInfo validWxAuthorization(String wxCode, PartnerAppConfig config)
      throws Exception{
    WxMiniAuthInfo baseInfo = HttpPostUtil.getRestResult(
        MessageFormat.format(
            WxConstants.WCHAT_MINI_OPENID_PATTERN, 
            config.getAppId(), 
            config.getAppSecret(), 
            wxCode), 
        null, WxMiniAuthInfo.class);
    checkWeChatAuth(baseInfo, new Object[]{config.getAppId(), config.getAppSecret(), wxCode}, wxCode);
    
//  设备缓存
    RedisUtil.directset(
        RedisCacheEnum.USER_WX_INFO, 
        JSON.toJSONString(baseInfo),
        new Object[]{baseInfo.getOpenid()});
    return baseInfo;
  }
  
  
  private void checkWeChatAuth(WxMiniAuthInfo userInfo, Object[] param, String wxCode){
    if(null == userInfo 
        || (null != userInfo.getErrcode() && userInfo.getErrcode()>0)){
      logger.error("获取用户授权失败, 请求参数为: {}, 授权码:{}", MessageFormat.format(
          WxConstants.WCHAT_OPENID_PATTERN, param), wxCode);
      throw new ServiceException("USERACTION.WECHAT_AUTH_FAILED");
    }
  }
}
