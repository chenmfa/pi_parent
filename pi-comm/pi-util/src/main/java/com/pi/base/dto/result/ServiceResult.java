package com.pi.base.dto.result;

import com.pi.base.dto.result.respcode.CommonResponse;

/**
 * @description 内部服务或者方法调用的业务类(按照郝总的要求)
 * @author chenmfa
 *
 */
public class ServiceResult<T> {

  public static final int SUCCESS = 1;
  
  public static final int FAIL = 0;
  public static ServiceResult<String> OK = new ServiceResult<String>(SUCCESS, CommonResponse.SUCCESS.getKey(), 0);
  public static final String FORMATTER = "yyyy年MM月dd日 HH:mm";
  
  public static final String INVALID_REQUEST = "REQ_COMMON.INVALID_REQUEST";
  
  private int status;
  private String msg;
  private T data;
  private int errorCode;
  
  public ServiceResult(){}
  
  public ServiceResult(int status, String msg) {
    this(status, msg, null, 0);
  }
  
  public ServiceResult(int status, String msg, int errorCode) {
    this(status, msg, null, errorCode);
  }
  
  public ServiceResult(int status, String msg, T data) {
    this(status, msg, data, 0);
  }
  
  public ServiceResult(int status, String msg, T data, int errorCode) {
    this.status = status;
    this.msg = msg;
    this.data = data;
    this.errorCode = errorCode;
  }
  
  public static <V> ServiceResult<V> newFailResult(String error){
    return new ServiceResult<V>(FAIL, error);
  }
  public static <V> ServiceResult<V> newFailResult(String error, int errorCode){
    return new ServiceResult<V>(FAIL, error, null, errorCode);
  } 
  
  public static <V> ServiceResult<V> newFailResult(String error,V data){
    return new ServiceResult<V>(FAIL, error, data);
  }
  public static <V> ServiceResult<V> newFailResult(String error, V data,int errorCode){
    return new ServiceResult<V>(FAIL, error, data, errorCode);
  }
  
  public static <V> ServiceResult<V> newFailResult(Exception error){
    return new ServiceResult<V>(FAIL, error.getLocalizedMessage());
  }
    
  public static <V> ServiceResult<V> newFailResult(Exception error, V data){
      return new ServiceResult<V>(FAIL, error.getLocalizedMessage(), data);
  }
  public static <V> ServiceResult<V> newFailResult(Exception error, V data, int errorCode){
    return new ServiceResult<V>(FAIL, error.getLocalizedMessage(), data, errorCode);
  }  
  public static <V> ServiceResult<V> newInvalidRequest(){
      return new ServiceResult<V>(FAIL,INVALID_REQUEST);
  }
  public static <V> ServiceResult<V> newInvalidRequest(V data){
    return new ServiceResult<V>(FAIL,INVALID_REQUEST,data);
  }
    
  public static <V> ServiceResult<V> newSuccessResult(V data){
      return new ServiceResult<V>(SUCCESS, CommonResponse.SUCCESS.getKey(), data);
  }
  
  public static <V> ServiceResult<V> newSuccessResult(String mess, V data){
      return new ServiceResult<V>(SUCCESS, mess, data);
  }
 
  @Override
  public String toString() {
    return "ServiceResult [status=" + status + ", msg=" + msg + ", data="
        + data + ",errorCode=" + errorCode + "]";
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

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
  public int getErrorCode() {
    return errorCode;
  }
  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }
  /**
   * 是否成功
   * @return
   */
  public boolean equalSuccess(){
    return this.getStatus() == SUCCESS;
  }
}
