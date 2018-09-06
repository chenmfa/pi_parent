package com.pi.base.enumerate.file;

public enum FileType {
	PNG(101,"PNG");
	private int code;
	private String desc;
	private FileType(int code, String desc){
		this.code = code;
		this.desc= desc;
	}
	public int getCode() {
		return code;
	}
	public String getDesc() {
		return desc;
	}
}
