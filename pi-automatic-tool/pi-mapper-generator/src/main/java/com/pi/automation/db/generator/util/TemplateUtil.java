package com.pi.automation.db.generator.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class TemplateUtil {

	
	public static String replace(String template, Map<String, String> map){
		Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String, String> entry = iterator.next();
			template = template.replace("${"+entry.getKey()+"}", entry.getValue());
		}
		return template;
	}
}
