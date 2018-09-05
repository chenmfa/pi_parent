package com.pi.base.util.config;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chenmfa
 * @date 创建时间: 2016-9-1 下午1:57:39
 * @description 读取server.properties配置文件并加载配置信息
 */
public class ConfigUtil {
  private static final Logger logger = LoggerFactory.getLogger(ConfigUtil.class); 
  private static Properties config = null;
  private static String PROPERTIES_FILE_ROUTER_STR = "server.config.active";
  private static String FILE_PATTERN_PREFIX = "server_";
  private static String FILE_PATTERN_SURFIX = ".properties";
  private static String FILE_COMM = "server.properties";
  public static boolean loadConfig(String propname){
    boolean loaded = false;
    InputStream stream = null;
   
    try {
      config = initializeConfigByFile(FILE_COMM);//先初始化现有文件
      //对于含有属性文件名的,加载指定文件
      if(null != propname && propname.trim().length() != 0){
        Properties destProperties = initializeConfigByFile(propname);
        mergerProperties(config, destProperties);
      }else{
        String configActive = (null != System.getProperty(PROPERTIES_FILE_ROUTER_STR))?
            System.getProperty(PROPERTIES_FILE_ROUTER_STR):config.getProperty(PROPERTIES_FILE_ROUTER_STR);
        if(null != configActive && configActive.trim().length() !=0){
          StringBuilder sb = new StringBuilder(FILE_PATTERN_PREFIX);
          sb.append(configActive);
          sb.append(FILE_PATTERN_SURFIX);
          Properties destProperties = initializeConfigByFile(sb.toString());
          mergerProperties(config, destProperties);
          logger.info("加载文件{}, 并覆盖现有属性值., 当前属性有：{}", sb.toString(), config);
        }
      }
      loaded = true;
    } catch (IOException e) {
      logger.error("获取配置文件信息出错", e);
    }finally{
      if(null != stream){
        try {
          stream.close();
        } catch (IOException e){
        }
      }
    }
    return loaded;
  }
  
  private static InputStream initializeStream(String input){
  	InputStream in = null;
  	try {
  		//获取当前文件路径
			String path = java.net.URLDecoder.decode(
					ConfigUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile(),"UTF-8");
			File sourceParent = new File(path);
			//如果文件存在,则获取当前目录下文件
			if(sourceParent.exists()){
				if(sourceParent.isDirectory()){
					//如果是目录,则获取目录下面的文件
					String realFile = (path.endsWith(File.separator)?path:path+File.separator)+input;
					File loadingFile = new File(realFile);
					if(loadingFile.exists()){
						in = new BufferedInputStream(new FileInputStream(realFile));
						logger.debug("读取文件：{}", realFile);
					}else{
						logger.debug("源文件目录不存在文件：{}", realFile);
					}
				}else if(sourceParent.isFile()){
					//如果是可执行文件,则获取可执行文件的目录下的配置文件
					File loadingFile = new File(sourceParent.getParentFile().getAbsolutePath()+File.separator+input);
					if(loadingFile.exists()){
						in =  new BufferedInputStream(new FileInputStream(
								sourceParent.getParentFile().getAbsolutePath()+File.separator+input));
						logger.debug("读取文件：{}", sourceParent.getParentFile().getAbsolutePath()+File.separator+input);
					}else{
						logger.debug("当前文件同级目录下不存在：{}", sourceParent.getParentFile().getAbsolutePath()+File.separator+input);
					}
				}
			}
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
		}
  	//如果未获取到配置文件,则
  	if(null == in){
  		logger.debug("读取默认ClassPath路径文件：/{}", input);
  		in = ConfigUtil.class.getResourceAsStream("/" + input);
  	}
    return in;
  }
  
  public static String getConfig(String... options){
    if(null == config){
      loadConfig(null);
    }
    int length = (null != options)?options.length:0;
    if(length >=1){      
      return config.getProperty(options[0], (options.length>1)?options[1]:null);
    }
    return null;
  }
  /**
   * @description 获取int 值
   * @param key
   * @param defaultValue 默认值
   * @return
   */
  public static int getInt(String key, int defaultValue){
    String value = getConfig(key);
    if(null == value){
      return defaultValue;
    }else{
      return Integer.parseInt(value.trim());
    }
  }
  /**
   * @description 获取int 值
   * @param key
   * @return
   */
  public static int getInt(String key){
    String value = getConfig(key);
    return Integer.parseInt(value.trim());
  }
  /**
   * @description 获取配置的布尔值
   * @param key 查找的键
   */ 
  public static boolean getBoolean(String key){
    return Boolean.parseBoolean(getConfig(key));
  }
  /**
   * @description 获取配置的布尔值
   * @param key 查找的键
   * @param defaultVal 默认值
   */ 
  public static boolean getBoolean(String key, boolean defaultVal){
    return null != getConfig(key)?Boolean.parseBoolean(getConfig(key)):defaultVal;
  }
  /**
   * @description 通过文件名来初始化输入文件流
   * @param fileName 存储有属性信息的目标文件
   * @return Properties 属性对象
   * @throws UnsupportedEncodingException
   * @throws IOException
   */
  private static Properties initializeConfigByFile(String fileName)
		  throws UnsupportedEncodingException, IOException{
	  Properties props = new Properties();
	  InputStream stream = initializeStream(fileName);
	  if(null != stream){	    
	    props.load(new InputStreamReader(stream,"UTF-8"));
	  }else{
	    logger.error("找不到文件{}", fileName);
	  }
    return props;
  }
  /**
   * @description 将目标属性覆盖到源中
   * @param sourceProperties
   * @param destProperties
   * @return
   */
  private static boolean mergerProperties(Properties sourceProperties, Properties destProperties){
	  if(null == sourceProperties){
		  sourceProperties = new Properties();
	  }
	  sourceProperties.putAll(destProperties);
	  return true;
  }
  /**
   * @description 初始化配置文件读取规则
   * @param settings 1. 全局文件名 2.扩展前缀名 3. 文件后缀
   */
  public static void initializeSettings(String...settings){
  	if(null != settings){
  		if(settings.length>0 && null != settings[0]){
  			FILE_COMM = settings[0].trim();
  			FILE_PATTERN_PREFIX = ((settings[0].indexOf(".")>0)?
  					settings[0].substring(0, settings[0].indexOf(".")):settings[0]).trim()+"_";
  		}
  		if(settings.length>1 && null != settings[1]){
  			FILE_PATTERN_PREFIX = settings[1].trim();
  		}
  		if(settings.length>2 && null != settings[2]){
  			FILE_PATTERN_SURFIX = settings[2].trim();
  		}
  	}
  }
  public static void main(String[] args) {
    System.out.println(System.getProperty("System.dev.mode"));
    String path = ConfigUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile();
    System.out.println(path);
    File file = new File(path);
    System.out.println(file.getParentFile().getAbsolutePath());
    System.out.println(file.exists());
    System.out.println(file.isDirectory());
    System.out.println(file.isFile());
    loadConfig(null);
  }
}
