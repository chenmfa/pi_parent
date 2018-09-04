package com.pi.base.util.lang;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ByteUtil {
  private static final Logger logger = LoggerFactory.getLogger(ByteUtil.class);

  /**
   * @description 将字节转换成字符串
   * @param bytes
   * @return String
   */
	public static String getByteString(byte[] bytes) {
		return getByteString(bytes, "UTF-8");
	}
	
  /**
   * @description 将字节转换成字符串
   * @param bytes
   * @return String
   */
  public static String getByteString(byte[] bytes, String charset) {
    try {
      return new String(bytes, charset);
    } catch (UnsupportedEncodingException e) {
      logger.error("根据字节码获取设备的UUID出错",e);
      return null;
    }    
  }

	/**
	 * UUIDStr获得byte数组
	 * 
	 * @param uuidStr
	 * @return
	 */
	public static byte[] getUuidByte(String uuidStr) {

		String[] strs = uuidStr.split("-");
		byte[] uuidb = new byte[8];
		try {
			for (int i = 0; i < strs.length; i++) {
				uuidb[i] = (byte) Integer.parseInt((strs[i]), 16);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uuidb;
	}

	/**
	 * 
	 * 获得设备UUID
	 * 
	 * @param uuidByte
	 * @return
	 */
	public static String getStr(byte[] strb) {

		String strs = "";
		try {
			strs = new String(strb, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return strs;
	}

	/**
	 * 
	 * 获得手机登录帐号
	 * 
	 * @param account
	 * @return
	 */
	public static String accountoruuid(byte[] account) {

		String strs = "";
		try {
			strs = new String(account, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return strs;
	}

  /**
   * @description 拼接字节 
   * @param byte[] b1,b2
   * @return
   */
	public static byte[] concat(byte[] b1, byte[] b2) {
	  if(null == b1){
	    return b2;
	  }else if(null == b2){
	    return b1;
	  }else{	    
	    byte[] newBytes = Arrays.copyOf(b1, b1.length+b2.length);
	    System.arraycopy(b2, 0, newBytes, b1.length, b2.length);
	    return newBytes;
	  }
	}

	/**
	 * @description 将IP地址转化成byte数组 如: 192.168.10.5== c0 a8 a 5
	 * @param ipStr
	 * @return byte[] ipbyte
	 */
	public static byte[] ipToBytes(String ipStr) {
		byte ipbyte[] = new byte[4];
		String[] ipStrs = ipStr.split("\\.");
		for (int i = 0; i < ipStrs.length; i++) {
			ipbyte[i] = (byte) (Integer.parseInt(ipStrs[i]));
		}
		return ipbyte;
	}
	
	/**
	 * 根据输入的mac Byte数组的得到mac地址的字符串 如：c0 a8 a 5 fe ce = C0-A8-0A-05-FE-CE
	 * 
	 * @param macByte
	 * @return
	 */
	public static String macByteToString(byte[] macByte) {
		String macStr = "";
		for (int i = 0; i < macByte.length; i++) {
			String str = Integer.toHexString((macByte[i] & 0xff));
			if (str.length() == 1) {
				str = "0" + str;
			}
			macStr += str + ":";
		}
		return macStr.substring(0, macStr.length() - 1).toUpperCase();
	}
	
	/**
	 * 根据输入的IC card Byte数组的得到IC卡的字符串 如：c0 a8 a 5 fe ce = C0 A8 0A 05 FE CE
	 * 
	 * @param macByte
	 * @return
	 */
	public static String byteToString(byte[] macByte) {
		String macStr = "";
		for (int i = 0; i < macByte.length; i++) {
			String str = Integer.toHexString((macByte[i] & 0xff));
			if (str.length() == 1) {
				str = "0" + str;
			}
			macStr += str + " ";
		}
		return macStr.trim();
	}

	/**
	 * 根据输入的mac Byte数组的得UUID值
	 * 
	 * @param macByte
	 * @return
	 */
	public static String timeToString(byte[] b) {
		String timeStr = "";
		String year = b[1]+"";
		if(year.length()<2){
			year = "0"+year;
		}
		timeStr = b[0]+""+year+"-"+b[2]+"-"+b[3]+" "+b[4]+":"+b[5];
		return timeStr;
	}
	
	 /**
   * @description 根据输入的byte值生成n进制的字符串(带分隔符)
   * @param macByte
   * @return
   */
  public static String byteToHex(byte[] b,int radix) {
    return byteToHex(b,true,radix);
  }
  
	/**
   * @description 根据输入的byte值生成16进制的字符串(带分隔符)
   * @param macByte
   * @return
   */
  public static String byteToHex(byte[] b) {
    return byteToHex(b,true);
  }
  /**
   * @description 根据输入的byte值生成16进制的字符串(不带分隔符)
   * @param macByte
   * @return
   */
  public static String byteToHexNoSpace(byte[] b) {
    return byteToHex(b,false);
  }
  
  /**
   * @description 根据输入的byte值生成16进制的字符串(不带分隔符)
   * @param macByte
   * @return
   */
  public static String byteToHexNoSpace(byte[] b, int radix) {
    return byteToHex(b,false,radix);
  }
  
	/**
	 * @description 根据输入的byte值生成16进制的字符串(带分隔符)
	 * @param needSpace 是否带空格
	 * @return
	 */
	public static String byteToHex(byte[] b,boolean needSpace) {
		return byteToHex(b, needSpace,16,true);
	}
	
	 /**
   * @description 根据输入的byte值生成n进制的字符串(带分隔符)
   * @param needSpace 是否带空格
   * @return
   */
  public static String byteToHex(byte[] b,boolean needSpace, int radix) {
    return byteToHex(b, needSpace,radix,true);
  }
  
  /**
  * @description 根据输入的byte值生成n进制的字符串(带分隔符)
  * @param needSpace 是否带空格
  * @param appendZero 是否自动补0
  * @return
  */
 public static String byteToHex(byte[] b,boolean needSpace, int radix,boolean appendZero) {
   return byteToHex(b, needSpace?" ":"",radix,appendZero);
 }
	
	 /**
   * @description 根据输入的byte值生成16进制的字符串(带分隔符)
   * @param msg
   * @return
   */
  public static String byteToHexString(byte[] b,String stuffing) {
    return byteToHex(b,stuffing,16,true);
  }
  /**
  * @description 根据输入的byte值生成16进制的字符串(带分隔符)
  * @param msg
  * @param appendZero 是否自动补0
  * @return
  */
 public static String byteToHexString(byte[] b,String stuffing,boolean appendZero) {
   return byteToHex(b,stuffing,16,appendZero);
 }
  
  /**
   * @description 根据输入的byte值生成n进制的字符串(带分隔符)
   * @param msg
   * @return
   */
  public static String byteToHex(byte[] b,String stuffing,int radix,boolean appendZero) {
    StringBuilder msg = new StringBuilder();    
    for(int k=0;k<b.length;k++){
      if(Integer.toString(b[k]&0xFF, (radix<=0)?16:radix).length()==1 && appendZero){
        msg.append("0");
      }
      String temp = Integer.toHexString(b[k]  & 0xFF);
      msg.append(temp+((null != stuffing)?stuffing:""));
    }
    return msg.substring(0, msg.length());
  }

  /**
   * @description 将16进制字符串转换为原始字节
   * @param data 16进制字符串
   * @return
   */
  public static byte[] hexToByte(String data){
    return hexToByte(data," ");
  }
  
  /**
   * @description 将16进制字符串转换为原始字节
   * @param data 16进制字符串
   * @return
   */
  public static byte[] hexToByte(String data,int radix){
    return hexToByte(data,"",radix);
  }
  
  /**
   * @description 将16进制字符串转换为原始字节
   * @param data 16进制字符串
   * @return
   */
  public static byte[] hexToByte(String data,boolean hasSpace){
    return hexToByte(data,(hasSpace?" ":""));
  }
  
  public static byte[] hexToByte(String data,String stuffing){
    return hexToByte(data,stuffing,16);
  }
  
  /**
   * @description 将16进制字符串转换为原始字节
   * @param data 16进制字符串
   * @return
   */
  public static byte[] hexToByte(String data,String stuffing, int radix){
    if(null == data){
      data ="";
    }else{
      if(null != stuffing && !"".equals(stuffing)){        
        data = data.replaceAll(((null != stuffing)?stuffing:""), "").toLowerCase();
      }
      if(data.length()%2 !=0){
        throw new IllegalArgumentException("传入参数的长度不正确");
      }
    }
    byte[] byteArray = new byte[data.length()/2]; 
    int len = byteArray.length; 
    if(radix == 16){//其实这里是一个老方法,移动过来之后舍不得删除>..<
      int j = 0;
      for (int i = 0; i<len; i++) { 
        j = (i<<1); 
        byteArray[i] = 0; 
        char c = data.charAt(j); 
        if ('0'<=c && c<='9') { 
          byteArray[i] |= ((c-'0')<<4); 
        } else if ('A'<=c && c<='F') { 
          byteArray[i] |= ((c-'A'+10)<<4); 
        } else if ('a'<=c && c<='f') { 
          byteArray[i] |= ((c-'a'+10)<<4); 
        } 
        j++; 
        c = data.charAt(j); 
        if ('0'<=c && c<='9') { 
          byteArray[i] |= (c-'0'); 
        } else if ('A'<=c && c<='F') { 
          byteArray[i] |= (c-'A'+10); 
        } else if ('a'<=c && c<='f') { 
          byteArray[i] |= (c-'a'+10); 
        } 
      } 
    }else{
      for (int i = 0; i<len; i++) {         
        int intVal = Integer.parseInt(data.substring(i*2, i*2+2), radix);
        byteArray[i] = (byte)intVal;
      }
    }    
    return byteArray; 
  }
  /**
   * @description 将byte数组转换成Long值(低位较大)
   * @param hTlByte (highToLowByte)
   * @return
   */
  public static Long byteToInt(byte[] lThByte){
    long convertedInterger = 0;
    
    for(int i = 0; i < lThByte.length; i++){
        byte curValue = lThByte[i];
        long shiftedValue = curValue << ((lThByte.length-i-1) * 8);
        long mask = 0xFF << ((lThByte.length-i-1) * 8);
        long maskedShiftedValue = shiftedValue & mask;
        convertedInterger |= maskedShiftedValue;
    } 
   return convertedInterger;
  }
  
  /**
   * @description 将byte数组转换成Long值(低位较小)
   * @param lThByte (lowToHighByte)
   * @return
   */
  public static Long byteToInt2(byte[] lThByte){
    long convertedInterger = 0;
    
    for(int i = 0; i < lThByte.length; i++){
        byte curValue = lThByte[i];
        long shiftedValue = curValue << (i * 8);
        long mask = 0xFF << (i * 8);
        long maskedShiftedValue = shiftedValue & mask;
        convertedInterger |= maskedShiftedValue;
    } 
   return convertedInterger;
  }
  /**
   * @description 字节数组的顺序对调
   * @param b
   * @return
   */
  public static byte[] byteReverse(byte[] b){
    byte[] result = null;
    if(null != b){
      result = new byte[b.length];
      for(int i= b.length -1,j=0;i>=0;i--,j++){
        result[j] = b[i];
      }
    }
    return result;
  }
  
  /**
   * @description 将数字转换成字节数组(顺序为由低位到高位)
   * @param value
   * @param arrLength
   * @return
   */
  public static byte[] intToByteArray(int value,int arrLength){
    byte[] convertedByteArr = new byte[arrLength];
    for (int i = 0; i < convertedByteArr.length; i++) {
      convertedByteArr[i] = (byte) ((value >> (8 * i)) & 0xFF);
    }
   return convertedByteArr;
  }
  
  public static void main(String[] args) {
    byte[] b = new byte[]{0x02,0x27};
    System.out.println(byteToInt(b));
    byte[] newb = byteReverse(b);
    System.out.println(byteToInt2(newb));
    System.out.println(byteToHex(intToByteArray(551, 2)));

  }
  
  /**
   * @description 将包装类字节转换成字节数组
   * @param  Byte... bytes
   * @return byte[]
   */
  public static byte[] byteToPrimitive(Byte... bytes){
    if(null != bytes){
      int len = bytes.length;
      byte[] b = new byte[len];;
      for(int i = 0; i < len; i++){
        b[i] = bytes[i].byteValue();
      }
      return b;
    }
    return null;
  }
}
