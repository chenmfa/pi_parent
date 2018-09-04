package com.pi.base.dto.result;

import com.pi.base.dto.result.respcode.CommonResponse;

public class AppResult{
  
  public static final int SUCCESS = 1;
  public static final int FAIL = 0;
  
	public static AppResult OK = new AppResult(SUCCESS, CommonResponse.SUCCESS.getKey(), 0);
	
	public static final String INVALID_REQUEST = "REQ_COMM.INVALID_REQUEST";
	
	private int status;
	private String msg;
	private Object data;
	private int errorCode;
	
	public AppResult(){
	}
	
	public AppResult(int status, String msg) {
	  this(status, msg, null, 0);
	}
	
	public AppResult(int status, String msg, int errorCode) {
	  this(status, msg, null, errorCode);
  }
	
	public AppResult(int status, String msg, Object data) {
		this(status, msg, data, 0);
	}
	
	public AppResult(int status, String msg, Object data, int errorCode) {
    this.status = status;
    this.msg = msg;
    this.data = data;
    this.errorCode = errorCode;
  }
	
	public static AppResult newFailResult(String error){
	  return newFailResult(error, null, 0);
	}
  public static AppResult newFailResult(String error, int errorCode){
    return newFailResult(error, null, errorCode);
  }	
	
	public static AppResult newFailResult(String error,Object data){
	  return newFailResult(error, data, 0);
	}
  public static AppResult newFailResult(String error,Object data,int errorCode){
    return new AppResult(FAIL, error, data, errorCode);
  }
	
	public static AppResult newFailResult(Exception error){
    	return newFailResult(error.getLocalizedMessage());
	}
    
  public static AppResult newFailResult(Exception error,Object data){
    	return newFailResult(error.getLocalizedMessage(),data);
	}
  public static AppResult newFailResult(Exception error,Object data, int errorCode){
    return newFailResult(error.getLocalizedMessage(),data,errorCode);
  }  
  public static AppResult newInvalidRequest(){
      return new AppResult(FAIL,INVALID_REQUEST,"");
  }
  public static AppResult newInvalidRequest(Object data){
    return new AppResult(FAIL,INVALID_REQUEST,data);
  }
    
  public static AppResult newSuccessResult(Object data){
    	return newSuccessResult(CommonResponse.SUCCESS.getKey(),data);
	}
	
  public static AppResult newSuccessResult(String mess,Object data){
    	return new AppResult(SUCCESS,mess,data);
	}
  /**
   * @description 判断是否成功
   * @return
   */
  public boolean equalsSuccess(){
    return this.getStatus() == SUCCESS;
  }
  /**
   * @description 判断是否失败
   * @return
   */
  public boolean equalsFail(){
    return this.getStatus() == FAIL;
  }
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
  public int getErrorCode() {
    return errorCode;
  }
  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }
  @Override
  public String toString() {
    return "AppResult [status=" + status + ", msg=" + msg + ", data="
        + data + ",errorCode=" + errorCode + "]";
  }
}
