package com.pi.nbcenter.base.errorcode;

/**
 * @description 错误码模块定义
 * @description 错误码格式: 100101
 * @description 第一位: (1)错误类型(服务端错误级别, 1为需要显示的, 2为不需要显示的)
 * @description 第二位: (001)模块类型
 * @description 第三位 : (01)错误码编号
 * @author chenmfa
 */
public enum ErrorDefine {
  DEFINE_DEV_INFO(1, 1, "ErrorIOTDev", "设备信息"),
  DEFINE_PARTNER(1, 2, "ErrorPartner", "合作商信息"),
  DEFINE_DEV_Center_Platform(1, 3, "ErrorCenterPlatform", "设备中心平台信息"),
  DEFINE_PARTNER_SUBSCRIBE(1, 4, "ErrorPartnerSubscribe", "合作商订阅"),
  
  DEFINE_DEV_PLATFORM_REG(2, 1, "ErrorIOTDev", "设备信息"),
  ;
  private int source;//错误码来源 1.系统内部 2.平台
  private int code;//编号
  private String defName;//对应名称(暂时用自定义的错误类表示)
  private String desc;//描述
  private ErrorDefine(int source, int code, String defName, String desc) {
    this.source = source;
    this.code = code;
    this.desc = desc;
    this.defName = defName;
  }
  public int getCode() {
    return code;
  }
  public String getDesc() {
    return desc;
  }
  public int getSource() {
    return source;
  }
  public String getDefName() {
    return defName;
  }
}
