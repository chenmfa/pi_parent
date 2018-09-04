package com.pi.nbcenter.device.bean.dto.nb.manage;

/**
 * @author lusj
 * @version V1.0
 * @Type ManageCompanyInfo
 * @Desc  管理单位实体类
 * @date 2018/6/24
 */
public class ManageCompanyInfo {

    /**
     * 公司名称
     */
    private String name;

    /**
     * 上级单位id（对接方）
     */
    private String parentId;

    /**
     * 公司地址
     */
    private String address;

    /**
     * 法人
     */
    private String corporate;

    /**
     * 法人电话
     */
    private String corporatePhone;

    /**
     * 负责人姓名
     */
    private String principal;

    /**
     * 负责人电话
     */
    private String principalPhone;


    /**
     * 单位id（对接方）
     */
    private String manageCompanyId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCorporate() {
        return corporate;
    }

    public void setCorporate(String corporate) {
        this.corporate = corporate;
    }

    public String getCorporatePhone() {
        return corporatePhone;
    }

    public void setCorporatePhone(String corporatePhone) {
        this.corporatePhone = corporatePhone;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getPrincipalPhone() {
        return principalPhone;
    }

    public void setPrincipalPhone(String principalPhone) {
        this.principalPhone = principalPhone;
    }

    public String getManageCompanyId() {
        return manageCompanyId;
    }

    public void setManageCompanyId(String manageCompanyId) {
        this.manageCompanyId = manageCompanyId;
    }
}
