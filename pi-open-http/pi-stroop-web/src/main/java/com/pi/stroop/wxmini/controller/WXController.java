package com.pi.stroop.wxmini.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.pi.base.dto.result.AppResult;
import com.pi.common.http.annotation.InterceptorIgnore;
import com.pi.stroop.base.controller.BaseController;
import com.pi.stroop.wxmini.facade.UserFacade;
import com.pi.stroop.wxmini.vo.UserInfoVo;
import com.pi.uc.dao.entity.UserEntity;
import com.pi.uc.model.user.UserPostForm;
import com.pi.uc.model.userlogin.UserLoginPostForm;
import com.pi.uc.service.UcUserService;

@RequestMapping("/user")
@RestController
public class WXController extends BaseController{
  private static final Logger logger = LoggerFactory.getLogger(WXController.class);
  @Autowired
  private UcUserService userService;
  @Autowired
  private UserFacade userFacade;
  
  @InterceptorIgnore(desc = "微信绑定登陆")
  @RequestMapping("/weChatBind")
  public AppResult weChatBind(UserLoginPostForm user) throws Exception{
    logger.debug("[微信绑定登陆] 来源:{}, 校验码：{}", user.toString());
    String token = userService.bindWeChat(user);
    return AppResult.newSuccessResult(token);
  }
  
  @PostMapping("/avatar")
  public AppResult updateUserAvatar(MultipartFile avatar) throws Exception{
    return AppResult.newSuccessResult(userService.updateUserAvatar(avatar, getLoginUserId()));
  }
  
  @PostMapping("/info")
  public AppResult updateUserInfo(UserPostForm user){
    logger.debug("[修改用户信息] 内容为：{}", JSON.toJSONString(user));
    user.setLoginUserId(getLoginUserId());
    userService.updateUserInfo(user);
    return AppResult.OK;
  }
  
  @GetMapping("/info")
  public AppResult getUserInfo(){
    logger.debug("[获取用户信息] 当前用户为为：{}", getLoginUserId());
    UserEntity entity = userService.queryUserInfo(getLoginUserId());
    UserInfoVo vo = userFacade.convertUserEntityToVo(entity);
    return AppResult.newSuccessResult(vo);
  }
}
