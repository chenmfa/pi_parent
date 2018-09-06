package com.pi.stroop.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi.base.dto.result.AppResult;
import com.pi.stroop.service.StroopInvitationService;

@RestController
public class ShareController {
  
  @Autowired
  StroopInvitationService invitationService;
  
  @PostMapping
  public AppResult bindShare(long inviterId, long loginUserId){
    invitationService.share(inviterId, loginUserId);
    return AppResult.OK;
  }
  
  @GetMapping
  public AppResult getQrCode(long loginUserId) throws Exception{
    String url = invitationService.getShareQrcode(loginUserId);
    return AppResult.newSuccessResult(url);
  }
}
