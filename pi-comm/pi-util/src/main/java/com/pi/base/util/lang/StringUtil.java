package com.pi.base.util.lang;
/** 
* @author chenmfa
* @version 创建时间：2017年5月27日 上午9:47:27 
* @description 字符串通用类
*/
public class StringUtil {
  /**
   * @description 生成固定长度的随机字符串
   * @return nonce
   */
  public static String generateNonce(){
    return generateNonce(11);
  }
  /**
   * @description 生成固定长度的随机字符串
   * @param length
   * @return nonce
   */
  public static String generateNonce(int length){
    String base = "abcdefghijklmnopqrstuvwxyz0123456789";     
    StringBuilder sb = new StringBuilder();     
    for (int i = 0; i < length; i++) {     
        int number = (int)(Math.random()* base.length());        
        sb.append(base.charAt(number));     
    }     
    return sb.toString();
  }
  /**
   * @description 截取字符串里面的数字
   * @param income
   * @return
   */
  public static String keepDigitFromString(String income){
    String result = null;
    if(null != income){
      result = income.replaceAll("[^0-9]","");
    }
    return result;
  }
  /**
   * @description 逆转字符串
   * @param income
   * @return
   */
  public static String reverse(String income){
    return (null != income)?new StringBuilder(income).reverse().toString():null;
  }
  
  /**
   * @description 实现mysql的rpad
   * @param origin 原始字符串
   * @param maxLength 最大长度
   * @param c 需要填充的字符串
   */
  public static String rpad(String origin, int maxLength, char c){
    if(null == origin){
      return null;
    }else{
      int len = (maxLength - origin.length());
      if(len <= 0){
        return origin.substring(0, maxLength);
      }else{
        StringBuilder sb = new StringBuilder(maxLength);
        sb.append(origin);
        for(int i=0;i < len;i++){
          sb.append(c);
        }
        return sb.toString();
      }
    }
  }
  
  /**
   * @description 实现mysql的lpad
   * @param origin 原始字符串
   * @param maxLength 最大长度
   * @param c 需要填充的字符串
   */
  public static String lpad(String origin, int maxLength, char c){
    if(null == origin){
      return null;
    }else{
      int len = (maxLength - origin.length());
      if(len <= 0){
        return origin.substring(0, maxLength);
      }else{
        StringBuilder sb = new StringBuilder(maxLength);        
        for(int i=0;i < len;i++){
          sb.append(c);
        }
        sb.append(origin);
        return sb.toString();
      }
    }
  }
  
  public static void main(String[] args) {
    System.out.println(rpad("aaaaaaaaa", 10, 'C'));
    System.out.println(lpad("aaaaaaaaaaaa", 10, 'C'));
    System.out.println(reverse(null));
    System.out.println(generateNonce());
    System.out.println(keepDigitFromString("d 52323 2fy"));
  }
}
