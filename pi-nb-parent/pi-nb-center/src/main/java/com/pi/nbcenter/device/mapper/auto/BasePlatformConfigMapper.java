package com.pi.nbcenter.device.mapper.auto;

import com.pi.nbcenter.device.entity.auto.BasePlatformConfig;
import com.pi.nbcenter.device.entity.auto.BasePlatformConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BasePlatformConfigMapper {
    int countByExample(BasePlatformConfigExample example);

    int deleteByExample(BasePlatformConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BasePlatformConfig record);

    int insertSelective(BasePlatformConfig record);

    List<BasePlatformConfig> selectByExample(BasePlatformConfigExample example);

    BasePlatformConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BasePlatformConfig record, @Param("example") BasePlatformConfigExample example);

    int updateByExample(@Param("record") BasePlatformConfig record, @Param("example") BasePlatformConfigExample example);

    int updateByPrimaryKeySelective(BasePlatformConfig record);

    int updateByPrimaryKey(BasePlatformConfig record);
}