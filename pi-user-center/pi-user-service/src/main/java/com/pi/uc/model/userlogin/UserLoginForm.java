package com.pi.uc.model.userlogin;

import java.util.Date;

public class UserLoginForm {
  private Date              loginDate;          // 登陆时间   
  private Integer           loginSource;        // 登陆的合作商来源 
  private String            loginIdentity;      // 登陆标志号 
  private Long              userId;             //  
  public Date getLoginDate() {
    return loginDate;
  }
  public void setLoginDate(Date loginDate) {
    this.loginDate = loginDate;
  }
  public Integer getLoginSource() {
    return loginSource;
  }
  public void setLoginSource(Integer loginSource) {
    this.loginSource = loginSource;
  }
  public String getLoginIdentity() {
    return loginIdentity;
  }
  public void setLoginIdentity(String loginIdentity) {
    this.loginIdentity = loginIdentity;
  }
  public Long getUserId() {
    return userId;
  }
  public void setUserId(Long userId) {
    this.userId = userId;
  }
  @Override
  public String toString() {
    return "UserLoginForm [loginDate=" + loginDate + ", loginSource=" + loginSource + ", loginIdentity=" + loginIdentity
        + ", userId=" + userId + "]";
  }
}
