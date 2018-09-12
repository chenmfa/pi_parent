package com.pi.stroop.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi.base.dto.result.AppResult;
import com.pi.stroop.base.controller.BaseController;
import com.pi.stroop.service.StroopInvitationService;

@RestController
public class ShareController extends BaseController{
  
  @Autowired
  StroopInvitationService invitationService;
  
  @PostMapping
  public AppResult bindShare(long inviterId){
    invitationService.share(inviterId, getLoginUserId());
    return AppResult.OK;
  }
  
  @GetMapping
  public AppResult getQrCode() throws Exception{
    String url = invitationService.getShareQrcode(getLoginUserId());
    return AppResult.newSuccessResult(url);
  }
}
