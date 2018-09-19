package com.pi.base.util.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectTool {
	
	public static <T>T getOne(List<T> list){
		if(list == null || list.size() == 0){
			return null;
		}
		return list.get(0);
	}
	/**
	 * @description 将list转换成map
	 * @param list 集合
	 * @param keyProperty 属性
	 * @return
	 * @throws Exception
	 */
	public static <V>Map<String,V> listToMap(List<V> list, String keyProperty) 
			throws Exception{
		Map<String,V> vMap = new HashMap<String,V>();
		if(null != list && list.size() > 0){
			for(V v:list){
				vMap.put(BeanUtil.getproperty(v, keyProperty).toString(), v);
			}
		}
		return vMap;
	}
}
