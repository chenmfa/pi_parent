package com.pi.base.enumerate.file;

public enum FileSource {
  USER_PIC(10, 1, "用户头像"),
  ;
  private int code;
  private int viewId;
  private String desc;
  private FileSource(int code, int viewId, String desc) {
    this.code = code;
    this.desc = desc;
    this.viewId = viewId;
  }
  public int getCode() {
    return code;
  }
  public String getDesc() {
    return desc;
  }
  public int getViewId() {
    return viewId;
  }
  public static FileSource getFileSourceBySource(Integer sourceId){
    if(null != sourceId)
      for(FileSource source: FileSource.values()){
        if(source.getCode() == sourceId)
          return source;
      }
    return null;
  }
  public static FileSource getFileSourceByView(Integer viewId){
    if(null != viewId)
      for(FileSource source: FileSource.values()){
        if(source.getViewId() == viewId)
          return source;
      }
    return null;
  }
  
  public static FileSource transSourceByView(Integer viewId){
    if(null != viewId)
      for(FileSource source: FileSource.values()){
        if(source.getViewId() == viewId)
          return source;
      }
    return null;
  }
  public static FileSource transSourceBySource(Integer sourceId){
    if(null != sourceId)
      for(FileSource source: FileSource.values()){
        if(source.getCode() == sourceId)
          return source;
      }
    return null;
  } 
}
