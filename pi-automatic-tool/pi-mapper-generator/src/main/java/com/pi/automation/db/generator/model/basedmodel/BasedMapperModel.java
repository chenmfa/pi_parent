package com.pi.automation.db.generator.model.basedmodel;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.pi.automation.db.generator.tool.MapperGenerator;
import com.pi.automation.db.generator.util.TemplateUtil;

public class BasedMapperModel {

	private String className;
	private String packageName;
	private String entityName;
	private String entityClassFullName;
	private String tableName;
	private String paramClassFullName;
	private String paramClassName;

	private static String templateContent = "";
	static {
		try (InputStream is = MapperGenerator.class
				.getResourceAsStream("/template_extends_base_mapper.java");
				Scanner scanner = new Scanner(is, "UTF-8");) {
			templateContent = scanner.useDelimiter("\\A").next();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BasedMapperModel(String className, String packageName, String entityName,
			String entityClassFullName, String tableName, String paramClassName, String paramClassFullName) {
		super();
		this.className = className;
		this.packageName = packageName;
		this.entityName = entityName;
		this.entityClassFullName = entityClassFullName;
		this.tableName = tableName;
		this.paramClassName = paramClassName;
		this.paramClassFullName = paramClassFullName;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getClassName() {
		return className;
	}

	@Override
	public String toString() {
		Map<String, String> map = new HashMap<>();
		map.put("mapperPackage", packageName);
		map.put("mapperClassName", className);
		map.put("entityClassName", entityName);
		map.put("fullEntityClassName", entityClassFullName);
		map.put("tableName", tableName);
		map.put("fullParamClassName", paramClassFullName);
		map.put("paramClassName", paramClassName);
		return TemplateUtil.replace(templateContent, map);
	}

}
