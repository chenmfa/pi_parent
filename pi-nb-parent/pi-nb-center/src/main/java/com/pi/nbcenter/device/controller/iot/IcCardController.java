package com.pi.nbcenter.device.controller.iot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi.base.dto.result.AppResult;
import com.pi.nbcenter.device.service.pi.external.PiIcCardService;

@RequestMapping("/iccard")
@RestController
public class IcCardController{
  @Autowired
  private PiIcCardService icCardService;
  
  @PostMapping("/delete")
  public AppResult deleteIcCard(String imei, String icCard, Long userId) 
      throws Exception{
    icCardService.deleteIcCard(imei, userId, icCard, 1L);
    return AppResult.OK;
  }
  
  @PostMapping("/add")
  public AppResult addIcCard(String imei, String icCard, Long userId) 
      throws Exception{
    Long cardId = icCardService.sendIcCard(imei, userId, icCard, 1L);
    return AppResult.newSuccessResult(cardId);
  }
}
