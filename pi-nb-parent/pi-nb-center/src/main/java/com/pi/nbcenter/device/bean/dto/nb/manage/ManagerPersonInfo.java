package com.pi.nbcenter.device.bean.dto.nb.manage;

import com.pi.nbcenter.device.bean.dto.nb.BaseInfo;

/**
 * @author lusj
 * @version V1.0
 * @Type ManagerPersonInfo
 * @Desc  管理人员信息
 * @date 2018/6/24
 */
public class ManagerPersonInfo extends BaseInfo{


    /**
     * 单位id（对接方）
     */
    private String manageCompanyId;
    /**
     * 公司名称
     */
    private String name;

    /**
     * 电话
     */
    private String phone;

    /**
     * 用户id（对接方)
     */
    private String userId;

    public String getManageCompanyId() {
        return manageCompanyId;
    }

    public void setManageCompanyId(String manageCompanyId) {
        this.manageCompanyId = manageCompanyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
