package com.pi.nbcenter.base.response;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pi.base.dto.result.AppResult;
import com.pi.base.dto.result.respcode.error.ErrorServer;
import com.pi.base.exception.ServiceException;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionController {
  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionController.class);
  @Autowired
  private MessageSource messageSource;
  
  @ExceptionHandler(value = Exception.class)
  public AppResult handleException(HttpServletRequest request, HttpServletResponse response, 
      Object handler, Exception e){
    if(e instanceof ServiceException){
      return AppResult.newFailResult(
          messageSource.getMessage(e.getMessage(), null, e.getMessage(), Locale.CHINA), 
          ((ServiceException) e).getCode());
    }
    logger.error(e.getMessage());
    return AppResult.newFailResult(
        messageSource.getMessage(
            ErrorServer.REQUEST_OPERATION_UNAVAILABLE.getTag(), null, Locale.CHINA), 
        0);
  }
}
