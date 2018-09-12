package com.pi.stroop.base.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.pi.base.dto.result.respcode.error.ErrorServer;
import com.pi.base.enumerate.redis.RedisCacheEnum;
import com.pi.base.exception.ServiceException;
import com.pi.base.util.cache.RedisUtil;
import com.pi.stroop.base.constants.StroopConstants;
import com.pi.uc.dao.entity.UserEntity;

public class AuthInterceptor implements HandlerInterceptor{

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String token = getRequest().getParameter("token");
    if(StringUtils.isBlank(token)){
      throw new ServiceException(
          ErrorServer.REQUEST_HAS_EXPIRED.getTag(),
          ErrorServer.REQUEST_HAS_EXPIRED.getErrorCode());
    }
    UserEntity userSession = RedisUtil.get(RedisCacheEnum.USER_LOGIN_TOKEN, UserEntity.class, token);
    if(null == userSession){
      throw new ServiceException(
          ErrorServer.REQUEST_HAS_EXPIRED.getTag(),
          ErrorServer.REQUEST_HAS_EXPIRED.getErrorCode());
    }
    request.setAttribute(StroopConstants.REQUEST_USER_ATTR, userSession);
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
    
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {
    
  }
  
  private HttpServletRequest getRequest(){
    return ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
  }
}
