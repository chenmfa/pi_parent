package com.pi.stroop.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.pi.comm.mapper.BaseMapper;

import com.pi.stroop.dao.entity.StroopDiagnosisHistoryEntity;

/**
 * 表stroop_diagnosis_history的mapper类
 *
 */
public interface StroopDiagnosisHistoryMapper extends BaseMapper<StroopDiagnosisHistoryEntity, Long>{

  @Select({"<script>",
      "SELECT * FROM `stroop_diagnosis_history` WHERE user_id = #{userId:BIGINT} ",
      "<if test='list != null and list.size()>0'>",
      "OR invite_id IN",
      " <foreach item='item' index='index' collection='list' ",
      "open='(' separator=',' close=')'>#{item}</foreach>",
      "</if>",
      "</script>"})
  public List<StroopDiagnosisHistoryEntity> queryUserDiagnosisHistory(
      @Param("userId") Long userId, @Param("list") Iterable<Long> list);
}