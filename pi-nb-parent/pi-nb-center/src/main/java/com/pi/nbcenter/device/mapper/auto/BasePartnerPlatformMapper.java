package com.pi.nbcenter.device.mapper.auto;

import com.pi.nbcenter.device.entity.auto.BasePartnerPlatform;
import com.pi.nbcenter.device.entity.auto.BasePartnerPlatformExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BasePartnerPlatformMapper {
    int countByExample(BasePartnerPlatformExample example);

    int deleteByExample(BasePartnerPlatformExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BasePartnerPlatform record);

    int insertSelective(BasePartnerPlatform record);

    List<BasePartnerPlatform> selectByExample(BasePartnerPlatformExample example);

    BasePartnerPlatform selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BasePartnerPlatform record, @Param("example") BasePartnerPlatformExample example);

    int updateByExample(@Param("record") BasePartnerPlatform record, @Param("example") BasePartnerPlatformExample example);

    int updateByPrimaryKeySelective(BasePartnerPlatform record);

    int updateByPrimaryKey(BasePartnerPlatform record);
}