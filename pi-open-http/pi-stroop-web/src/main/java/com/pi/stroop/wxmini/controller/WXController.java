package com.pi.stroop.wxmini.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi.base.dto.result.AppResult;
import com.pi.uc.service.UcUserService;

@RequestMapping("/")
@RestController
public class WXController {
  @Autowired
  private UcUserService userService;
  
  @RequestMapping("/user/weChatBind")
  public AppResult weChatBind(Long sourceId, String wxCode) throws Exception{
    String token = userService.bindWeChat(sourceId, wxCode);
    return AppResult.newSuccessResult(token);
  }
}
