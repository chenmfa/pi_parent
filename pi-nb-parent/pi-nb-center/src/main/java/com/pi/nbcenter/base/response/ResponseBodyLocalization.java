package com.pi.nbcenter.base.response;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.pi.base.dto.result.AppResult;

/**
 * @description 国际化,统一消息过滤类
 * @author chenmfa
 * @date 2018年9月5日16:37:34
 */
@ControllerAdvice
public class ResponseBodyLocalization implements ResponseBodyAdvice<AppResult>{
  @Autowired
  private MessageSource messageSource;
  @Override
  public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }

  @Override
  public AppResult beforeBodyWrite(AppResult body, MethodParameter returnType, MediaType selectedContentType,
      Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
      ServerHttpResponse response) {
    if(null != body && StringUtils.isNotBlank(body.getMsg())){
      body.setMsg(messageSource.getMessage(body.getMsg(), null, body.getMsg(), Locale.CHINA));
    }
    return body;
  }
}
