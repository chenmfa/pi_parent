package com.pi.automation.db.generator.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClassModel {

	private String className;
	private String packageName;

	private List<MethodModel> methods = new ArrayList<>();
	private List<FieldModel> fields = new ArrayList<>();
	private String tableName;

	public ClassModel(String className, String packageName, String tableName) {
		super();
		this.className = className;
		this.packageName = packageName;
		this.tableName = tableName;
	}

	public String getPackageName() {
		return packageName;
	}
	
	public String getClassName() {
		return className;
	}

	public List<MethodModel> getMethods() {
		return methods;
	}

	public List<FieldModel> getFields() {
		return fields;
	}

	public void addMethod(MethodModel me) {
		methods.add(me);
	}

	public void addField(FieldModel fe) {
		fields.add(fe);
	}

	@Override
	public String toString() {
		boolean hasDate = false;
		boolean hasBigDecimal = false;
		for(FieldModel f: fields){
			if("Date".equals(f.getType())){
				hasDate = true;
			}else if("BigDecimal".equals(f.getType())){
				hasBigDecimal = true;
			}
			
		}
		
		String comment = "/**\n"+
						" * @description 表"+tableName+"的实体类\n"+
						" * @author chenmfa\n"+
						" * @date " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +"\n"+
						" */\n";
		
		StringBuilder sb = new StringBuilder(500);
		sb.append("package " + packageName + ";\n\n");
		if(hasDate){
			sb.append("import java.util.Date;\n\n");
		}
		if(hasBigDecimal){
			sb.append("import java.math.BigDecimal;\n\n");
		}
		sb.append(comment);
		sb.append("public class " + className + "{\n");
		for (FieldModel f : fields) {
			sb.append(f.toString());
		}
		sb.append("\n");
		for (MethodModel m : methods) {
			sb.append(m.toString());
		}
		sb.append("\n}");
		return sb.toString();
	}

}
