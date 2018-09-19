package com.pi.stroop.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.pi.comm.mapper.BaseMapper;

import com.pi.stroop.dao.entity.StroopTaskRecordEntity;

/**
 * 表stroop_task_record的mapper类
 *
 */
public interface StroopTaskRecordMapper extends BaseMapper<StroopTaskRecordEntity, Long>{

  @Update({"<script>",
    "UPDATE stroop_task_record` SET VERSION = VERSION + 1, diagnosis_id= #{diagnosisId} WHERE id in ",
    "<if test='list != null and list.size()>0'>",
    " <foreach item='item' index='index' collection='list' ",
    "open='(' separator=',' close=')'>#{item}</foreach>",
    "</if>",
    "</script>"})
  public void updateTaskDiagnosiIdByIds(
      @Param("list")List<Long> ids, @Param("diagnosisId") Long diagnosisId);
}