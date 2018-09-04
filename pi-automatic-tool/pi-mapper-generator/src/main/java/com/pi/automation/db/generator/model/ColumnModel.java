package com.pi.automation.db.generator.model;

public class ColumnModel {

	private String columnName;
	private String columnType;
	private String comment;

	public ColumnModel(String columnName, String columnType, String comment) {
		super();
		this.columnName = columnName;
		this.columnType = columnType;
		this.comment = comment;
	}
	
	public String getComment() {
		return comment;
	}

	public String getColumnName() {
		return columnName;
	}

	public String getColumnType() {
		return columnType;
	}
}
