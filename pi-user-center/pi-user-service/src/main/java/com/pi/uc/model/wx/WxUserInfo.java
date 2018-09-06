package com.pi.uc.model.wx;

public class WxUserInfo {

  private String unionid;
  private String openid;//
  private String nickname;//
  private Integer sex;//
  private String language;
  private String province;
  private String country;
  private String headimgurl;
  public String getUnionid() {
    return unionid;
  }
  public void setUnionid(String unionid) {
    this.unionid = unionid;
  }
  public String getOpenid() {
    return openid;
  }
  public void setOpenid(String openid) {
    this.openid = openid;
  }
  public String getNickname() {
    return nickname;
  }
  public void setNickname(String nickname) {
    this.nickname = nickname;
  }
  public Integer getSex() {
    return sex;
  }
  public void setSex(Integer sex) {
    this.sex = sex;
  }
  public String getLanguage() {
    return language;
  }
  public void setLanguage(String language) {
    this.language = language;
  }
  public String getProvince() {
    return province;
  }
  public void setProvince(String province) {
    this.province = province;
  }
  public String getCountry() {
    return country;
  }
  public void setCountry(String country) {
    this.country = country;
  }
  public String getHeadimgurl() {
    return headimgurl;
  }
  public void setHeadimgurl(String headimgurl) {
    this.headimgurl = headimgurl;
  }
}

