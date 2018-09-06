package com.pi.stroop.wxmini.facade;

import org.springframework.stereotype.Component;

import com.pi.base.dto.result.respcode.user.UcResp;
import com.pi.base.exception.ServiceException;
import com.pi.stroop.wxmini.vo.UserInfoVo;
import com.pi.uc.dao.entity.UserEntity;

@Component
public class UserFacade {
  public UserInfoVo convertUserEntityToVo(UserEntity entity){
    if(null == entity){
      throw new ServiceException(
          UcResp.RESP_USER_NOT_FOUND.getTag(), 
          UcResp.RESP_USER_NOT_FOUND.getErrorCode());
    }
    UserInfoVo vo = new UserInfoVo();
    vo.setAge(entity.getAge());
    vo.setAvatar(entity.getAvatar());
    vo.setEducation(entity.getEducation());
    vo.setMobile(entity.getMobile());
    vo.setName(entity.getName());
    vo.setSex(entity.getSex());
    vo.setUserId(entity.getId());
    return vo;
  }
}
