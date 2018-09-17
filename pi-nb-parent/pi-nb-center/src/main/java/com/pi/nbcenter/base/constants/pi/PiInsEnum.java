package com.pi.nbcenter.base.constants.pi;

/**
 * @description 透传指令枚举
 * @author chenmfa
 */
public enum PiInsEnum {
  LOKC_RESET((byte)0x04, "清空所有用户信息"),
  ICCARD_ADD((byte)0x04, "IC卡添加"),
  ICCARD_DEL((byte)0x05, "IC卡删除"),
  FINGER_ADD_RISE((byte)0x06, "指纹录入发起"),
  FINGER_DEL((byte)0x07, "指纹删除"),
  PWD_ADD((byte)0x08, "密码添加"),
  PWD_DEL((byte)0x09, "密码删除"),
  TEMP_PWD((byte)0x0A, "临时钥匙添加"),
  ;
  private PiInsEnum(byte ins, String desc) {
    this.ins = ins;
    this.desc = desc;
  }
  private byte ins;
  private String desc;
  public byte getIns() {
    return ins;
  }
  public String getDesc() {
    return desc;
  }
}
