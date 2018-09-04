package com.pi.nbcenter.util.ctcc.server;

/**
 * @description 电信服务地址类
 * @author chenmfa
 */
public class CTCCServerV1 {
  public static final String TOKEN_URL = "http://enterpriseapi.gizwits.com/v1/products/{product_key}/access_token";
  public static final String DEV_BIND_URL = "http://enterpriseapi.gizwits.com/v1/products/{product_key}/devices/bindings";
  public static final String DEV_UNBIND_URL = "http://enterpriseapi.gizwits.com/v1/products/{product_key}/devices/bindings";
  public static final String DEV_DID_URL = "http://enterpriseapi.gizwits.com/v1/products/{product_key}/devices";
  public static final String DEV_DETAIL_URL = "http://enterpriseapi.gizwits.com/v1/products/{product_key}/device_detail";
  public static final String DEV_SEARCH_URL = "http://enterpriseapi.gizwits.com/v1/products/{product_key}/devices/search";
  public static final String DEV_CONTROL_URL = "http://enterpriseapi.gizwits.com/v1/products/{product_key}/devices/{did}/control";
  public static final String DEV_ONLINE_RECORD_URL = "http://enterpriseapi.gizwits.com/v1/products/{product_key}/devices/{did}/online";
  public static final String DEV_HISTORIC_DATA_URL = "http://enterpriseapi.gizwits.com/v1/products/{product_key}/devices/{did}/data";
  public static final String DEV_HISTORIC_CMD_URL = "http://enterpriseapi.gizwits.com/v1/products/{product_key}/devices/{did}/cmd";
}
