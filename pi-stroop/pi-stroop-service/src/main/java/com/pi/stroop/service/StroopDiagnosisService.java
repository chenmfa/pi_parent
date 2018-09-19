package com.pi.stroop.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.pi.base.exception.ServiceException;
import com.pi.base.util.time.DateTools;
import com.pi.stroop.dao.entity.StroopDiagnosisRecordEntity;
import com.pi.stroop.dao.entity.StroopInvitationEntity;
import com.pi.stroop.dao.entity.StroopTaskRecordEntity;
import com.pi.stroop.dao.mapper.StroopDiagnosisRecordMapper;
import com.pi.stroop.enumerate.DiagnosisResponseState;
import com.pi.stroop.enumerate.DiagnosisStageEnum;
import com.pi.stroop.model.diagnosis.DiagnosisResult;
import com.pi.stroop.model.diagnosis.DiagnosisTask;
import com.pi.stroop.model.dto.StroopTaskRecordPostForm;

@Validated
@Service
public class StroopDiagnosisService {
  @Autowired
  private StroopInvitationService invitationServce;
  @Autowired
  private StroopDiagnosisRecordMapper diagnosisRecordMapper;
  @Autowired
  private StroopDiagnosisTaskService diagnosisTaskService;
  
  public List<StroopDiagnosisRecordEntity> queryUserDiagnosisHistory(
      @NotNull(message="STROOP_RECORD.USER_ID_IS_EMPTY") Long userId){
    //查询我的邀请记录
    List<StroopInvitationEntity> invitationList = invitationServce.queryUserInvitation(userId);
    List<Long> inviteIds = getUserInvites(invitationList);
    //查询我和邀请用户的记录
    return diagnosisRecordMapper.queryUserDiagnosisHistory(userId, inviteIds);
  }
  @Transactional
  public DiagnosisResult uploadDiagnosisResult(
      @NotNull(message="STROOP_RECORD.USER_ID_IS_EMPTY") Long userId,
      @NotBlank(message="STROOP.DIAGNOSIS_DATA_IS_EMPTY") String diagnosisPeriod, 
      @NotNull(message="STROOP.STAGE_IS_ENPTY") Integer stage, 
      @NotNull(message="STROOP.START_TIME_IS_ENPTY") Long start) {
    DiagnosisStageEnum diagnosisStage = DiagnosisStageEnum.getDiagnosisStage(stage);
    if(null == diagnosisStage)
      throw new ServiceException("STROOP.STAGE_NOT_FOUND");
    if(!diagnosisPeriod.matches("(\\d.*-\\d)(;(1)).*"))
      throw new ServiceException("STROOP.DIAGNOSIS_DATA_NOT_CORRECT");
    StroopTaskRecordPostForm form = new StroopTaskRecordPostForm();
    DiagnosisTask taskResult = getResultCountFromParam(diagnosisPeriod);
    form.setTaskCorrectCount(taskResult.getCorrectCount());
    form.setTaskErrorCount(taskResult.getErrorCount());
    form.setTaskId(stage);
    form.setTaskMisCount(taskResult.getMisCount());
    form.setTaskResultParam(diagnosisPeriod);
    form.setTaskStartTime(start);
    form.setUserId(userId);
    diagnosisTaskService.addIfNotExistsTaskRecord(form);
    if(stage == DiagnosisStageEnum.getMaxStage()){
      List<StroopTaskRecordEntity> taskEntityList = 
          diagnosisTaskService.selectByUserTime(userId, start);
      StroopDiagnosisRecordEntity entity = new StroopDiagnosisRecordEntity();
      entity.setDiagnosisCorrectInterference(0);
      entity.setDiagnosisDelay(0);
      entity.setDiagnosisInfo("");
      entity.setDiagnosisLevel(3);
      entity.setDiagnosisReactPeriod(0);
      entity.setDiagnosisResult("");
      entity.setDiagnosisScore(100);
      entity.setUserId(userId);
      //查询当前用户是否是被邀请的用户
      StroopInvitationEntity inviterInfo = invitationServce.queryUserInviter(userId);
      if(null != inviterInfo){
        entity.setInviteId(inviterInfo.getId());
        entity.setInviteUserId(inviterInfo.getInviterId());
      }else{
        entity.setInviteId(0l);
        entity.setInviteUserId(0l);
      }

      List<Long> ids = new ArrayList<>();
      for(StroopTaskRecordEntity taskEntity:taskEntityList){
        ids.add(taskEntity.getId());
      }
      diagnosisRecordMapper.insert(entity);
      diagnosisTaskService.updateStroopTaskById(ids, entity.getId());
      entity.setCreateDate(new Date());
      return getDiagnosisResult(entity, taskEntityList);
    }
    return null;
  }
  
  public DiagnosisResult queryDiagnosisDetail(
      @NotNull(message="STROOP.DIAGNOSIS_ID_IS_EMPTY")Long diagnosisId) {
    diagnosisId=1L;
    StroopDiagnosisRecordEntity entity = diagnosisRecordMapper.findOne(diagnosisId);
    if(null == entity)
      throw new ServiceException("STROOP_RECORD.RECORD_IS_EMPTY");
    List<StroopTaskRecordEntity> taskList = 
        diagnosisTaskService.queryTaskRecordByDiagnosisId(diagnosisId);
    return getDiagnosisResult(entity, taskList);
  }
  private DiagnosisResult getDiagnosisResult(
      StroopDiagnosisRecordEntity entity, List<StroopTaskRecordEntity> taskList){
    DiagnosisResult result = new DiagnosisResult();
    result.setDelayPeriod(entity.getDiagnosisDelay());
    result.setDiagnosisDate(DateTools.getFullDate(entity.getCreateDate()));
    result.setDiagnosisId(entity.getId());
    result.setDiagnosisLevel(entity.getDiagnosisLevel());
    result.setInterference(entity.getDiagnosisCorrectInterference());
    result.setReactPeriod(entity.getDiagnosisReactPeriod());
    result.setTaskList(new ArrayList<>());
    if(null != taskList && !taskList.isEmpty()){
      for(StroopTaskRecordEntity task:taskList){
        DiagnosisTask diagnosisTask = new DiagnosisTask();
        diagnosisTask.setCorrectCount(task.getTaskCorrectCount());
        diagnosisTask.setErrorCount(task.getTaskErrorCount());
        diagnosisTask.setMisCount(task.getTaskMisCount());
        diagnosisTask.setTaskName(
            DiagnosisStageEnum.getDiagnosisStage(task.getTaskId()).getDesc());
        result.getTaskList().add(diagnosisTask);
      }
    }
    return result;
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
  
  private DiagnosisTask getResultCountFromParam(String diagnosisPeriod){
    String[] periods = diagnosisPeriod.split(";");
    if(null == periods || periods.length ==0){
      throw new ServiceException("STROOP——.DIAGNOSIS_DATA_NOT_CORRECT");
    }
    DiagnosisTask task = new DiagnosisTask();
    for(String period: periods){
      String[] timeSet = period.split("-");
      if(null == timeSet || timeSet.length != 2){
        throw new ServiceException("STROOP.DIAGNOSIS_DATA_NOT_CORRECT");
      }
      int responseCode = Integer.parseInt(timeSet[1]);
      DiagnosisResponseState responseState = 
          DiagnosisResponseState.getDiagnosisResponseState(responseCode);
      if(null == responseState){
        throw new ServiceException("STROOP_RECORD.DIAGNOSIS_STAE_NOT_CORRECT");
      }
      if(responseCode == DiagnosisResponseState.NO_RESPONSE.getCode())
        task.setMisCount(task.getMisCount() + 1);
      if(responseCode == DiagnosisResponseState.RESPONSE_CORRECT.getCode())
        task.setCorrectCount(task.getCorrectCount() + 1);
      if(responseCode == DiagnosisResponseState.RESPONSE_WRONG.getCode())
        task.setErrorCount(task.getErrorCount() + 1);
    }
    return task;
  }
}
