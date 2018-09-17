package com.pi.base.util.lang;

public class ShortUtil {
  public static byte[] shortToByte(short number) {
    int temp = number; 
    byte[] b = new byte[2]; 
    for (int i = 0; i < b.length; i++) { 
      b[i] = new Integer(temp & 0xff).byteValue();// 
      temp = temp >> 8; // 向右移8位 
    } 
    return b; 
  }
}
