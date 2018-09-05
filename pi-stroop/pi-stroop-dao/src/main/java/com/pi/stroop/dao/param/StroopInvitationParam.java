package com.pi.stroop.dao.param;

import com.pi.comm.query.MapperQuery;

/**
 * @description 表stroop_invitation的实体类
 * @author chenmfa
 * @date 2018-09-05 11:04:59
 */
public class StroopInvitationParam extends MapperQuery{
	private static final long serialVersionUID = -4972683369271453960L;
	private Long              userId;             // 用户编号 
	private Long              inviterId;          // 邀请者编号 
	private Integer           userPv;             // 用户通过邀请进入页面的次数 

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId=userId;
	}

	public Long getInviterId() {
		return this.inviterId;
	}

	public void setInviterId(Long inviterId) {
		this.inviterId=inviterId;
	}

	public Integer getUserPv() {
		return this.userPv;
	}

	public void setUserPv(Integer userPv) {
		this.userPv=userPv;
	}

}