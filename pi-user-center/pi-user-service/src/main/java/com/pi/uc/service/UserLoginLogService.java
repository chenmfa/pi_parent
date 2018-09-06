package com.pi.uc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.pi.uc.dao.entity.UserLoginLogEntity;
import com.pi.uc.dao.mapper.UserLoginLogMapper;
import com.pi.uc.model.userlogin.UserLoginForm;

@Validated
@Service
public class UserLoginLogService {
  private static final Logger logger =  LoggerFactory.getLogger(UcUserService.class);
  
  @Autowired
  private UserLoginLogMapper mapper;
  
  public void appenLoginLog(UserLoginForm form){
    if(!checkLoginForm(form)){
      return;
    }
    UserLoginLogEntity entity = translateUserLoginLog(form);
    mapper.insert(entity);
  }
  
  private boolean checkLoginForm(UserLoginForm form) {
    if(null == form){
      logger.error("登录日志为空");
      return false;
    }
    if(null == form.getLoginDate()){
      logger.error("登录时间为空, {}", form);
      return false;
    }
    if(null == form.getLoginIdentity()){
      logger.error("登录用户标志为空, {}", form);
      return false;
    }
    if(null == form.getLoginSource()){
      logger.error("登录来源为空, {}", form);
      return false;
    }
    if(null == form.getUserId()){
      logger.error("登录用户为空, {}", form);
      return false;
    }
    return true;
  }

  private UserLoginLogEntity translateUserLoginLog(UserLoginForm form){
    UserLoginLogEntity entity = new UserLoginLogEntity();
    entity.setAppVersion("");
    entity.setLoginArea("");
    entity.setLoginChannel("");
    entity.setLoginCity("");
    entity.setLoginDate(form.getLoginDate());
    entity.setLoginDevice("");
    entity.setLoginIdentity(form.getLoginIdentity());
    entity.setLoginRemindFlag(0);
    entity.setLoginRemindSummary("");
    entity.setLoginSource(form.getLoginSource());
    entity.setUserId(form.getUserId());
    return entity;
  }
}
