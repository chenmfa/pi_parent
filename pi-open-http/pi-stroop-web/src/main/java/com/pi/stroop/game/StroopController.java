package com.pi.stroop.game;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pi.base.dto.result.AppResult;

public class StroopController {
  
  @PostMapping("/user/diagnosis")
  public AppResult postDiagnosisResult(){
    return null;
  }
  
  @RequestMapping("/user/diagnosis/history")
  public AppResult getDiagnosisHistory(){
    return null;
  }
  
  @RequestMapping("/user/diagnosis/detail")
  public AppResult getDiagnosisDetail(){
    return null;
  }
  
  @GetMapping("/user/diagnosis")
  public AppResult getDiagnosisData(){
    return null;
  }
  /**
   * @description 获取诊断结果, 是否要和提交合并？
   * @return
   */
  @GetMapping("/user/diagnosis/result")
  public AppResult getDiagnosisResult(){
    return null;
  }
}
