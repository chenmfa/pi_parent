package com.pi.nbcenter.device.mapper.auto;

import com.pi.nbcenter.device.entity.auto.IotDeviceInfo;
import com.pi.nbcenter.device.entity.auto.IotDeviceInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IotDeviceInfoMapper {
    long countByExample(IotDeviceInfoExample example);

    int deleteByExample(IotDeviceInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(IotDeviceInfo record);

    int insertSelective(IotDeviceInfo record);

    List<IotDeviceInfo> selectByExample(IotDeviceInfoExample example);

    IotDeviceInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") IotDeviceInfo record, @Param("example") IotDeviceInfoExample example);

    int updateByExample(@Param("record") IotDeviceInfo record, @Param("example") IotDeviceInfoExample example);

    int updateByPrimaryKeySelective(IotDeviceInfo record);

    int updateByPrimaryKey(IotDeviceInfo record);
}