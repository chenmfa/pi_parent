package com.dsmjd.productutil.utils;

public class WeChatQrUtil {
  
  /**
   * @description 根据mac地址和设备编号生成小程序的路径
   * @param lockMac 设备MAC
   * @param devCode 设备编号
   * @return
   */
  public static String generatePath(String lockMac, String devCode){
    StringBuilder sb = new StringBuilder("/pages/payment/index?deviceId=");
    sb.append(devCode.toUpperCase());
    sb.append("&deviceMac=");
    sb.append(lockMac.toUpperCase());
    return sb.toString();
  }
  
}
