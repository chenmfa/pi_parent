package com.pi.base.util.http.v2;

import java.io.IOException;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.PrivateKeyDetails;
import org.apache.http.ssl.PrivateKeyStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.pi.base.enumerate.charset.CommCharset;
import com.pi.base.util.http.v2.component.SimpleHostnameVerifier;
import com.pi.base.util.http.v2.component.TrustAllManager;

public class HttpPostUtil {
	private static final Logger logger = LoggerFactory.getLogger(HttpPostUtil.class);
	
	public static <T>T getRestResult(
	    String url, Map<String, String> paramMap, Class<T> clz) throws Exception{
	  String response = post(url, paramMap);
	  if(null == response){
	    return null;
	  }
	  logger.debug("获取到原始数据： {}", response);
	  return JSON.parseObject(response, clz);
	}
	
  public static String postJSON(String url, Map<String, String> paramMap)
      throws KeyManagementException, UnrecoverableKeyException, 
      ClientProtocolException, NoSuchAlgorithmException, KeyStoreException, IOException{
    StringEntity postEntity = new StringEntity(JSON.toJSONString(paramMap));
    postEntity.setContentEncoding("UTF-8");
    postEntity.setContentType("application/json");//发送json数据需要设置contentType
    return post(url, postEntity);
  }
  
  public static String post(String url, Map<String, String> paramMap)
      throws KeyManagementException, UnrecoverableKeyException, 
      ClientProtocolException, NoSuchAlgorithmException, KeyStoreException, IOException{
    logger.debug("请求地址: {}, 请求参数：{}", url, paramMap);
    List<NameValuePair> pairs = new ArrayList<NameValuePair>();
    wrapParam(pairs, paramMap);
    HttpEntity postEntity = new UrlEncodedFormEntity(pairs, CommCharset.UTF_8.getValue());
    return post(url, postEntity);
  }
  
	public static String post(String url, HttpEntity postEntity) 
			throws ClientProtocolException, IOException, KeyManagementException,
			UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException{
		
		CloseableHttpClient closeableClient = createHttpsClient();
		CloseableHttpResponse response = null;
		try{		  
		  HttpPost post = new HttpPost(url);
		  post.setEntity(postEntity);
		  response = closeableClient.execute(post);
		  if(null != response && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
		    HttpEntity entity = response.getEntity();
		    return  null != entity? EntityUtils.toString(entity, CommCharset.UTF_8.getValue()) : null;
		  }
		  return null;
		}finally{
		  closeableClient.close();
		  if(null != response){
		    response.close();
		  }
		}
	}
	
	public static CloseableHttpClient createHttpsClient() 
			throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException{
		SSLContext context = new SSLContextBuilder().loadKeyMaterial(null, null, new PrivateKeyStrategy() {
			@Override
			public String chooseAlias(Map<String, PrivateKeyDetails> aliases, Socket socket) {
				if(null != aliases){
					for(Map.Entry<String, PrivateKeyDetails> aliase: aliases.entrySet()){
						logger.info("别名：{}, 内容详情：{}",  aliase.getKey(), aliase.getValue().toString());
					}
				}
				return null;
			}
		}).build();
		context.init(null, new TrustManager[]{new TrustAllManager()}, new SecureRandom());
		// 设置协议http和https对应的处理socket链接工厂的对象  
    Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()  
        .register("http", PlainConnectionSocketFactory.INSTANCE)  
        .register("https", new SSLConnectionSocketFactory(context, new SimpleHostnameVerifier(PublicSuffixMatcherLoader.getDefault())))  
        .build();  //new DefaultHostnameVerifier
    PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		return HttpClients.custom().setConnectionManager(connManager).build();
	}
	
	private static void wrapParam(List<NameValuePair> pairs, Map<String, String> paramMap){
	  if(null != paramMap){
      for(Map.Entry<String, String> entry:paramMap.entrySet())
        pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
    }
	}
}
