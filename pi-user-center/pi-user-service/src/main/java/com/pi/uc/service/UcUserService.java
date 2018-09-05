package com.pi.uc.service;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
public class UcUserService {
  
  public String bindWeChat(
      @NotNull(message="UC_USER.SOURCE_ID_EMPTY") Long sourceId, 
      @Length(min=20, max=50, message="UC_USER.WECHAT_ID_EMPTY") String wxCode){
    //查询app配置
    
    //校验授权
    
    //生成登陆信息
    return null;
  }
}
