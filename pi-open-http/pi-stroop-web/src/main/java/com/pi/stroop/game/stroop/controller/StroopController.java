package com.pi.stroop.game.stroop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi.base.dto.result.AppResult;
import com.pi.stroop.base.controller.BaseController;
import com.pi.stroop.dao.entity.StroopDiagnosisRecordEntity;
import com.pi.stroop.game.stroop.facade.diagnosis.history.DiagnosisHistoryFacade;
import com.pi.stroop.game.stroop.vo.diagnosis.history.DiagnosisBriefHistoryVo;
import com.pi.stroop.service.StroopDiagnosisService;

@RestController
public class StroopController extends BaseController{
  
  @Autowired
  private DiagnosisHistoryFacade historyFacade;
  @Autowired
  private StroopDiagnosisService diagnosisService;
  
  @PostMapping("/user/diagnosis")
  public AppResult postDiagnosisResult(){
    return null;
  }
 // r06i62qcuzwbi73awxbuy5r3rr1lbgfh
  @RequestMapping("/user/diagnosis/history")
  public AppResult getDiagnosisHistory(){
    List<StroopDiagnosisRecordEntity> diagnosisList = 
        diagnosisService.queryUserDiagnosisHistory(getLoginUserId());
    List<DiagnosisBriefHistoryVo> voList = historyFacade.transDiagnsisEntityToBrief(diagnosisList);
    return AppResult.newSuccessResult(voList);
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
