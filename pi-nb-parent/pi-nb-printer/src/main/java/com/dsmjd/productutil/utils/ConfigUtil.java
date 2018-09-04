package com.dsmjd.productutil.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

/**
 * Config工具类
 * 
 * @author Administrator
 * 
 */
public class ConfigUtil {
  public static String printer_name = "Gprinter  GP-3150TN";
	public static int qrPrintCount = 2;

	public static int xyPrintX = 220;

	public static int xyPrintY = 0;

	public static int xyText1X = 385;

	public static int xyText1Y = 40;

	public static int xyMacX = 30;

	public static int xyMacY = 350;
	/** 新自定义属性开始 **/
	public static String gap = "1.2";
	public static int text_startY = 240;
	public static String qrcodeX = "10";
	public static String qrcodeY = "55";
	public static String printDirection = "1";
	public static String cellWidth = "5";
	/** 新自定义属性结束 **/
	public static String urlsBeforeMac = "https://api.weixin.qq.com/wxa/getwxacode?access_token=";

	public static String urlsAfterMac = "|lock";

	public static String versionStr = "V3.8";

	public static String macSplit = ":";

	public static String utilLogoImage = "utilLogo.png";

	public static String pcbLogoImage = "pcbLogo.png";

	public static String dsmLogoImage = "noQrCode.png";

	public static String qrCodeTempImageDir = "/qrTemp/";

	public static String loginedUser = "01";

	public static String loginedPassword = "123456";

	public static String showGetMacBtn = "1";

	public static String defaultComText = "COM2";
	
	public static String deviceTypeList = "T700";
	
	public static String fingerTypeList = "光学指纹头.半导体指纹头";
	
	public static String ServicePhone ="4008801889";
	
	public static String configFile = "config.properties";
	
	public static String oem="";
	
	public static String tokenUrl ="";
	public static String qrDomainUrl = "https://dmps.maquane.com/sys-web";
	public static String serverUrl = "/sys-web/appdev/getQrCode";
	public static String qrcodeUrl = qrDomainUrl + serverUrl;//"http://121.40.178.129/sys-web/devinfo/getQrCode";
	static {
	  loadConfig();
	}
	
	/**
	 * @description 加载配置信息
	 * @return
	 */
	public static boolean loadConfig(){
	  boolean result = true;
	  try {
      Properties sysProp = new Properties();
//    获取当前文件目录
//    String file = ConfigUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile();
      String userdir = System.getProperty("user.dir"); 
      Reader reader;
      File file = new File(userdir+File.separator+configFile);
      if(file.exists()){        
        reader = new InputStreamReader(new FileInputStream(file),"UTF-8");
      }else{
        reader = new InputStreamReader(ConfigUtil.class.getClassLoader().getResourceAsStream(configFile),"UTF-8");
      }
      if(null != reader){
        sysProp.load(reader);
        System.out.println("加载配置文件"+configFile);
        versionStr = sysProp.getProperty("versionStr",versionStr);
        urlsAfterMac = sysProp.getProperty("urlsAfterMac",urlsAfterMac);
        macSplit = sysProp.getProperty("macSplit",macSplit);
        utilLogoImage = sysProp.getProperty("utilLogoImage",utilLogoImage);
        pcbLogoImage = sysProp.getProperty("pcbLogoImage",pcbLogoImage);
        dsmLogoImage = sysProp.getProperty("dsmLogoImage",dsmLogoImage);
        qrCodeTempImageDir = sysProp.getProperty("qrCodeTempImageDir",qrCodeTempImageDir);
        loginedUser = sysProp.getProperty("loginedUser",loginedUser);
        loginedPassword = sysProp.getProperty("loginedPassword",loginedPassword);
        showGetMacBtn = sysProp.getProperty("showGetMacBtn",showGetMacBtn);
        defaultComText = sysProp.getProperty("defaultComText",defaultComText);

        xyPrintX = Integer.valueOf(sysProp.getProperty("xyPrintX",String.valueOf(xyPrintX)));
        xyPrintY = Integer.valueOf(sysProp.getProperty("xyPrintY",String.valueOf(xyPrintY)));
        xyText1X = Integer.valueOf(sysProp.getProperty("xyText1X",String.valueOf(xyText1X)));
        xyText1Y = Integer.valueOf(sysProp.getProperty("xyText1Y",String.valueOf(xyText1Y)));
        xyMacX = Integer.valueOf(sysProp.getProperty("xyMacX",String.valueOf(xyMacX)));
        xyMacY = Integer.valueOf(sysProp.getProperty("xyMacY",String.valueOf(xyMacY)));
        deviceTypeList = sysProp.getProperty("deviceTypeList",deviceTypeList);
        fingerTypeList = sysProp.getProperty("fingerTypeList",fingerTypeList);
        qrPrintCount = Integer.valueOf(sysProp.getProperty("qrPrintCount",String.valueOf(qrPrintCount)));
        oem = sysProp.getProperty("oem","");
        ServicePhone = sysProp.getProperty("ServicePhone","");
        urlsBeforeMac = sysProp.getProperty("urlsBeforeMac",urlsBeforeMac);
        tokenUrl = sysProp.getProperty("tokenUrl",tokenUrl);
        gap = sysProp.getProperty("gap",gap);
        text_startY = Integer.parseInt(sysProp.getProperty("text-startY",String.valueOf(text_startY)));
        qrcodeX = sysProp.getProperty("qrcodeX",String.valueOf(qrcodeX));
        qrcodeY = sysProp.getProperty("qrcodeY",String.valueOf(qrcodeY));
        cellWidth = sysProp.getProperty("cellWidth",cellWidth);
        printDirection = sysProp.getProperty("printDirection",printDirection);
        printer_name = sysProp.getProperty("printer-name",printer_name);
        if(StringUtils.isNotBlank(oem)){
          urlsBeforeMac = urlsBeforeMac.replace("dsmzg.com","about:blank");
        }
        
        qrDomainUrl = sysProp.getProperty("qr_domain_url",qrDomainUrl);
        qrcodeUrl = qrDomainUrl+serverUrl;
      }    
    } catch (Exception e) {
      result = false;
      e.printStackTrace();
    }
	  return result;
	}
	
	public static void setConfigFile(String name){
	  configFile = name;
	  loadConfig();
	}
}
