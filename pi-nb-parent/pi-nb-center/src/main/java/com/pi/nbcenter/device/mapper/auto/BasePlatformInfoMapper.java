package com.pi.nbcenter.device.mapper.auto;

import com.pi.nbcenter.device.entity.auto.BasePlatformInfo;
import com.pi.nbcenter.device.entity.auto.BasePlatformInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BasePlatformInfoMapper {
    int countByExample(BasePlatformInfoExample example);

    int deleteByExample(BasePlatformInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BasePlatformInfo record);

    int insertSelective(BasePlatformInfo record);

    List<BasePlatformInfo> selectByExample(BasePlatformInfoExample example);

    BasePlatformInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BasePlatformInfo record, @Param("example") BasePlatformInfoExample example);

    int updateByExample(@Param("record") BasePlatformInfo record, @Param("example") BasePlatformInfoExample example);

    int updateByPrimaryKeySelective(BasePlatformInfo record);

    int updateByPrimaryKey(BasePlatformInfo record);
}