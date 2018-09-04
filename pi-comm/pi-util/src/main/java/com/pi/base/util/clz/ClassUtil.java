package com.pi.base.util.clz;

import java.io.File;
import java.io.FileFilter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassUtil {
	private static final Logger logger = LoggerFactory.getLogger(ClassUtil.class);
	private static String CLZ_NAME_SURFIX = ".class";
	private static String PROTOCOL_FILE = "file";
	private static String PROTOCOL_JAR = "JAR";
	private static String PKG_CLZ_DELIMITER = ".";
	private static String FILE_PKG_DELIMITER = "/";
  /**
   * @description 根据方法名字获取上面的注解
   * @param clz 类对象
   * @param methodName 方法名称
   * @throws SecurityException 
   * @throws NoSuchMethodException 
   */
  public static Annotation[] getAnnotationOnMethod(Class<?> clz, String methodName) 
      throws NoSuchMethodException, SecurityException{
    if(null != clz){      
      Method method = clz.getMethod(methodName, Object.class);
      return method.getAnnotations();
    }else{
      return null;
    }
  }
  
  public static <T extends Annotation> T getAnnotationOnClass(Class<?> clz, 
      Class<T> annotationClass){
    if(null == clz || null == annotationClass){
      return null;
    }
    return clz.getAnnotation(annotationClass);
  }
  
  /**
   * @param method 获取某各类方法上的特定注解
   * @param clz
   * @return Annotation
   */
  public static <T extends Annotation> T getAnnotation(Method method,Class<T> annotation){
    T result = null;
    result = method.getAnnotation(annotation);
    return result;
  }
  
  /**
   * @param method 获取某个类属性上的特定注解
   * @param clz
   * @return Annotation
   */
  public static <T extends Annotation> T getAnnotation(Field field,Class<T> annotation){
    T result = null;
    result = field.getAnnotation(annotation);
    return result;
  }
  
  /**
   * @param method 获取某各类(无参)方法上的特定注解
   * @param clz
   * @param String methodName
   * @return Annotation
   * @throws SecurityException 
   * @throws NoSuchMethodException 
   */
  public static <T extends Annotation> T getAnnotation(Class<T> annotation,String methodName,Class<?> clz) 
      throws NoSuchMethodException, SecurityException{
    T result = null;
    if(null != methodName && !"".equals(methodName)){
      Method method = clz.getMethod(methodName);
      result = getAnnotation(method, annotation);
    }
    return result;
  }
  
  
  /**
   * @description 根据包名字获取类信息(仅文件不包括jar)
   * @param packageName 包名
   * @param recursive 默认递归
   * @return Set<Class<?>> 类集合
   */
  public static Set<Class<?>> getAllClassFromPackage(String packageName){
  	//类集合
  	Set<Class<?>> clzList = new LinkedHashSet<Class<?>>();
  	String packagePath = packageName.replace(PKG_CLZ_DELIMITER, FILE_PKG_DELIMITER);
  	
  	try {
			Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(packagePath);
			if(null != resources){
				while(resources.hasMoreElements()){
					URL element = resources.nextElement();
					String protocol = element.getProtocol();
					logger.info("扫描到资源包：Element: {}, protocol: {}", element,protocol);
					if(PROTOCOL_FILE.equalsIgnoreCase(protocol)){
					  clzList.addAll(getClassFromPackage(URLDecoder.decode(element.getFile(), "UTF-8"), packageName));
					}else if(PROTOCOL_JAR.equalsIgnoreCase(protocol)){
					  JarURLConnection jarConnection = (JarURLConnection)element.openConnection();
					  if(null != jarConnection){					    
					    JarFile jarFile = jarConnection.getJarFile();
					    if(null != jarFile){
					      Enumeration<JarEntry> jarEntries = jarFile.entries();
					      if(null != jarEntries){
					        while(jarEntries.hasMoreElements()){
					          JarEntry netJarElement = jarEntries.nextElement();
					          if(netJarElement.getName().endsWith(CLZ_NAME_SURFIX)){
					            String className = netJarElement.getName().substring(0, netJarElement.getName().length() -6)
                          .replaceAll(FILE_PKG_DELIMITER, PKG_CLZ_DELIMITER);
					            clzList.add(getCurrentClassLoader().loadClass(className));
					          }
					        }
					      }
					    }
					  }
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取文件失败",e);
		}
  	//获取当前上下文环境的
  	return clzList;
  }
  
  /**
   * @description 根据包名字获取类信息(仅文件不包括jar)
   * @param packageName 包名
   * @param recursive 默认递归
   * @return Set<Class<?>> 类集合
   */
  public static Set<Class<?>> getClassFromPackage(String packagePath, String packageName){
  	return getAllClassFromPackage(packagePath, packageName,true);
  }
  
  /**
   * @description 根据包名字获取类信息
   * @param packageName 包名
   * @param packagePath 文件路径
   * @param recursive 是否递归获取
   * @return Set<Class<?>> 类集合
   */
  public static Set<Class<?>> getAllClassFromPackage(String packagePath, String packageName, boolean recursive){
  	Set<Class<?>> clzList = new LinkedHashSet<Class<?>>();
  	if(null != packageName && null != packagePath){	
  		File file = new File(packagePath);
  		if(file.exists()){
  		  if(file.isDirectory()){
  		    File[] clzFileList = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
              if(file.getName().endsWith(CLZ_NAME_SURFIX) || file.isDirectory()){
                return true;
              }else{
                return false;
              }
            }
          });
          
          if(null != clzFileList && clzFileList.length > 0){
            for(File childFile :clzFileList){
              if(childFile.isDirectory()){
                if(recursive){                
                  clzList.addAll(getAllClassFromPackage(childFile.getPath(), packageName + PKG_CLZ_DELIMITER + childFile.getName(), true));
                }
              }else{
//                添加java类到集合中
                getClassFromNameAndPackage(childFile.getName(), packageName, clzList);
              }
            }
          }
  		  }else if(packagePath.endsWith(CLZ_NAME_SURFIX)){
  		    getClassFromNameAndPackage(file.getName(), packageName, clzList);
  		  }
  		}
  	}
  	return clzList;
  }
  
  /**
   * @description 根据包名和文件名，将获取到的类放到集合中
   * @param fileName 文件名
   * @param packageName 包名
   * @param clzList 类集合
   */
  private static void getClassFromNameAndPackage(
  		String fileName, String packageName, Set<Class<?>> clzList){
		try {
			clzList.add(getCurrentClassLoader().loadClass(
			    packageName + PKG_CLZ_DELIMITER + (fileName.substring(0, fileName.length() - 6))));
		} catch (ClassNotFoundException e) {
			logger.error("未找到类：" + fileName, e);
		}
  }
  
  public static ClassLoader getCurrentClassLoader(){
    return Thread.currentThread().getContextClassLoader();
  }
  
	public static Set<Class<?>> 
  	queryAnnotationClass(String packageName, Class<? extends Annotation> annotation){
  	Set<Class<?>> sets = getAllClassFromPackage(packageName);
  	Set<Class<?>> tSet =new LinkedHashSet<Class<?>>();
  	if(null != sets){
  		for(Class<?> clz:sets){
  			if(null != clz.getAnnotation(annotation)){
  				try {
						tSet.add(clz);
					} catch (Exception e) {
						logger.error("未找到类：" + clz.getName(), e);
					}
  			}
  		}
  	}
  	return tSet;
  }
  @SuppressWarnings("unchecked")
	public static <T> Set<Class<T>> 
  	queryAnnotationClass(String packageName, Class<? extends Annotation> annotation, Class<T> requestClz){
  	Set<Class<?>> sets = getAllClassFromPackage(packageName);
  	Set<Class<T>> tSet =new LinkedHashSet<Class<T>>();
  	if(null != sets){
  		for(Class<?> clz:sets){
  			if(null != clz.getAnnotation(annotation)){
  				try {
						if(null != requestClz && requestClz.isAssignableFrom(clz)){
							tSet.add((Class<T>) clz);
						}
					} catch (Exception e) {
						logger.error("未找到类：" + clz.getName(), e);
					}
  			}
  		}
  	}
  	return tSet;
  }
  
  public static void main(String[] args)
      throws NoSuchMethodException, SecurityException {
    Set<Class<?>> sets = getAllClassFromPackage("com.dsmzg.base.annotation");
    System.out.println(sets);
    System.out.println("8#8".matches(".*#.*#.*"));
    //getAnnotation(Deprecated.class, "getAnnotation",ClassUtil.class);
  }
}
