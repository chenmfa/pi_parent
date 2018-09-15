package com.pi.nbcenter.device.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pi.nbcenter.device.service.pi.internal.PiIotDevService;

@Component
public class IotSessionSyncronizeJob {
  
  private static final Logger logger = LoggerFactory.getLogger(IotSessionSyncronizeJob.class);
  @Autowired
  PiIotDevService iotDevService;
  
  @Scheduled(cron = "0 */2 * * * ?")
  public void sessionSyncronize(){
    try {
      iotDevService.sessionSyncronize();
    } catch (Exception e) {
      logger.error("同步设备状态失败",e);
    }
  }
}
