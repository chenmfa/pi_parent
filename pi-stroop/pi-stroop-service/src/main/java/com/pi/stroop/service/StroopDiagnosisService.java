package com.pi.stroop.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.pi.stroop.dao.entity.StroopDiagnosisHistoryEntity;
import com.pi.stroop.dao.entity.StroopInvitationEntity;
import com.pi.stroop.dao.mapper.StroopDiagnosisHistoryMapper;

@Validated
@Service
public class StroopDiagnosisService {
  @Autowired
  private StroopInvitationService invitationServce;
  @Autowired
  private StroopDiagnosisHistoryMapper diagnosisHistoryService;
  
  public List<StroopDiagnosisHistoryEntity> queryUserDiagnosisHistory(
      @NotNull(message="UC_USER.USER_ID_EMPTY") Long userId){
    //查询我的邀请记录
    List<StroopInvitationEntity> invitationList = invitationServce.queryUserInvitation(userId);
    List<Long> inviteIds = getUserInvites(invitationList);
    //查询我和邀请用户的记录
    return diagnosisHistoryService.queryUserDiagnosisHistory(userId, inviteIds);
  }
  
  private List<Long> getUserInvites(List<StroopInvitationEntity> invitationList){
    if(null == invitationList){
      return null;
    }
    List<Long> list = new ArrayList<>();
    for(StroopInvitationEntity entity:invitationList){
      list.add(entity.getId());
    }
    return list;
  }
}
