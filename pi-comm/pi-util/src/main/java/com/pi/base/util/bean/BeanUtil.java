package com.pi.base.util.bean;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi.base.annotation.FieldAlia;
import com.pi.base.util.clz.ClassUtil;
import com.pi.base.util.lang.ArrayUtil;

public class BeanUtil {
  private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);
	private BeanUtil(){
	}
	
	/**
	 * @description 将对象中的属性转换成clzs包含的map
	 * @param obj 实例对象
	 * @param clzs 需要找寻属性的类对象
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
  public static Map<String,Object> objectToMap(Object obj, Class<?>... clzs) 
	    throws IllegalArgumentException, IllegalAccessException{
	  Map<String,Object> map = new HashMap<String,Object>();
	  if(null != obj){
	    Field[] fields;
	    if(obj instanceof Map){
	      return (Map<String,Object>)obj;
	    }else if(null != clzs && clzs.length>0){
	      fields = getFiledListFromClz(clzs);
	    }else{
	      fields = getFiledListFromClz(obj.getClass());
	    }
	    return wrapFieldsIntoMap(fields, map, obj);
	  }
	  return map;
	}
	private static Map<String,Object> wrapFieldsIntoMap(Field[] fields, Map<String,Object> map,Object fieldObject) 
	    throws IllegalArgumentException, IllegalAccessException{
    if(null != fields){
      for(int a=0, length= fields.length; a<length; a++){
        if(!fields[a].isAccessible()){
          fields[a].setAccessible(true);
        }
        map.put(fields[a].getName(), fields[a].get(fieldObject));
      }
    }
    return map;
	}
	
	/**
	 * @description 提取类里面的属性
	 * @param clzs 多个类
	 * @return
	 */
	public static Field[] getFiledListFromClz(Class<?>... clzs){
	  Field[] fields = null;
	  if(null != clzs && clzs.length>0){  
	    for(int a=0, length= clzs.length; a<length; a++){
	      if(null == fields){
	        fields = getFieldFromSingle(clzs[a]);
	      }else{
	        fields = ArrayUtil.concat(fields, clzs[a].getDeclaredFields());
	      }
	    }
	  }
	  return fields;
	}
	private static Field[] getFieldFromSingle(Class<?> clz){
	  Field[] old = null;
    for(; clz != Object.class ; clz = clz.getSuperclass()) {  
      try {  
          Field[] fields = clz.getDeclaredFields();
          if(null == old){
            old = fields;
          }else{
            old = ArrayUtil.concat(old, fields);
          }
      } catch (Exception e) {         
      }   
    }
    return old;
	}
	
	/**
	 * @author Jane Doe
	 * @param originObj
	 * @param newObj
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws IntrospectionException
	 */
	public static void beanUpdate(Object originObj, Object newObj) 
	    throws NoSuchMethodException, SecurityException, IllegalAccessException, 
	    IllegalArgumentException, InvocationTargetException, IntrospectionException{
		if(originObj.getClass() != newObj.getClass()){
			logger.info("两个对象类不相同");
			return;
		}
		Map<String,Method> setMethodMap = getBeanSetMethodMap(originObj);
		Map<String,Method> getMethodMap = getBeanGetMethodMap(newObj);
		for(Map.Entry<String, Method> entry : setMethodMap.entrySet()){
			String fieldName = entry.getKey();
			Method setMethod = entry.getValue();
			Method getMethod = getMethodMap.get(fieldName);
			if(getMethod.invoke(newObj, new Object[]{}) != null && StringUtils.isNotBlank(getMethod.invoke(newObj, new Object[]{}).toString())){
				setMethod.invoke(originObj, getMethod.invoke(newObj, new Object[]{}));
			}
		}
	}
	/**
	 * @description 获取对象的Get方法(修改过)
	 * @param obj
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IntrospectionException 
	 */
	public static Map<String,Method> getBeanGetMethodMap(Object obj) 
	    throws NoSuchMethodException, SecurityException, IntrospectionException{
		Map<String,Method> methodMap = new HashMap<String,Method>();
		if(null != obj){
//		  获取对象的属性		  
	    Map<String,Class<?>> fieldMap = reflectClassFieldNameAndTypeMap(obj);
	    for(Map.Entry<String, Class<?>> entry : fieldMap.entrySet()){
	      Method methodGet = getReadMethod(obj.getClass(), entry.getKey());
	      if(null != methodGet){
	        methodMap.put(entry.getKey(), methodGet);
	      }
//	      String fieldName = entry.getKey();
//	      String firstChar = fieldName.substring(0, 1);
//	      String methodName = "get" + entry.getKey().replaceFirst(entry.getKey().substring(0, 1), firstChar.toUpperCase(Locale.US));
//	      logger.info("字段" + fieldName + "对应的get方法为" + methodName);
//	      Class<?>[] methodParamType = getMethodParamTypes(obj, methodName);
//	      methodGet = obj.getClass().getDeclaredMethod(methodName, methodParamType);
	      
	    }
		}
		return methodMap;
	}
	 /**
   * @description 获取对象的Set方法(修改过)
   * @param obj
   * @return
   * @throws NoSuchMethodException
   * @throws SecurityException
   * @throws IntrospectionException 
   */
	public static Map<String,Method> getBeanSetMethodMap(Object obj) 
	    throws NoSuchMethodException, SecurityException, IntrospectionException{
		Map<String,Method> methodMap = new HashMap<String,Method>();
		Map<String,Class<?>> fieldMap = reflectClassFieldNameAndTypeMap(obj);
		for(Map.Entry<String, Class<?>> entry : fieldMap.entrySet()){
			Method methodSet = getWriteMethod(obj.getClass(), entry.getKey());
//			String fieldName = entry.getKey();
//			String firstChar = fieldName.substring(0, 1);
//			String methodName = "set" + fieldName.replaceFirst(firstChar, firstChar.toUpperCase(Locale.US));
//	     logger.info("字段" + fieldName + "对应的set方法为" + methodName);
//			Class<?>[] methodParamType = getMethodParamTypes(obj, methodName);
//			methodSet = obj.getClass().getDeclaredMethod(methodName, methodParamType);
			methodMap.put(entry.getKey(), methodSet);
		}
		return methodMap;
	}
	
	/**
	 * @description 根据字符串或者对象获取 目标对象的属性信息
	 * @param obj
	 * @return
	 */
	public static Map<String,Class<?>> reflectClassFieldNameAndTypeMap(Object obj){
		Map<String,Class<?>> fieldMap = new HashMap<String,Class<?>>();
		if(null != obj){
		  Class<?> clazz = null;
	    try {
	      if(obj instanceof String){
	        String obj_string = (String) obj;
	        clazz = Class.forName(obj_string);
	      }else{
	        clazz = obj.getClass();
	      }
	      Field[] fieldList = clazz.getDeclaredFields();
	      for(int i=0;i<fieldList.length;i++){
	        fieldMap.put(fieldList[i].getName(), fieldList[i].getType());
	      }
	    } catch (ClassNotFoundException e) {
	      logger.error("获取对象的属性错误",e);
	    }
		}
		return fieldMap;
	}
	
	public static Method getWriteMethod(Class<?> clz, String key) throws IntrospectionException{
		if(null != clz && null != key){
			PropertyDescriptor propDesc = new PropertyDescriptor(key, clz);
			return propDesc.getWriteMethod();
		}
		return null;
	}
	
	public static Method getReadMethod(Class<?> clz, String key) throws IntrospectionException {
		if(null != clz && null != key){
			PropertyDescriptor propDesc = new PropertyDescriptor(key, clz);
			return propDesc.getReadMethod();
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public static Class[]  getMethodParamTypes(Object classInstance, String methodName){
		Class[] paramTypes = null;
		try {
			paramTypes = null;
			Method[]  methods = classInstance.getClass().getMethods();//??��?��?��??
			for (int  i = 0;  i< methods.length; i++) {
				if(methodName.equals(methods[i].getName())){//???�???��?��???????��?? 
					Class[] params = methods[i].getParameterTypes();
			        paramTypes = new Class[ params.length] ;
			        for (int j = 0; j < params.length; j++) {
			            paramTypes[j] = Class.forName(params[j].getName());
			        }
			        break; 
			    }
			}
		} catch (ClassNotFoundException e) {
		  logger.error("获取对象的属性错误",e);
		}
		return paramTypes;
	}
	
	/**
	 * @description 从对象中获取属性值
	 * @param obj
	 * @param property
	 * @param args
	 * @return
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	public static <T>T getproperty(Object obj, String property, Object...args) 
			throws IntrospectionException, IllegalAccessException, 
			IllegalArgumentException, InvocationTargetException{
		if(null != obj){
			Method readMethod = getReadMethod(obj.getClass(), property);
			if(null != readMethod)
			  return (T)readMethod.invoke(obj, args);
		}
		return null;
	}
	
	/**
	 * @description 设置某个对象里属性的值
	 * @param obj
	 * @param propName 属性名
	 * @param propValue 属性值
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws Exception
	 */
	public static void setProperty(Object obj,String propName, Object propValue) throws Exception{
		if(null != obj && null != propName){
			Method writeMethod = null;
      try {
        writeMethod = getWriteMethod(obj.getClass(), propName);
      } catch (IntrospectionException e) {
      }
      if(null == writeMethod){
        Field[] fields = getFiledListFromClz(obj.getClass());
        if(null == fields || fields.length  == 0){
          return;
        }
        for(Field filed:fields){
          FieldAlia alias = ClassUtil.getAnnotation(filed, FieldAlia.class);
          if(null != alias && propName.equals(alias.name())){
            if(!filed.isAccessible()){
              filed.setAccessible(true);
            }
            filed.set(obj, propValue);
          }
        }
      }else{        
        writeMethod.invoke(obj, propValue);
      }
		}
	}
}