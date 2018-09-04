package com.pi.base.exception;

public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = -5979086874856666302L;
  private int code;
	private String message;  //异常对应的描述信息
	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
	  super(message);
		this.message = message;
	}
	public ServiceException(String message,int code) {
    super(message);
    this.message = message;
    this.code = code;
  }
	public ServiceException(String message,Throwable cause) {
    this(message,cause,0);
  }
  public ServiceException(String message,Throwable cause, int code) {
    super(message,cause);
    this.message = message;
    this.code = code;
  }
	public String toString(){
		return message;
	}
  public int getCode() {
    return code;
  }
  public void setCode(int code) {
    this.code = code;
  }
  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }
}
