package com.pi.base.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chenmfa
 * @description 处理request请求的通用类
 */
public class RequestUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(RequestUtil.class);
	
	public static String LOCAL_HOST = "localhost";
	public static String LOOP_BACK_ADDR = "127.0.0.1";
	
	/**
	 * @description 获取request 的来源
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getHeader("HTTP_CLIENT_IP");  
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
    //对于本地地址,有时候会显示成ipv6的
    if(isLoopAddress(ip)){
      InetAddress address = null;
      try {
        address = InetAddress.getLocalHost();
        ip = address.getHostAddress();
        //logger.info(ip+"是本地地址");
      } catch (UnknownHostException e){
        ip= "127.0.0.1";
      }
    }
		return ip;
	}
	
	/**
	 * @description 获取访问ip的mac地址
	 * @param request
	 * @return
	 */
	public static String getMacAddress(HttpServletRequest request){
		try {
	    String ip = getIpAddr(request);
	    if(isLocalAddress(ip)){
	    	byte[] hardwareAddress;
	        InetAddress address = InetAddress.getLocalHost();
	        hardwareAddress = NetworkInterface.getByInetAddress(address).getHardwareAddress();
	        StringBuilder sb = new StringBuilder();
	    		for(byte by :hardwareAddress){
	    			if(sb.length()>0){
	    				sb.append(":");
	    			}
	    			String hexString = Integer.toHexString(by & 0xFF);
	    			sb.append(hexString.length()==1?"0"+hexString:hexString);
	    		}
	    		return sb.toString().toUpperCase();		
	    }else{
	      String mac = "";//getMacByNbtStat(ip);
	     /* if(StringUtils.isBlank(mac)){
	        mac = getMacByArp(ip);
	      }*/
	    	return mac;
	    }
    } catch (Exception e) {
      logger.error("获取MAC地址出错",e);
      return "";
    }
	}
	
	public static String getMacByArp(String ip){
	  try {
	    long start = System.currentTimeMillis();
	    Process process = Runtime.getRuntime().exec("cmd /c ping "+ip);
      process.waitFor();
      process = Runtime.getRuntime().exec("arp -a "+ip);
      long end = System.currentTimeMillis();
      System.out.println(end-start);
      BufferedReader bfr =new BufferedReader(new InputStreamReader(process.getInputStream(),"GBK"));
      String line ="";    
      //String macPrefix = "MAC 地址 =";
      String macPrefix = ip;
      String macAddress="";
      while((line = bfr.readLine()) != null){
        //logger.info(line);
        int index= line.indexOf(macPrefix);
        if(index !=-1){
          String[] arr = line.trim().split("\\s+");
          macAddress = arr[1].trim().replaceAll("-", ":").toUpperCase();  
          break;
        }
      }
      return macAddress;
    }catch (Exception e) {
      logger.error("arp -a 获取mac地址失败");
      return "";
    }
	}
	
	public static String getMacByNbtStat(String ip){
	  try {
      Process process = Runtime.getRuntime().exec("cmd /c C:\\Windows\\sysnative\\nbtstat.exe -a "+ ip);
      process.waitFor();
      BufferedReader bfr =new BufferedReader(new InputStreamReader(process.getInputStream(),"GBK"));
      String line ="";    
      String macPrefix = "MAC 地址 =";
      String macAddress="";
      while((line = bfr.readLine()) != null){
        //logger.info(line);
        int index= line.indexOf(macPrefix);
        if(index !=-1){        
          macAddress = line.replaceAll("-", ":").trim().toUpperCase();  
          break;
        }
      }
      return macAddress;
    } catch (Exception e) {
      logger.error("nbtstat -a 获取mac地址失败");
      return "";
    }
	}
	
	
	/**
	 * 设备请求来源
	 * @param request
	 * @return int 1:android,windows,web chrome  2 iphone,ipad
	 */
	public static int checkDeviceType(HttpServletRequest request){
		int flag = 1;
		String userAgent = request.getHeader("user-agent");
		if(StringUtils.isEmpty(userAgent)){
			return flag; //默认安卓或者windows或者webchrome
		}
		String[] keywords = { "Fiddler", "iPhone", "iPod", "iPad" };     //所有苹果设备头信息，Fiddler是自定义
		for(String item:keywords){
			if(userAgent.contains(item)){
				flag = 2;
				break;
			}
		}
		return flag;	
	}
	
	/**
	 * @param request
	 * @param 获取request里面的JSON数据
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public static String getJSONData(HttpServletRequest request) 
	    throws UnsupportedEncodingException, IOException{
	  BufferedReader bur = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
	  StringBuilder sb = new StringBuilder();
	  String line;
	  while((line = bur.readLine()) != null ){
	    sb.append(line);
	  }
	  request.getInputStream().close();
	  return sb.toString();
	}
	/**
	 * @description 判断该地址是否为本地回环地址
	 * @param ip
	 * @return
	 */
	public static boolean isLoopAddress(String ip){
	  if(null != ip && (ip.equals("0:0:0:0:0:0:0:1") || ip.equals("127.0.0.1"))){
	    return true;
	  }
	  return false;
	}
	
	 /**
   * @description 判断该地址是否为本地回环地址
   * @param ip
   * @return
	 * @throws UnknownHostException 
   */
  public static boolean isLocalAddress(String ip) 
      throws UnknownHostException{
    if(ip.equals("127.0.0.1") || null != ip && (ip.equals("0:0:0:0:0:0:0:1")) 
        || ip.equalsIgnoreCase(InetAddress.getLocalHost().getHostAddress())){
      return true;
    }
    return false;
  }
  /**
   * @description 判断请求是否为Ajax
   * @param request
   * @return boolean 是： true
   */
  public static boolean isAjax(HttpServletRequest request){
    if (request.getHeader("accept").indexOf("application/json") >= 0
            || (request.getHeader("X-Requested-With")!= null && 
            request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") >=0)){
      return true;
    }else{
      return false;
    }
  }
}
