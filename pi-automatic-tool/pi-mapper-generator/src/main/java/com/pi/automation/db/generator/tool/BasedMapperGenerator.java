package com.pi.automation.db.generator.tool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import com.pi.automation.db.generator.model.ColumnModel;
import com.pi.automation.db.generator.model.FieldModel;
import com.pi.automation.db.generator.model.MethodModel;
import com.pi.automation.db.generator.model.basedmodel.BasedClassModel;
import com.pi.automation.db.generator.model.basedmodel.BasedMapperModel;
import com.pi.automation.db.generator.model.basedmodel.BasedParamModel;
import com.pi.automation.db.generator.util.TemplateUtil;

/**
 * @description 数据库脚本生成工具
 * @author com.liulutu.liugang.db.mapper(改编自这个模板)
 * @updateby chenmfa
 */
public class BasedMapperGenerator {


	public void generate(String databaseName, String tableName, String superPackageName)
			throws Exception {
		System.out.println("读取配置中...");
		Properties datasource = new Properties();
		try (InputStream resourceAsStream = BasedMapperGenerator.class
				.getResourceAsStream("/datasource.properties");) {
			datasource.load(resourceAsStream);
		}
		Properties types = new Properties();
		try (InputStream resourceAsStream = BasedMapperGenerator.class
				.getResourceAsStream("/type.properties");) {
			types.load(resourceAsStream);
		}
		String mapperTemplate = "";
		try (InputStream is = BasedMapperGenerator.class
				.getResourceAsStream("/BasedTemplate.xml");
				Scanner scanner = new Scanner(is, "UTF-8");) {
			mapperTemplate = scanner.useDelimiter("\\A").next();
		}

		try (Connection conn = DriverManager.getConnection(
				datasource.getProperty("jdbc.url"),
				datasource.getProperty("jdbc.username"),
				datasource.getProperty("jdbc.password"));) {
			
			System.out.println("解析数据库表中...");
			PreparedStatement prepareStatement = conn
					.prepareStatement("select column_name, data_type, COLUMN_COMMENT from columns where table_schema='"+databaseName+"' and table_name='"
//					.prepareStatement("select column_name, data_type, COLUMN_COMMENT from columns where  table_name='"
							+ tableName + "'");
			ResultSet resultSet = prepareStatement.executeQuery();

			List<ColumnModel> rows = new ArrayList<>();

			while (resultSet.next()) {
				rows.add(new ColumnModel(resultSet.getString(1), resultSet
						.getString(2),resultSet
						.getString(3)));
			}
			conn.clearWarnings();

			System.out.println("代码生成中...");
			doGenerate(rows, tableName, superPackageName, types, mapperTemplate);
		}
	}

	private void doGenerate(List<ColumnModel> rows, String tableName,
			String superPackageName, Properties types, String mapperTemplate)
			throws Exception {
		String className = normalizeClass(tableName);
		
		BasedClassModel entityClass = new BasedClassModel(className + "Entity",
				superPackageName + ".entity", tableName);
		String entityClassFullName = entityClass.getPackageName() + "."
				+ entityClass.getClassName();
		
		BasedParamModel paramClass = new BasedParamModel(className + "Param",
				superPackageName + ".param", tableName);
		String paramClassFullName = paramClass.getPackageName() + "."
				+ paramClass.getClassName();
		
		BasedMapperModel mapperClass = new BasedMapperModel(className
				+ "Mapper", superPackageName + ".mapper",
				entityClass.getClassName(), entityClassFullName, tableName, paramClass.getClassName(), paramClassFullName);
		String mapperClassFullName = mapperClass.getPackageName() + "."
				+ mapperClass.getClassName();

		StringBuilder resultMap = new StringBuilder(300);
		StringBuilder updateSet = new StringBuilder(300);
		StringBuilder allColumns = new StringBuilder(200);
		
		StringBuilder insertContent = new StringBuilder(200);
		insertContent.append("NULL, now(), now(), 200");
		
		StringBuilder whereSet = new StringBuilder(300);
		
		boolean hasVersion = false;
		boolean hasUpdateDate = false;
		
		for (ColumnModel mm : rows) {
			String columnName = mm.getColumnName();
			
			if(!hasVersion && "version".equals(columnName.toLowerCase())){
				hasVersion = true;
			}
			
			if(!hasUpdateDate && ("update_date".equals(columnName.toLowerCase()) || "updateDate".equals(columnName.toLowerCase()))){
				hasUpdateDate = true;
			}
			
			String columnType = mm.getColumnType();
			String comment = mm.getComment();
			String type = types.getProperty(columnType);
			String name = normalizeName(columnName);
			
			FieldModel field = new FieldModel(type, name, columnName, comment);
			MethodModel setMethod = new MethodModel(type,
					normalizeSetMethod(name), name, false);
			MethodModel getMethod = new MethodModel(type,
					normalizeGetMethod(name), name, true);
			
			resultMap.append("\t\t<result column=\"" + columnName
					+ "\" property=\"" + name + "\" />\n");

			allColumns.append("," + columnName);
			
			whereSet.append("\t\t\t<if test=\""+name+" != null \">and "+columnName+"=#{"+name+"}"+"</if>\n");
			
			if("id".equals(name) || "createDate".equals(name) || "updateDate".equals(name) || "version".equals(name)){
				continue;
			}
			
	    entityClass.addField(field);
      entityClass.addMethod(getMethod);
      entityClass.addMethod(setMethod);
      
      paramClass.addField(field);
      paramClass.addMethod(getMethod);
      paramClass.addMethod(setMethod);
	      
			updateSet.append("\t\t<if test=\""+name+" != null \">,"+columnName+"=#{"+name+"}"+"</if>\n");
			
			insertContent.append(",#{"+name+"}");
		}
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("namespace", mapperClassFullName);
		map.put("resultTypeId", entityClass.getClassName());
		map.put("resultMapContent", resultMap.toString());
		map.put("all_columns", allColumns.substring(1));
		map.put("table_name", tableName);
		map.put("updateSet", updateSet.toString());
		map.put("entityFullClassName", entityClassFullName);
		map.put("insertValues", insertContent.toString());
		map.put("whereSet", whereSet.toString());
		
		String updatePrefix = "";
		if(hasUpdateDate && hasVersion){
			updatePrefix = "version=version+1, update_date=now() ";
		}else if(hasUpdateDate){
			updatePrefix = "update_date=now() ";
		}else if(hasVersion){
			updatePrefix = "version=version+1 ";
		}
		map.put("updatePrefix", updatePrefix);
		
		saveAll(entityClass, paramClass, mapperClass, className, TemplateUtil.replace(mapperTemplate, map));
	}

	private void saveAll(BasedClassModel entityClass, BasedParamModel paramClass,
			BasedMapperModel mapperClass, String tableName,
			String mapperContent) throws IOException {
		new File("output/param").mkdirs();
		new File("output/entity").mkdirs();
		new File("output/mapper").mkdirs();
		new File("output/xml").mkdirs();
		try (FileWriter fileWriter = new FileWriter("output/entity/"
				+ entityClass.getClassName() + ".java");) {
			fileWriter.write(entityClass.toString());
		}
		try (FileWriter fileWriter = new FileWriter("output/param/"
				+ paramClass.getClassName() + ".java");) {
			fileWriter.write(paramClass.toString());
		}
		try (FileWriter fileWriter = new FileWriter("output/mapper/"
				+ mapperClass.getClassName() + ".java");) {
			fileWriter.write(mapperClass.toString());
		}
		try (FileWriter fileWriter = new FileWriter("output/xml/" + tableName
				+ "Mapper.xml");) {
			fileWriter.write(mapperContent);
		}
		System.out.println("执行成功");
	}

	private String normalizeGetMethod(String name) {
		return "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	private String normalizeSetMethod(String name) {
		return "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	private String normalizeClass(String name) {
		
		String[] split = name.split("_");
		StringBuilder sb = new StringBuilder();
		for(String s : split){
			if(s.length() < 1){
				continue;
			}
			if(s.length() == 1){
				sb.append(s.toUpperCase());
			}else{
				sb.append(s.substring(0, 1).toUpperCase()).append(s.substring(1));
			}
		}
		return sb.toString();
		
//		int firstUnderscoreIndex = name.indexOf("_");
//		if(firstUnderscoreIndex == 2){
//			name = name.substring(3);
//		}
//		String normalizeName = normalizeName(name);
//		String className = normalizeName.substring(0, 1).toUpperCase()
//				+ normalizeName.substring(1);
//		return className.startsWith("T")?className:"T"+className;
//		return "UcUserLoginLog";
//		return "CmsMarketBuilding";
	}

	private String normalizeName(String name) {
		int indexOf = name.indexOf("_");
		if (indexOf == -1) {
			return name;
		}
		// 最后一个是 _
		if (name.length() == indexOf + 1) {
			return name.substring(0, indexOf);
		}
		if (indexOf == 0) {
			return normalizeName(name.substring(1));
		}
		if (name.length() == indexOf + 2) {
			return name.substring(0, indexOf)
					+ name.substring(indexOf + 1).toUpperCase();
		}
		name = name.substring(0, indexOf)
				+ name.substring(indexOf + 1, indexOf + 2).toUpperCase()
				+ name.substring(indexOf + 2);

		return normalizeName(name);
	}

}
