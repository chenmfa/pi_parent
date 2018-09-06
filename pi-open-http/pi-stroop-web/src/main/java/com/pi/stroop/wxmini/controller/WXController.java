package com.pi.stroop.wxmini.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pi.base.dto.result.AppResult;
import com.pi.stroop.wxmini.facade.UserFacade;
import com.pi.stroop.wxmini.vo.UserInfoVo;
import com.pi.uc.dao.entity.UserEntity;
import com.pi.uc.model.user.UserPostForm;
import com.pi.uc.service.UcUserService;

@RequestMapping("/")
@RestController
public class WXController {
  @Autowired
  private UcUserService userService;
  @Autowired
  private UserFacade userFacade;
  
  @RequestMapping("/user/weChatBind")
  public AppResult weChatBind(Long sourceId, String wxCode) throws Exception{
    String token = userService.bindWeChat(sourceId, wxCode);
    return AppResult.newSuccessResult(token);
  }
  
  @PostMapping("/user/avatar")
  public AppResult updateUserAvatar(MultipartFile avatar, long loginUserId) throws Exception{
    return AppResult.newSuccessResult(userService.updateUserAvatar(avatar,loginUserId));
  }
  
  @PostMapping("/user/info")
  public AppResult updateUserInfo(UserPostForm user){
    userService.updateUserInfo(user);
    return AppResult.OK;
  }
  @GetMapping
  public AppResult getUserInfo(long loginUserId){
    UserEntity entity = userService.queryUserInfo(loginUserId);
    UserInfoVo vo = userFacade.convertUserEntityToVo(entity);
    return AppResult.newSuccessResult(vo);
  }
}
