package com.pi.stroop.dao.entity;

import com.pi.comm.entity.BaseEntity;

/**
 * @description 表stroop_invitation的实体类
 * @author chenmfa
 * @date 2018-09-07 04:26:37
 */
public class StroopInvitationEntity extends BaseEntity{
	private static final long serialVersionUID = -4972683369271453960L;
	private Long              userId;             // 用户编号 
	private Long              inviterId;          // 邀请者编号 
	private Integer           userPv;             // 用户通过邀请进入页面的次数 
	private Integer           inviteState;        // 状态 

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

	public Integer getInviteState() {
		return this.inviteState;
	}

	public void setInviteState(Integer inviteState) {
		this.inviteState=inviteState;
	}

}