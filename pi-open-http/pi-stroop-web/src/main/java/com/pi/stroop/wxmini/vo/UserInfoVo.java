package com.pi.stroop.wxmini.vo;

public class UserInfoVo {
  private String avatar;       //头像地址
  private int age;             //年龄
  private String name;         //姓名
  private int sex;             //性别 1. 男性 2.女性
  private long userId;         //用户编号
  private String mobile;       //手机号
  private String education;    //教育程度
  public String getAvatar() {
    return avatar;
  }
  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }
  public int getAge() {
    return age;
  }
  public void setAge(int age) {
    this.age = age;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public int getSex() {
    return sex;
  }
  public void setSex(int sex) {
    this.sex = sex;
  }
  public long getUserId() {
    return userId;
  }
  public void setUserId(long userId) {
    this.userId = userId;
  }
  public String getMobile() {
    return mobile;
  }
  public void setMobile(String mobile) {
    this.mobile = mobile;
  }
  public String getEducation() {
    return education;
  }
  public void setEducation(String education) {
    this.education = education;
  }
}
