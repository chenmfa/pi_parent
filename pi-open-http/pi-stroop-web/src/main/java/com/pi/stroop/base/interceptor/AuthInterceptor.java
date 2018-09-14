package com.pi.stroop.base.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.pi.base.dto.result.respcode.error.ErrorServer;
import com.pi.base.enumerate.redis.RedisCacheEnum;
import com.pi.base.exception.ServiceException;
import com.pi.base.util.cache.RedisUtil;
import com.pi.base.util.http.RequestUtil;
import com.pi.common.http.annotation.InterceptorIgnore;
import com.pi.stroop.base.constants.StroopConstants;
import com.pi.uc.dao.entity.UserEntity;

public class AuthInterceptor extends HandlerInterceptorAdapter{
  private static final String MSG_TAG = "[全局记录]";
  private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    if(null != request.getRequestURI() && request.getRequestURI().contains(".")){
      logger.error("{},非法访问,请求来源： {}, 请求地址：{}", MSG_TAG, RequestUtil.getIpAddr(request), request.getRequestURI());
      response.setStatus(HttpStatus.FORBIDDEN.value());
      return false;
    }
    logger.debug("{},请求地址：{}, 请求参数：{}", MSG_TAG,
        request.getRequestURI(), JSON.toJSONString(request.getParameterMap()));
    HandlerMethod method = (HandlerMethod)handler;
    InterceptorIgnore ignoreAnno = method.getMethodAnnotation(InterceptorIgnore.class);
    if(null != ignoreAnno && ignoreAnno.value()){
      return true;
    }
    String token = getRequest().getParameter("token");
    if(StringUtils.isBlank(token)){
      throw new ServiceException(
          ErrorServer.REQUEST_HAS_EXPIRED.getTag(),
          ErrorServer.REQUEST_HAS_EXPIRED.getErrorCode());
    }
    UserEntity userSession = RedisUtil.get(RedisCacheEnum.USER_LOGIN_SESSION, UserEntity.class, token);
    if(null == userSession){
      throw new ServiceException(
          ErrorServer.REQUEST_HAS_EXPIRED.getTag(),
          ErrorServer.REQUEST_HAS_EXPIRED.getErrorCode());
    }
    request.setAttribute(StroopConstants.REQUEST_USER_ATTR, userSession);
    return true;
  }
  
  private HttpServletRequest getRequest(){
    return ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
  }
}
