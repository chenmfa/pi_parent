package com.pi.stroop.game.stroop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi.base.dto.result.AppResult;
import com.pi.stroop.base.controller.BaseController;
import com.pi.stroop.service.StroopInvitationService;

@RequestMapping("/bind")
@RestController
public class ShareController extends BaseController{
  
  @Autowired
  StroopInvitationService invitationService;
  
  @PostMapping("/bind")
  public AppResult bindShare(long inviterId){
    invitationService.share(inviterId, getLoginUserId());
    return AppResult.OK;
  }
  
  @GetMapping("/qrcode")
  public AppResult getQrCode() throws Exception{
    String url = invitationService.getShareQrcode(getLoginUserId());
    return AppResult.newSuccessResult(url);
  }
}
