package com.pi.uc.dao.param;

import com.pi.comm.query.MapperQuery;

/**
 * @description 表user的实体类
 * @author chenmfa
 * @date 2018-09-07 02:38:20
 */
public class UserParam extends MapperQuery{
	private static final long serialVersionUID = -4972683369271453960L;
	private String            mobile;             // 手机号 
	private String            password;           // 密码(默认为空--后期按照需求定) 
	private String            nickName;           // 昵称 
	private String            avatar;             // 头像 
	private String            name;               // 姓名 
	private Integer           age;                // 年龄 
	private Integer           sex;                // 1.男 2. 女 
	private String            wxOpenid;           // 微信openid 
	private String            wxUnionid;          // 微信unionid 
	private Long              sourceId;           // 用户来源 
	private String            education;          // 教育程度 
	private Integer           state;              // 10 正常 20 删除/注销 

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile=mobile;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password=password;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName=nickName;
	}

	public String getAvatar() {
		return this.avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar=avatar;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name=name;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age=age;
	}

	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex=sex;
	}

	public String getWxOpenid() {
		return this.wxOpenid;
	}

	public void setWxOpenid(String wxOpenid) {
		this.wxOpenid=wxOpenid;
	}

	public String getWxUnionid() {
		return this.wxUnionid;
	}

	public void setWxUnionid(String wxUnionid) {
		this.wxUnionid=wxUnionid;
	}

	public Long getSourceId() {
		return this.sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId=sourceId;
	}

	public String getEducation() {
		return this.education;
	}

	public void setEducation(String education) {
		this.education=education;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state=state;
	}

}