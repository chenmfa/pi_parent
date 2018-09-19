package com.pi.stroop.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.pi.comm.mapper.BaseMapper;

import com.pi.stroop.dao.entity.StroopDiagnosisRecordEntity;

/**
 * 表stroop_diagnosis_record的mapper类
 *
 */
public interface StroopDiagnosisRecordMapper extends BaseMapper<StroopDiagnosisRecordEntity, Long>{
  @Select({"<script>",
    "SELECT * FROM `stroop_diagnosis_record` WHERE user_id = #{userId:BIGINT} ",
    "<if test='list != null and list.size()>0'>",
    "OR invite_id IN",
    " <foreach item='item' index='index' collection='list' ",
    "open='(' separator=',' close=')'>#{item}</foreach>",
    "</if>",
    "</script>"})
  public List<StroopDiagnosisRecordEntity> queryUserDiagnosisHistory(
      @Param("userId") Long userId, @Param("list") Iterable<Long> list);
}