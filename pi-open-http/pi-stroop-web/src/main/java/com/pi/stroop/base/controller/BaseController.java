package com.pi.stroop.base.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.pi.stroop.base.constants.StroopConstants;
import com.pi.uc.dao.entity.UserEntity;

public class BaseController {
  
  protected boolean isLogin(){
    return getRequest().getAttribute(StroopConstants.REQUEST_USER_ATTR) !=  null;
  }
  
  protected UserEntity getLoginUserInfo(){
    if(isLogin()){
      return (UserEntity)getRequest().getAttribute(StroopConstants.REQUEST_USER_ATTR);
    }
    return null;
  }
  
  protected Long getLoginUserId(){
    if(isLogin()){
      return ((UserEntity)getRequest().getAttribute(StroopConstants.REQUEST_USER_ATTR)).getId();
    }
    return null;
  }
  
  protected HttpServletRequest getRequest(){
    return ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
  }
}
