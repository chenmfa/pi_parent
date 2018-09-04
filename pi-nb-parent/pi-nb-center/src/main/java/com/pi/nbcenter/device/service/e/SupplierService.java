package com.pi.nbcenter.device.service.e;

import com.pi.base.util.http.HttpClientUtil;
import com.pi.nbcenter.device.bean.dto.nb.BaseInfo;
import com.pi.nbcenter.device.bean.dto.nb.SupportInfo;
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
 * @Type SupplierService
 * @Desc 供应商查询信息
 * @date 2018/6/24
 */
@Service
public class SupplierService {

    @Autowired
    private BaseEService baseEService;


    /**
     * 供应商接口
     *
     * @param supportInfo 供应商参数
     * @return
     */
    public BaseInfo addSupportCompany(SupportInfo supportInfo) {
        BaseInfo baseInfo = new BaseInfo();
        Map<String, Object> params = new HashMap<>();
        params.put("name", supportInfo.getName());
        params.put("address", supportInfo.getAddress());
        params.put("corporate", supportInfo.getCorporate());
        params.put("corporatePhone", supportInfo.getCorporatePhone());
        params.put("principal", supportInfo.getPrincipal());
        params.put("principalPhone", supportInfo.getPrincipalPhone());
        String url = "&code=addSupportCompany";

        String str = baseEService.commonPostMethod(url, params);
        baseInfo.setResult(str);
        return baseInfo;
    }


}
