package com.pi.nbcenter.device.bean.dto.session;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Customer {
  private String name;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date birth;
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public Date getBirth() {
    return birth;
  }
  public void setBirth(Date birth) {
    this.birth = birth;
  }
}
