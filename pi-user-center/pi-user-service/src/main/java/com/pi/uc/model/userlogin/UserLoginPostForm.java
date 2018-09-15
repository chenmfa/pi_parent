package com.pi.uc.model.userlogin;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class UserLoginPostForm {

  @NotNull(message="UC_USER.SOURCE_ID_EMPTY") Long sourceId;
  @NotNull(message="UC_USER.WECHAT_CODE_EMPTY") 
  @Length(min=20, max=50, message="UC_USER.WECHAT_CODE_NOT_CORRECT") String wxCode;
  private String nickName;
  private String avatarUrl;
  private int gender;
  private String country;// 用户所在国家
  private String province;//用户所在省份
  private String city;//用户所在城市
  private String language;
  public Long getSourceId() {
    return sourceId;
  }
  public void setSourceId(Long sourceId) {
    this.sourceId = sourceId;
  }
  public String getWxCode() {
    return wxCode;
  }
  public void setWxCode(String wxCode) {
    this.wxCode = wxCode;
  }
  public String getNickName() {
    return nickName;
  }
  public void setNickName(String nickName) {
    this.nickName = nickName;
  }
  public String getAvatarUrl() {
    return avatarUrl;
  }
  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }
  public int getGender() {
    return gender;
  }
  public void setGender(int gender) {
    this.gender = gender;
  }
  public String getCountry() {
    return country;
  }
  public void setCountry(String country) {
    this.country = country;
  }
  public String getProvince() {
    return province;
  }
  public void setProvince(String province) {
    this.province = province;
  }
  public String getCity() {
    return city;
  }
  public void setCity(String city) {
    this.city = city;
  }
  public String getLanguage() {
    return language;
  }
  public void setLanguage(String language) {
    this.language = language;
  }
  @Override
  public String toString() {
    return "UserLoginPostForm [sourceId=" + sourceId + ", wxCode=" + wxCode + ", nickName=" + nickName + ", avatarUrl="
        + avatarUrl + ", gender=" + gender + ", country=" + country + ", province=" + province + ", city=" + city
        + ", language=" + language + "]";
  }
}
