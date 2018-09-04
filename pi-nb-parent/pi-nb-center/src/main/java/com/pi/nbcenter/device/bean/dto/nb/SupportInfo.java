package com.pi.nbcenter.device.bean.dto.nb;

/**
 * @author lusj
 * @version V1.0
 * @Type SupportInfo
 * @Desc 供应商相关实体
 * @date 2018/6/24
 */
public class SupportInfo  extends BaseInfo{

    /**
     * 公司名称
     */
     private String name;

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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
