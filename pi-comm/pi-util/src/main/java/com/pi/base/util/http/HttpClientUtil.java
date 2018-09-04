package com.pi.base.util.http;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * @author lusj
 * @version V1.0
 * @Type HttpClientUtil
 * @Desc
 * @date 2018/6/24
 */
@Component
public class HttpClientUtil {

  private static final Log logger = LogFactory.getLog(HttpClientUtil.class);

  @Autowired
  private RestTemplate restTemplate;


  public  String getGetMethod(String url) {
      CloseableHttpClient httpclient = HttpClientBuilder.create().build();
  
      logger.info("搜索url=" + url);
      url = url.replaceAll(" ", "%20");
      HttpGet httpget = new HttpGet(url);
      String json = null;
      try {
          HttpResponse response = httpclient.execute(httpget);
          org.apache.http.HttpEntity entity = response.getEntity();
          if (entity != null) {
              json = EntityUtils.toString(entity, "UTF-8").trim();
          }
      } catch (Exception e) {
          logger.error("GET请求出现异常,e=",e);
      } finally {
          httpget.abort();
      }
      return json;
  }

    /**
     * post请求
     *
     * @param url
     * @param params
     * @return
     */
    public String getHttpClientPost(String url,  Map<String, Object> params) {
        CloseableHttpClient httpClient =  HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(JSONObject.toJSONString(params), "utf-8"));
        httpPost.addHeader("content-type", "application/json");
        String result = null;
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            result = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (IOException e) {
            logger.error("POST请求出现异常,e=",e);
        }
        System.out.println("返回结果:"+result);
        return result;
    }



    /**
     *get请求
     *
     * @param url 请求地址
     * @param params  组装参数
     *  @param clazz  返回实体参数
     * @return  <T> 返回参数,
     */
    public <T> T httpClientGet(String url ,Class<T> clazz, Map<String, Object> params) {
        StringBuilder paramBuilder = new StringBuilder();
        if (params != null && params.size() > 0) {
            for (Iterator<String> iter = params.keySet().iterator(); iter.hasNext();) {
                String key = iter.next();
                Object value = params.get(key);
                if (paramBuilder.length() == 0) {
                    paramBuilder.append("?");
                } else {
                    paramBuilder.append("&");
                }
                paramBuilder.append(key).append("=").append(value);
            }
        }

        logger.info("搜索url=" + url+paramBuilder.toString());
        ResponseEntity<T>  data = restTemplate.getForEntity(url + paramBuilder.toString(), clazz, params);
        if(data.getStatusCode().equals(HttpStatus.OK)){
            return data.getBody();
        }
        logger.error("返回数据为={}" + JSONObject.toJSON(data));
        return null;

    }

    /**
     * Post请求
     * @param url 地址
     * @param request 请求参数
     * @param clazz 返回参数模板
     * @param <T>
     * @return
     */
    public <T> T httpClientPost(String url,Object request,Class<T> clazz,MultiValueMap<String, Object> params) {
        return httpClientPost(url, request, clazz, params, null);
    }

    /**
     * Post请求
     * @param url 地址
     * @param request 请求参数
     * @param clazz 返回参数模板
     * @param <T>
     * @return
     */
    public <T> T httpClientPost(String url,Object request,Class<T> clazz,MultiValueMap<String, Object> params, Map<String, String> tokenMap) {
      logger.info("url={},request={}" + url);

      HttpHeaders headers = new HttpHeaders();
      headers.set("content-type", "application/json");
      headers.setContentType(MediaType.APPLICATION_JSON);
      
      if(null != tokenMap && !tokenMap.isEmpty()){
        for(Map.Entry<String, String> entry:tokenMap.entrySet()){
          headers.set(entry.getKey(), entry.getValue());
        }
      }
      
      HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(params, headers);

      T data = restTemplate.postForObject(url,entity,clazz);
      logger.info("返回数据为={}" + JSONObject.toJSON(data));
      return data;
    }

}
