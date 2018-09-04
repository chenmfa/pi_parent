package com.pi.base.util.lang;
/** 
* @author chenmfa
* @version 创建时间：2017年8月14日 下午4:04:23 
* @description
*/
public class IntegerUtil {
  /**
   * @description 将字符串转换成数字
   * @param input
   * @return 数字
   */
  public static Integer parseIntSafe(String input){
    try{
      if(null == input){
        return null;
      }else{        
        return Integer.parseInt(input);
      }
    }catch(Exception e){
      return null;
    }
  }
  
  /**
   * @description 生成指定位数的随机码
   * @param digit
   * @return String code
   */
  public static String generateRandomCode(int digit){
    StringBuilder sb= new StringBuilder();
    for(int i=0;i<digit;i++){
      sb.append((int)Math.floor(Math.random()*9+1));
    }
    return sb.toString();
  }
}
