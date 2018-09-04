package com.pi.nbcenter.base.constants;

public enum BatteryStatus {
  FULL(100),//充满
  NONE(0);//未知(初始化状态)
  private int capacity;
  
  private BatteryStatus(int capacity){
    this.capacity = capacity;
  }
  public int value(){
    return capacity;
  }
}
