package com.pi.nbcenter.device.service.e;

import com.pi.base.util.http.HttpClientUtil;
import com.pi.nbcenter.device.bean.dto.nb.BaseInfo;
import com.pi.nbcenter.device.bean.dto.nb.manage.ManageCompanyInfo;
import com.pi.nbcenter.device.bean.dto.nb.manage.ManagerPersonInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lusj
 * @version V1.0
 * @Type ManageService
 * @Desc 管理单位接口
 * @date 2018/6/24
 */
@Service
public class ManageService {


    @Autowired
    private BaseEService baseEService;




    @Value("${wisdom.e.device.post.url}")
    private String  wisdomDevicePostUrl;

    @Autowired
    private HttpClientUtil httpClientUtil;




    /**
     * 单位添加接口
     *
     * @param manageCompanyInfo 单位信息相关
     * @return
     */
    public BaseInfo addManageCompany(ManageCompanyInfo manageCompanyInfo) {
        BaseInfo baseInfo = new BaseInfo();
        Map<String, Object> params = new HashMap<>();
        params.put("name", manageCompanyInfo.getName());
        params.put("parentId", manageCompanyInfo.getParentId());
        params.put("address", manageCompanyInfo.getAddress());
        params.put("corporate", manageCompanyInfo.getCorporate());
        params.put("corporatePhone", manageCompanyInfo.getCorporatePhone());
        params.put("principal", manageCompanyInfo.getPrincipal());
        params.put("principalPhone", manageCompanyInfo.getPrincipalPhone());
        params.put("manageCompanyId", manageCompanyInfo.getManageCompanyId());
        String url = "&code=addManageCompany";
        String str = baseEService.commonPostMethod(url, params);
        baseInfo.setResult(str);
        return baseInfo;

    }



    /**
     * 添加管理人员
     *
     * @param managerPersonInfo 人员信息
     * @return
     */
    public BaseInfo addManager(ManagerPersonInfo managerPersonInfo) {
        BaseInfo baseInfo = new BaseInfo();
        Map<String, Object> params = new HashMap<>();
        params.put("manageCompanyId", managerPersonInfo.getManageCompanyId());
        params.put("name", managerPersonInfo.getName());
        params.put("phone", managerPersonInfo.getPhone());
        params.put("userId", managerPersonInfo.getUserId());
        String url =  "&code=addManager";
        String str = baseEService.commonPostMethod(url, params);
        baseInfo.setResult(str);
        return baseInfo;

    }


    /**
     * 获取管理人员列表
     *
     * @param manageCompanyId 管理单位id（对接方）
     * @return
     */
    public ManagerPersonInfo getManagerList(String manageCompanyId) {
        ManagerPersonInfo baseInfo = new ManagerPersonInfo();
        String accessToken = baseEService.getAccessToken();
        String url = wisdomDevicePostUrl + accessToken + "&code=getManagerList&"+manageCompanyId;
        String str = baseEService.commonGetMethod(url);
        baseInfo.setResult(str);
        return baseInfo;

    }



}
