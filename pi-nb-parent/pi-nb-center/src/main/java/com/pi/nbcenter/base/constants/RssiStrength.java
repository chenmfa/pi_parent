package com.pi.nbcenter.base.constants;

/**
 * @description 信号强度
 * @author chenmfa
 */
public enum RssiStrength {
  FULL(100),//正常
  ZERO(0);
  private int strength;
  
  private RssiStrength(int strength){
    this.strength = strength;
  }
  public int value(){
    return strength;
  }
}
