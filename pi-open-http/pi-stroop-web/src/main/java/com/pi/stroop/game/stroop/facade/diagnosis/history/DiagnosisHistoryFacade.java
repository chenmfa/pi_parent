package com.pi.stroop.game.stroop.facade.diagnosis.history;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.pi.base.util.time.DateTools;
import com.pi.stroop.dao.entity.StroopDiagnosisRecordEntity;
import com.pi.stroop.enumerate.DiagnosisLevelEnum;
import com.pi.stroop.game.stroop.vo.diagnosis.history.DiagnosisBriefHistoryVo;
import com.pi.uc.dao.entity.UserEntity;
import com.pi.uc.service.UcUserService;

@Service
public class DiagnosisHistoryFacade {
  @Autowired
  private UcUserService userService;
  @Autowired
  private MessageSource messageSource;
  
  public List<DiagnosisBriefHistoryVo> transDiagnsisEntityToBrief(
      List<StroopDiagnosisRecordEntity> histories){
    if(null == histories || histories.isEmpty()){
      return new ArrayList<DiagnosisBriefHistoryVo>(0);
    }
    List<DiagnosisBriefHistoryVo> list = new ArrayList<DiagnosisBriefHistoryVo>(histories.size());
    List<Long> userIds = new ArrayList<Long>(histories.size());
    for(StroopDiagnosisRecordEntity entity:histories){
      userIds.add(entity.getUserId());
    }
    List<UserEntity> users = userService.queryUserByIds(userIds);
    HashMap<Long, UserEntity> userMap = users.stream().collect(
        Collectors.toMap(UserEntity::getId, Function.identity(), 
            (key1, key2) -> key2, HashMap::new));
    for(StroopDiagnosisRecordEntity entity:histories){
      DiagnosisBriefHistoryVo vo = new DiagnosisBriefHistoryVo();
      vo.setDiagnosisDate(DateTools.getFullDate(entity.getCreateDate()));
      vo.setDiagnosisId(entity.getId());
      vo.setDiagnosisLevel(DiagnosisLevelEnum.getDiagnosisLevelDesc(entity.getDiagnosisLevel()));
      vo.setName(userMap.containsKey(entity.getUserId())?
          userMap.get(entity.getUserId()).getName():messageSource.getMessage("UC_USER.UNKNOWN_USER", null, "UC_USER.UNKNOWN_USER", Locale.CHINA));
      list.add(vo);
    }
    return list;
  }
}
