package com.pi.base.util.lang;
/** 
* @author chenmfa
* @version 创建时间：2017年11月30日 下午2:47:52 
* @description
*/
public class LongUtil {
  /**
   * @description 将数字时间转换成4字节数组
   * @param num 数值
   * @return byte[] 字节数组
   */
  public static byte[] long2Bytes(long num) {
    byte[] by = new byte[8];
    for (int ix = 0; ix < by.length; ++ix){
    int offset = by.length * 8 - (ix + 1) * 8;
      by[ix] = (byte) ((num >> offset) & 0xff);
    }
    return by;
  }
  /**
   * @description 将字节组成long时间戳
   * @param data
   * @return long
   */
  public static long byte2Long(byte[] data){
    long num = 0;  
    for (int ix = 0, length = data.length; ix < length && ix < 8; ++ix) {
      num <<= 8;
      num |= (data[ix] & 0xff);  
    }  
    return num;
  }
  
  public static void main(String[] args) {
    long a= System.currentTimeMillis();
    System.out.println(a);
    long b = byte2Long(long2Bytes(a));
    System.out.println(b);
  }
}
