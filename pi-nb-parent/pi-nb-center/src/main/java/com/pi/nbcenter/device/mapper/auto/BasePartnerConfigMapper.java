package com.pi.nbcenter.device.mapper.auto;

import com.pi.nbcenter.device.entity.auto.BasePartnerConfig;
import com.pi.nbcenter.device.entity.auto.BasePartnerConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BasePartnerConfigMapper {
    int countByExample(BasePartnerConfigExample example);

    int deleteByExample(BasePartnerConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BasePartnerConfig record);

    int insertSelective(BasePartnerConfig record);

    List<BasePartnerConfig> selectByExample(BasePartnerConfigExample example);

    BasePartnerConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BasePartnerConfig record, @Param("example") BasePartnerConfigExample example);

    int updateByExample(@Param("record") BasePartnerConfig record, @Param("example") BasePartnerConfigExample example);

    int updateByPrimaryKeySelective(BasePartnerConfig record);

    int updateByPrimaryKey(BasePartnerConfig record);
}