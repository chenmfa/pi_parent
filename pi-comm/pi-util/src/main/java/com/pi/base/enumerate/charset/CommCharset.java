package com.pi.base.enumerate.charset;
/** 
* @author chenmfa
* @version 创建时间：2017年8月15日 下午6:28:18 
* @description
*/
public enum CommCharset {
  UTF_8("UTF-8");
  //消息大类
  private String charsetName;
  private CommCharset(String charsetName){
    this.charsetName = charsetName;
  }
  public static CommCharset getMessageType(String charsetName){
    if(null != CommCharset.values() && null != charsetName ){
      for(CommCharset charset: CommCharset.values()){
        if(charset.getValue() == charsetName){
          return charset;
        }
      }
    }
    return null;
  }
  public String getValue(){
    return this.charsetName;
  }
}
