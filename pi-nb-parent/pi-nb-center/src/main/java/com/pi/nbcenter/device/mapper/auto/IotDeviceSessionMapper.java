package com.pi.nbcenter.device.mapper.auto;

import com.pi.nbcenter.device.entity.auto.IotDeviceSession;
import com.pi.nbcenter.device.entity.auto.IotDeviceSessionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IotDeviceSessionMapper {
    long countByExample(IotDeviceSessionExample example);

    int deleteByExample(IotDeviceSessionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(IotDeviceSession record);

    int insertSelective(IotDeviceSession record);

    List<IotDeviceSession> selectByExampleWithBLOBs(IotDeviceSessionExample example);

    List<IotDeviceSession> selectByExample(IotDeviceSessionExample example);

    IotDeviceSession selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") IotDeviceSession record, @Param("example") IotDeviceSessionExample example);

    int updateByExampleWithBLOBs(@Param("record") IotDeviceSession record, @Param("example") IotDeviceSessionExample example);

    int updateByExample(@Param("record") IotDeviceSession record, @Param("example") IotDeviceSessionExample example);

    int updateByPrimaryKeySelective(IotDeviceSession record);

    int updateByPrimaryKeyWithBLOBs(IotDeviceSession record);

    int updateByPrimaryKey(IotDeviceSession record);
}