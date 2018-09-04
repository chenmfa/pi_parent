package com.pi.nbcenter.device.mapper.auto;

import com.pi.nbcenter.device.entity.auto.IotDeviceNotificationLog;
import com.pi.nbcenter.device.entity.auto.IotDeviceNotificationLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IotDeviceNotificationLogMapper {
    long countByExample(IotDeviceNotificationLogExample example);

    int deleteByExample(IotDeviceNotificationLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(IotDeviceNotificationLog record);

    int insertSelective(IotDeviceNotificationLog record);

    List<IotDeviceNotificationLog> selectByExample(IotDeviceNotificationLogExample example);

    IotDeviceNotificationLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") IotDeviceNotificationLog record, @Param("example") IotDeviceNotificationLogExample example);

    int updateByExample(@Param("record") IotDeviceNotificationLog record, @Param("example") IotDeviceNotificationLogExample example);

    int updateByPrimaryKeySelective(IotDeviceNotificationLog record);

    int updateByPrimaryKey(IotDeviceNotificationLog record);
}