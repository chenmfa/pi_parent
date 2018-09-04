package com.pi.automation.db.generator.model;


public class MethodModel {

	private String type;
	private String methodName;
	private boolean isGet;
	private String fieldName;

	public MethodModel(String type, String methodName, String fieldName,
			boolean isGet) {
		super();
		this.type = type;
		this.methodName = methodName;
		this.isGet = isGet;
		this.fieldName = fieldName;
	}

	public String getType() {
		return type;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	
	public String getMethodName() {
		return methodName;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(50);
		if (!isGet) {
			sb.append("\n\tpublic void " + methodName + "(");
			sb.append(type + " " + fieldName);
			sb.append(") {\n");
			sb.append("\t\tthis." + fieldName + "=" + fieldName + ";");
			sb.append("\n\t}");
		} else {
			sb.append("\n\tpublic " + type + " " + methodName + "(");
			sb.append(") {\n");
			sb.append("\t\treturn this." + fieldName + ";");
			sb.append("\n\t}");
		}
		sb.append("\n");
		return sb.toString();
	}

}
