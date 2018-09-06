package com.pi.base.dto.result.respcode.error;

public enum ErrorServer{
	REQUEST_UNAVAILABLE(100000, "REQ_COMMON.REQUEST_UNAVAILABLE", "服务暂时不可用"),
	REQUEST_NO_LOGIN(100001, "REQ_COMMON.REQUEST_NO_LOGIN", "请登录后再试"),
	REQUEST_OPERATION_UNAVAILABLE(100002, "REQ_COMMON.REQUEST_OPERATION_UNAVAILABLE", "操作暂时无法完成"),
	REQUEST_DATA_UNAVAILABLE(100003, "REQ_COMMON.REQUEST_DATA_UNAVAILABLE", "数据查询暂时无法完成"),
	DUPLICATED_RECORD(100004, "REQ_COMMON.DUPLICATED_RECORD", "记录已存在"),
	UNKNOWN_PARTNER_INFO(100005, "REQ_COMMON.UNKNOWN_PARTNER_INFO", "登陆信息不完善, 需重新登录"),
	REQUEST_PARAM_INCOMPLETE(100006, "REQ_COMMON.REQUEST_PARAM_INCOMPLETE", "参数不完整"),
  SERVICE_PARAM_EMPTY(100007, "REQ_COMMON.SERVICE_PARAM_EMPTY", "参数为空"),
  REQUEST_HAS_EXPIRED(100008, "REQ_COMMON.REQUEST_HAS_EXPIRED", "会话已过期"),
  OPERATE_FAILED(100009, "REQ_COMMON.OPERATE_FAILED", "操作失败"),
  ;
	private int errorCode;
	private String tag;
	private String desc;
	private ErrorServer(int errorCode, String tag, String desc) {
		this.errorCode = errorCode;
		this.tag = tag;
		this.desc = desc;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public String getTag() {
		return tag;
	}
	public String getDesc() {
		return desc;
	}
}
