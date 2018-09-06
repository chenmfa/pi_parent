package com.pi.base.util.Assert;

import org.apache.commons.lang3.StringUtils;

import com.pi.base.dto.result.respcode.error.ErrorServer;
import com.pi.base.exception.ServiceException;

public class Assert {
  public static void notBlank(String args){
    if(StringUtils.isBlank(args)){
      throw new ServiceException(
          ErrorServer.SERVICE_PARAM_EMPTY.getTag(), 
          ErrorServer.SERVICE_PARAM_EMPTY.getErrorCode());
    }
  }
  
  public static void notNull(Object args){
    if(null == args){
      throw new ServiceException(
          ErrorServer.SERVICE_PARAM_EMPTY.getTag(), 
          ErrorServer.SERVICE_PARAM_EMPTY.getErrorCode());
    }
  }
}
