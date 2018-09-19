package com.pi.stroop.service;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.pi.base.exception.ServiceException;
import com.pi.base.util.bean.ObjectTool;
import com.pi.stroop.dao.entity.StroopTaskRecordEntity;
import com.pi.stroop.dao.mapper.StroopTaskRecordMapper;
import com.pi.stroop.dao.param.StroopTaskRecordParam;
import com.pi.stroop.model.dto.StroopTaskRecordPostForm;

@Validated
@Service
public class StroopDiagnosisTaskService {
  
  @Autowired
  private StroopTaskRecordMapper taskRecordMapper;
  
  public List<StroopTaskRecordEntity> queryTaskRecordByDiagnosisId(
      @NotNull(message="STROOP_TASK.DIAGNOSIS_ID_IS_EMPTY") Long diagnosisId){
    StroopTaskRecordParam param = new StroopTaskRecordParam();
    param.setDiagnosisId(diagnosisId);
    List<StroopTaskRecordEntity> list = taskRecordMapper.findList(param);
    if(null == list || list.isEmpty()){
      throw new ServiceException("STROOP_TASK.TASK_IS_EMPTY");
    }
    return list;
  }
  
  public Long addIfNotExistsTaskRecord(@Valid StroopTaskRecordPostForm record){
    StroopTaskRecordEntity entity = 
        selectByUserTime(record.getUserId(), record.getTaskStartTime(), record.getTaskId());
    if(null != entity){
      throw new ServiceException("STROOP_TASK.TASK_UPLOAD_REPEATABLE_NOT_SUPPORTED");
    }
    entity = new StroopTaskRecordEntity();
    entity.setTaskCorrectCount(record.getTaskCorrectCount());
    entity.setTaskErrorCount(record.getTaskErrorCount());
    entity.setTaskId(record.getTaskId());
    entity.setTaskMisCount(record.getTaskMisCount());
    entity.setTaskResultParam(record.getTaskResultParam());
    entity.setTaskStartTime(new Date((record.getTaskStartTime()/1000)*1000));
    entity.setDiagnosisId(0l);
    entity.setTaskUserId(record.getUserId());
    taskRecordMapper.insert(entity);
    return entity.getId();
  }
  
  public void updateStroopTaskById(List<Long> ids, Long diagnosisId){
    if(null != ids && ids.size() >0){
      for(Long id:ids){
        StroopTaskRecordEntity entity = new StroopTaskRecordEntity();
        entity.setId(id);
        entity.setDiagnosisId(diagnosisId);
        taskRecordMapper.updateById(entity); 
      }
    }
  }

  public List<StroopTaskRecordEntity> selectByUserTime(
      @NotNull(message="STROOP_TASK.TASK_USER_ID_IS_EMPTY")Long userId,
      @NotNull(message="STROOP_TASK.TASK_START_TIME_IS_EMPTY")Long startTime) {
    StroopTaskRecordParam param = new StroopTaskRecordParam();
    param.setTaskUserId(userId);
    param.setTaskStartTime(new Date((startTime/1000)*1000));
    List<StroopTaskRecordEntity> list = taskRecordMapper.findList(param);
    if(null == list || list.isEmpty()){
      throw new ServiceException("STROOP_TASK.TASK_IS_EMPTY");
    }
    return list;
  }
  public StroopTaskRecordEntity selectByUserTime(
      @NotNull(message="STROOP_TASK.TASK_USER_ID_IS_EMPTY")Long userId,
      @NotNull(message="STROOP_TASK.TASK_START_TIME_IS_EMPTY")Long startTime,
      @NotNull(message="STROOP_TASK.TASK_ID_IS_EMPTY")Integer taskId) {
    StroopTaskRecordParam param = new StroopTaskRecordParam();
    param.setTaskUserId(userId);
    param.setTaskStartTime(new Date((startTime/1000)*1000));
    param.setTaskId(taskId);
    List<StroopTaskRecordEntity> list = taskRecordMapper.findList(param);
    return ObjectTool.getOne(list);
  }
}
