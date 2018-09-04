package com.pi.nbcenter.device.controller.e;

import com.pi.base.dto.result.AppResult;
import com.pi.nbcenter.device.bean.dto.nb.BaseInfo;
import com.pi.nbcenter.device.bean.dto.nb.SupportInfo;
import com.pi.nbcenter.device.bean.dto.nb.lockdevice.LockDeviceAlarmInfo;
import com.pi.nbcenter.device.bean.dto.nb.lockdevice.LockDeviceInfo;
import com.pi.nbcenter.device.bean.dto.nb.lockdevice.LockDeviceLogInfo;
import com.pi.nbcenter.device.bean.dto.nb.lockdevice.LockNotificationInfo;
import com.pi.nbcenter.device.bean.dto.nb.manage.ManageCompanyInfo;
import com.pi.nbcenter.device.bean.dto.nb.manage.ManagerPersonInfo;
import com.pi.nbcenter.device.service.e.BaseEService;
import com.pi.nbcenter.device.service.e.LockDeviceService;
import com.pi.nbcenter.device.service.e.ManageService;
import com.pi.nbcenter.device.service.e.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lusj
 * @version V1.0
 * @Type LockDeviceController
 * @Desc
 * @date 2018/6/25
 */
@RequestMapping("/device")
@RestController
public class LockDeviceController {

    @Autowired
    private BaseEService baseEService;

    @Autowired
    private ManageService manageService;


    @Autowired
    private LockDeviceService lockDeviceService;

    @Autowired
    private SupplierService supplierService;


    /**
     * @description 获取token
     * @return
     * @throws Exception
     */
    @GetMapping("/getAccessToken")
    @ResponseBody
    public AppResult getAccessToken() {
        String token =  baseEService.getAccessToken();
       return AppResult.newSuccessResult(token);
    }



    /**
     * 供应商接口
     * @param supportInfo
     */
    @PostMapping("/addSupportCompany")
    @ResponseBody
    public AppResult addSupportCompany(@RequestBody SupportInfo supportInfo) {
        BaseInfo  managerPersonInfo= supplierService.addSupportCompany(supportInfo);
        System.out.println(managerPersonInfo);
        return AppResult.newSuccessResult(managerPersonInfo);
    }


    /**
     * 添加设备信息
     * @param manageCompanyInfo
     */
    @PostMapping("/addManageCompany")
    @ResponseBody
    public AppResult addManageCompany(@RequestBody ManageCompanyInfo manageCompanyInfo) {
        BaseInfo managerId = manageService.addManageCompany(manageCompanyInfo);
        System.out.println(managerId);
        return AppResult.newSuccessResult(managerId);
    }

    /**
     * 添加管理人员
     * @param managerPersonInfo
     */
    @PostMapping("/addManager")
    @ResponseBody
    public AppResult addManager(@RequestBody ManagerPersonInfo managerPersonInfo) {
        BaseInfo managerId = manageService.addManager(managerPersonInfo);
        System.out.println(managerId);
        return AppResult.newSuccessResult(managerId);
    }



    /**
     * 获取管理人员列表
     * @param manageCompanyId
     */
    @GetMapping("/getManagerList/{manageCompanyId}")
    @ResponseBody
    public AppResult getManagerList(String manageCompanyId) {
         ManagerPersonInfo  managerPersonInfo= manageService.getManagerList(manageCompanyId);
        System.out.println(managerPersonInfo);
        return AppResult.newSuccessResult(managerPersonInfo);
    }



    /**
     * 添加设备基本信息
     * @param lockDeviceInfo
     */
    @PostMapping("/addLockDeviceInfo")
    @ResponseBody
    public AppResult addLockDeviceInfo(@RequestBody LockDeviceInfo lockDeviceInfo) {
        BaseInfo str = lockDeviceService.addLockDeviceInfo(lockDeviceInfo);
        System.out.println(str);
        return AppResult.newSuccessResult(str);
    }



    /**
     * 更新设备基本信息
     * @param lockDeviceInfo
     */
    @PostMapping("/updateLockDeviceInfo")
    @ResponseBody
    public AppResult updateLockDeviceInfo(@RequestBody LockDeviceInfo lockDeviceInfo) {
        BaseInfo  str= lockDeviceService.updateLockDeviceInfo(lockDeviceInfo);
        System.out.println(str);
        return AppResult.newSuccessResult(str);
    }



    /**
     * 上报日志
     * @param lockDeviceLogInfo
     */
    @PostMapping("/addLockDeviceLog")
    @ResponseBody
    public AppResult addLockDeviceLog(@RequestBody LockDeviceLogInfo lockDeviceLogInfo) {
        BaseInfo  str= lockDeviceService.addLockDeviceLog(lockDeviceLogInfo);
        System.out.println(str);
        return AppResult.newSuccessResult(str);
    }



    /**
     * 批量添加设备上报日志
     * @param list
     */
    @PostMapping("/addLockDeviceLogs")
    @ResponseBody
    public AppResult addLockDeviceLogs(@RequestBody List<LockDeviceLogInfo> list) {
        BaseInfo  str= lockDeviceService.addLockDeviceLogs(list);
        System.out.println(str);
        return AppResult.newSuccessResult(str);
    }



    /**
     *  告警上报
     * @param lockDeviceAlarmInfo
     */
    @PostMapping("/addLockDeviceAlarm")
    @ResponseBody
    public AppResult addLockDeviceAlarm(@RequestBody LockDeviceAlarmInfo lockDeviceAlarmInfo) {
        BaseInfo  str= lockDeviceService.addLockDeviceAlarm(lockDeviceAlarmInfo);
        System.out.println(str);
        return AppResult.newSuccessResult(str);
    }






    /**
     * 告警处理
     * @param lockDeviceAlarmInfo
     */
    @PostMapping("/addLockDeviceAlarmDeal")
    @ResponseBody
    public AppResult addLockDeviceAlarmDeal(@RequestBody LockDeviceAlarmInfo lockDeviceAlarmInfo) {
        BaseInfo  str= lockDeviceService.addLockDeviceAlarmDeal(lockDeviceAlarmInfo);
        System.out.println(str);
        return AppResult.newSuccessResult(str);
    }




    /**
     * 告警处理
     * @param lockNotificationInfo
     */
    @PostMapping("/addLockNotification")
    @ResponseBody
    public AppResult addLockNotification(@RequestBody LockNotificationInfo lockNotificationInfo) {
        BaseInfo  str= lockDeviceService.addLockNotification(lockNotificationInfo);
        System.out.println(str);
        return AppResult.newSuccessResult(str);
    }


}
