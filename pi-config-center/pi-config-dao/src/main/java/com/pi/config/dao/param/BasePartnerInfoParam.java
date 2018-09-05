package com.pi.config.dao.param;

import com.pi.comm.query.MapperQuery;

/**
 * @description 表base_partner_info的实体类
 * @author chenmfa
 * @date 2018-09-05 19:45:55
 */
public class BasePartnerInfoParam extends MapperQuery{
	private static final long serialVersionUID = -4972683369271453960L;
	private String            partnerName;        // 厂商名称 
	private String            partnerAlias;       // 合作商别名 

	public String getPartnerName() {
		return this.partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName=partnerName;
	}

	public String getPartnerAlias() {
		return this.partnerAlias;
	}

	public void setPartnerAlias(String partnerAlias) {
		this.partnerAlias=partnerAlias;
	}

}