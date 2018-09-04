package com.pi.automation.db.generator.model;

public class FieldModel {

	private String type;
	private String name;
	private String columnName;
	private String comment;
	public FieldModel(String type, String name, String columnName, String comment) {
		super();
		this.type = type;
		this.name = name;
		this.columnName = columnName;
		this.comment = comment;
	}
	
	public String getComment() {
		return comment;
	}
	
	public String getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
	
	public String getColumnName() {
		return columnName;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(80);
		sb.append("\tprivate "+type);
		for(int i=1; i<19-type.length(); i++) {
			sb.append(" ");//为了使文字排版保持一致
		}
		sb.append(name+";");
		
		for(int i=1; i<20-name.length(); i++) {
			sb.append(" ");
		}
		sb.append("// "+comment+" \n");
		return sb.toString();
	}
	
}
