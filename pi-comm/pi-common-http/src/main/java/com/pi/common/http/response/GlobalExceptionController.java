package com.pi.common.http.response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.ServletRequestBindingException;
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
  
  @ExceptionHandler(value = Exception.class)
  public AppResult handleException(HttpServletRequest request, HttpServletResponse response, 
      Object handler, Exception e){
    logger.error(e.getMessage(),e);
    return AppResult.newFailResult(
        ErrorServer.REQUEST_OPERATION_UNAVAILABLE.getTag(),
        ErrorServer.REQUEST_OPERATION_UNAVAILABLE.getErrorCode());
  }
  
  @ExceptionHandler(ServletRequestBindingException.class)
  public AppResult handleServletRequestBindingException(){
    return AppResult.newFailResult(ErrorServer.REQUEST_OPERATION_UNAVAILABLE.getTag(),
        ErrorServer.REQUEST_OPERATION_UNAVAILABLE.getErrorCode());
  }
  
  @ExceptionHandler(ServiceException.class)
  public AppResult handleServiceException(ServiceException e){
    return AppResult.newFailResult(e.getMessage(), 
        e.getCode());
  }
  
  @ExceptionHandler(ConstraintViolationException.class)
  public AppResult handleConstraintViolationException(ConstraintViolationException e){
    if(null != e.getConstraintViolations() && !e.getConstraintViolations().isEmpty()){
      return AppResult.newFailResult(e.getConstraintViolations().iterator().next().getMessage());
    }
    return AppResult.newFailResult(ErrorServer.REQUEST_OPERATION_UNAVAILABLE.getTag(),
        ErrorServer.REQUEST_OPERATION_UNAVAILABLE.getErrorCode());
  }
 
}
