package com.pi.base.contants;

public class WxConstants {
  public static final String WCHAT_OPENID_PATTERN = 
      "https://api.weixin.qq.com/sns/oauth2/access_token?appid={0}&secret={1}&code={2}&grant_type=authorization_code";
  
  public static final String WCHAT_USER_INFO_PATTERN =
      "https://api.weixin.qq.com/sns/userinfo?access_token={0}&openid={1}&lang=zh_CN";
  
  public static final String WCHAT_REFRESH_TOKEN_PATTERN = 
      "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid={0}&grant_type=refresh_token&refresh_token={1}";
  
  public static final String WCHAT_APP_TOKEN_URL =
      "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";
  public static final String WCHAT_MINI_QRCODE_URL = 
      "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token={0}";
  public static final int DEFAULT_WECHAT_TOKEN_EXPIRED = 2592000;
}

