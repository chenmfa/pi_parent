package com.pi.nbcenter.util.ctcc.control;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.pi.base.util.http.HttpClientUtil;
import com.pi.base.util.http.HttpPostUtil;
import com.pi.nbcenter.util.ctcc.server.CTCCConfigV1;
import com.pi.nbcenter.util.ctcc.server.CTCCServerV1;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Component
public class DeviceCommand {
  @Autowired
  private HttpClientUtil httpClient;
  
  private static final String URL_STUFFING_PRODUCT_KEY = "{product_key}";
  
  @Test
  public void getToken() throws Exception{
    MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
    bodyMap.add("enterprise_id", CTCCConfigV1.enterpriseId);
    bodyMap.add("enterprise_secret", CTCCConfigV1.enterpriseSecret);
    bodyMap.add("product_secret", CTCCConfigV1.productSecret);
    Map<String, Object> parmaMap = new LinkedHashMap<>();
    parmaMap.put("enterprise_id", CTCCConfigV1.enterpriseId);
    parmaMap.put("enterprise_secret", CTCCConfigV1.enterpriseSecret);
    parmaMap.put("product_secret", CTCCConfigV1.productSecret);
    String response = httpClient.getHttpClientPost(
        CTCCServerV1.TOKEN_URL.replace(URL_STUFFING_PRODUCT_KEY, CTCCConfigV1.productKey), parmaMap);
    String res = HttpPostUtil.postByHttpClient(CTCCServerV1.TOKEN_URL.replace(URL_STUFFING_PRODUCT_KEY, CTCCConfigV1.productKey), parmaMap, HttpPostUtil.JSON_CONTENT_TYPE);
    String result = httpClient.httpClientPost(
        CTCCServerV1.TOKEN_URL.replace(URL_STUFFING_PRODUCT_KEY, CTCCConfigV1.productKey), null, String.class, bodyMap);
    
    System.out.println(result);
    System.out.println(response);
    System.out.println(res);
  }
}
