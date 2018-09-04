package com.pi.nbcenter.device.service.e;

import com.pi.base.util.http.HttpClientUtil;
import com.pi.nbcenter.device.bean.dto.nb.BaseInfo;
import com.pi.nbcenter.device.bean.dto.nb.lockdevice.LockDeviceAlarmInfo;
import com.pi.nbcenter.device.bean.dto.nb.lockdevice.LockDeviceInfo;
import com.pi.nbcenter.device.bean.dto.nb.lockdevice.LockDeviceLogInfo;
import com.pi.nbcenter.device.bean.dto.nb.lockdevice.LockNotificationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lusj
 * @version V1.0
 * @Type LockDeviceService
 * @Desc 设备监测接口
 * @date 2018/6/24
 */
@Service
public class LockDeviceService {




    @Autowired
    private HttpClientUtil httpClientUtil;



    @Autowired
    private BaseEService baseEService;

    @Value("${wisdom.e.device.post.url}")
    private String  wisdomDevicePostUrl;

    /**
     * 添加设备基本信息
     *
     * @param lockDeviceInfo 2.4.1添加设备基本信息实体
     * @return
     */
    public BaseInfo  addLockDeviceInfo(LockDeviceInfo lockDeviceInfo) {
        BaseInfo baseInfo =new BaseInfo();
       Map<String, Object> params= new HashMap<>();
        params.put("imei",lockDeviceInfo.getImei());
        params.put("model", lockDeviceInfo.getModel());
        params.put("region",lockDeviceInfo.getRegion());
        params.put("installAddress", lockDeviceInfo.getInstallAddress());
        params.put("latitude", lockDeviceInfo.getLatitude());
        params.put("longitude", lockDeviceInfo.getLongitude());
        params.put("iccid", lockDeviceInfo.getIccid());
        params.put("supportCompanyId", lockDeviceInfo.getSupportCompanyId());
        params.put("manageCompanyId", lockDeviceInfo.getManageCompanyId());
        params.put("managerId", lockDeviceInfo.getManagerId());
        String url = "&code=addLockDeviceInfo";
        String str = baseEService.commonPostMethod(url, params);
        baseInfo.setResult(str);
        return baseInfo;
    }


    /**
     * 修改设备基本信息
     *
     * @param lockDeviceInfo 修改设备基本信息
     * @return
     */
    public BaseInfo  updateLockDeviceInfo(LockDeviceInfo lockDeviceInfo) {

        BaseInfo baseInfo =new BaseInfo();
        Map<String, Object> params= new HashMap<>();
        params.put("imei",lockDeviceInfo.getImei());
        params.put("model", lockDeviceInfo.getModel());
        params.put("region",lockDeviceInfo.getRegion());
        params.put("installAddress", lockDeviceInfo.getInstallAddress());
        params.put("latitude", lockDeviceInfo.getLatitude());
        params.put("longitude", lockDeviceInfo.getLongitude());
        params.put("iccid", lockDeviceInfo.getIccid());
        params.put("supportCompanyId", lockDeviceInfo.getSupportCompanyId());
        params.put("manageCompanyId", lockDeviceInfo.getManageCompanyId());
        params.put("managerId", lockDeviceInfo.getManagerId());
        String url = "&code=updateLockDeviceInfo";
        String str = baseEService.commonPostMethod(url, params);
        baseInfo.setResult(str);
        return baseInfo;
    }




    /**
     * 上报日志
     *
     * @param lockDeviceLogInfo 2.4.1添加设备基本信息实体
     * @return
     */
    public BaseInfo  addLockDeviceLog(LockDeviceLogInfo lockDeviceLogInfo) {


        BaseInfo baseInfo =new BaseInfo();
        Map<String, Object> params= new HashMap<>();
        params.put("imei",lockDeviceLogInfo.getImei());
        params.put("supportCompanyId", lockDeviceLogInfo.getSupportCompanyId());
        params.put("signalIntensity",lockDeviceLogInfo.getSignalIntensity());
        params.put("ecl", lockDeviceLogInfo.getEcl());
        params.put("snr", lockDeviceLogInfo.getSnr());
        params.put("location", lockDeviceLogInfo.getLocation());
        String url = "&code=addLockDeviceLog";
        String str = baseEService.commonPostMethod(url, params);
        baseInfo.setResult(str);
        return baseInfo;
    }



    /**
     * 批量添加设备上报日志
     *
     * @param list
     * @return
     */
    public BaseInfo  addLockDeviceLogs(List<LockDeviceLogInfo> list) {

        BaseInfo baseInfo =new BaseInfo();
        Map<String, Object> params= new HashMap<>();
    /*    for(LockDeviceLogInfo lockDeviceLogInfo : list){
            params.add("imei",lockDeviceLogInfo.getImei());
            params.add("supportCompanyId", lockDeviceLogInfo.getSupportCompanyId());
            params.add("signalIntensity",lockDeviceLogInfo.getSignalIntensity());
            params.add("ecl", lockDeviceLogInfo.getEcl());
            params.add("snr", lockDeviceLogInfo.getSnr());
            params.add("location", lockDeviceLogInfo.getLocation());
        }*/
        params.put("smokeLogList",list);

        String url =  "&code=addLockDeviceLogs";
        String str = baseEService.commonPostMethod(url, params);
        baseInfo.setResult(str);
        return baseInfo;
    }


    /**
     * 告警上报
     *
     * @param lockDeviceAlarmInfo
     * @return
     */
    public BaseInfo  addLockDeviceAlarm(LockDeviceAlarmInfo lockDeviceAlarmInfo) {


        BaseInfo baseInfo =new BaseInfo();
        Map<String, Object> params= new HashMap<>();
        params.put("imei",lockDeviceAlarmInfo.getImei());
        params.put("supportCompanyId", lockDeviceAlarmInfo.getSupportCompanyId());
        params.put("eventId",lockDeviceAlarmInfo.getEventId());
        params.put("type", lockDeviceAlarmInfo.getType());
        params.put("alarmName", lockDeviceAlarmInfo.getAlarmName());
        params.put("alarmTime", lockDeviceAlarmInfo.getAlarmTime());
        String url = "&code=addLockDeviceAlarm";
        String str = baseEService.commonPostMethod(url, params);
        baseInfo.setResult(str);
        return baseInfo;
    }


    /**
     * 告警处理
     *
     * @param lockDeviceAlarmInfo 2.4.1添加设备基本信息实体
     * @return
     */
    public BaseInfo  addLockDeviceAlarmDeal(LockDeviceAlarmInfo lockDeviceAlarmInfo) {
        BaseInfo baseInfo =new BaseInfo();
        Map<String, Object> params= new HashMap<>();
        params.put("imei",lockDeviceAlarmInfo.getImei());
        params.put("supportCompanyId", lockDeviceAlarmInfo.getSupportCompanyId());
        params.put("eventId",lockDeviceAlarmInfo.getEventId());
        params.put("alarmState", lockDeviceAlarmInfo.getAlarmState());
        params.put("confirmType", lockDeviceAlarmInfo.getConfirmType());
        params.put("result", lockDeviceAlarmInfo.getResult());
        String url = "&code=addLockDeviceAlarmDeal";
        String str = baseEService.commonPostMethod(url, params);
        baseInfo.setResult(str);
        return baseInfo;
    }



    /**
     * 上报通知
     *
     * @param lockNotificationInfo
     * @return
     */
    public BaseInfo  addLockNotification(LockNotificationInfo lockNotificationInfo) {
        BaseInfo baseInfo =new BaseInfo();
        Map<String, Object> params= new HashMap<>();
        params.put("imei",lockNotificationInfo.getImei());
        params.put("supportCompanyId", lockNotificationInfo.getSupportCompanyId());
        params.put("eventId",lockNotificationInfo.getEventId());
        params.put("notificationType", lockNotificationInfo.getNotificationType());
        params.put("phone", lockNotificationInfo.getPhone());
        params.put("userName", lockNotificationInfo.getUserName());
        params.put("content", lockNotificationInfo.getContent());
        params.put("sendState", lockNotificationInfo.getSendState());
        params.put("sendTime", lockNotificationInfo.getSendState());
        params.put("failedReason", lockNotificationInfo.getFailedReason());
        String url = "&code=addLockDeviceAlarmDeal";
        String str = baseEService.commonPostMethod(url, params);
        baseInfo.setResult(str);
        return baseInfo;

    }
}
