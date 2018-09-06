package com.pi.uc.model.user;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class UserPostForm {
  @NotNull(message="UC_USER.USER_ID_EMPTY") 
  long loginUserId;
  @NotNull(message="UC_USER.SEX_IS_EMPTY")
  private Integer sex;  //性别 1. 男性 2.女性
  @NotNull(message="UC_USER.MOBILE_IS_EMPTY")
  @Length(min=11,max=11, message="UC_USER.MOBILE_LENGTH_ERROR")
  private String mobile;  //手机号
  @NotNull(message="UC_USER.AGE_IS_EMPTY")
  private Integer age;  //年龄
  @NotNull(message="UC_USER.NAME_IS_EMPTY")
  @Length(max=20, message="UC_USER.NAME_LENGTH_ERROR")
  private String name; //姓名
  @NotNull(message="UC_USER.EDU_IS_EMPTY")
  @Length(max=10, message="UC_USER.EDU_LENGTH_ERROR")
  private String education;  //教育程度
  public long getLoginUserId() {
    return loginUserId;
  }
  public void setLoginUserId(long loginUserId) {
    this.loginUserId = loginUserId;
  }
  public void setSex(Integer sex) {
    this.sex = sex;
  }
  public void setAge(Integer age) {
    this.age = age;
  }
  public int getSex() {
    return sex;
  }
  public void setSex(int sex) {
    this.sex = sex;
  }
  public String getMobile() {
    return mobile;
  }
  public void setMobile(String mobile) {
    this.mobile = mobile;
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
  public String getEducation() {
    return education;
  }
  public void setEducation(String education) {
    this.education = education;
  }
}
