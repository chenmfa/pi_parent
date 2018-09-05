package com.pi.stroop.wxmini.controller;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pi.base.dto.result.AppResult;

@RequestMapping("/")
@RestController
public class WXController {

  @RequestMapping("/user/weChatBind")
  public AppResult weChatBind(@Validated
      @NotNull(message="来源不能为空") @RequestParam Long sourceId,BindingResult result1, @Validated
      @Length(min=2, max=40, message = "校验码不能为空") @RequestParam String wxCode, BindingResult result){
    return AppResult.OK;
  }
}
