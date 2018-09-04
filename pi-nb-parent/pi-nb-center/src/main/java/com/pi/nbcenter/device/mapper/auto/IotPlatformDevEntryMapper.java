package com.pi.nbcenter.device.mapper.auto;

import com.pi.nbcenter.device.entity.auto.IotPlatformDevEntry;
import com.pi.nbcenter.device.entity.auto.IotPlatformDevEntryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IotPlatformDevEntryMapper {
    int countByExample(IotPlatformDevEntryExample example);

    int deleteByExample(IotPlatformDevEntryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(IotPlatformDevEntry record);

    int insertSelective(IotPlatformDevEntry record);

    List<IotPlatformDevEntry> selectByExample(IotPlatformDevEntryExample example);

    IotPlatformDevEntry selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") IotPlatformDevEntry record, @Param("example") IotPlatformDevEntryExample example);

    int updateByExample(@Param("record") IotPlatformDevEntry record, @Param("example") IotPlatformDevEntryExample example);

    int updateByPrimaryKeySelective(IotPlatformDevEntry record);

    int updateByPrimaryKey(IotPlatformDevEntry record);
}