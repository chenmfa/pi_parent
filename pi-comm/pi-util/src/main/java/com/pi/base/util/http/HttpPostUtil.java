package com.pi.base.util.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pi.base.util.bean.BeanUtil;

/**
 * HttpPost工具类
 * 
 * @author DongJun
 * 
 */
public class HttpPostUtil {
  private static final Logger logger = LoggerFactory.getLogger(HttpPostUtil.class);
  
  private static  String CONTENT_TYPE_JSON="application/json; charset=utf-8";
  private static String CONTENT_TYPE_PLAIN="application/x-www-form-urlencoded; charset=utf-8";
  @SuppressWarnings("unused")
  private static String CONTENT_TYPE_HTML="text/html; charset=utf-8";
  
  public static String JSON_CONTENT_TYPE="JSON";
  public static String TEXT_CONTENT_TYPE="TEXT";
  
  public static String DEFAULT_CHARSET= "UTF-8";

	/**
	 * 以Post方式向web接口请求数据,编码为utf-8
	 * 
	 * 超时时间5s,编码UTF8
	 * 
	 * @param actionUrl
	 * @param params
	 * @return
	 * @throws InvalidParameterException
	 * @throws NetWorkException
	 * @throws IOException
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws Exception
	 */
	public static String post(String url, Object params)
			throws IOException, IllegalArgumentException, IllegalAccessException {
		return post(url, params,5 * 1000);
	}
	
	/**
	 * 以Post方式向web接口请求数据,编码为utf-8
	 * 
	 * 编码UTF8
	 * 
	 * @param actionUrl
	 * @param params
	 * @param timeout 超时时间  毫秒
	 * @return
	 * @throws InvalidParameterException
	 * @throws NetWorkException
	 * @throws IOException
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws Exception
	 */
  public static String post(String url, Object params,int timeout)
			throws IOException, IllegalArgumentException, IllegalAccessException {
		if (url == null || url.equals(""))
			throw new IllegalArgumentException("url 为空");
		if (params == null)
			throw new IllegalArgumentException("params 参数为空");
		String sb2 = "";
		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";
		// try {
		URL uri = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		// 设置超时
		conn.setConnectTimeout(timeout);
		conn.setReadTimeout(timeout);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Charsert", "UTF-8");
		conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
				+ ";boundary=" + BOUNDARY);

		StringBuilder sb = new StringBuilder();
		Map<String,Object> mapParams = BeanUtil.objectToMap(params);
		for (Map.Entry<String, Object> entry : mapParams.entrySet()) {
			sb.append(PREFIX);
			sb.append(BOUNDARY);
			sb.append(LINEND);
			sb.append("Content-Disposition: form-data; name=\""
					+ entry.getKey() + "\"" + LINEND);
			sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
			sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
			sb.append(LINEND);
			sb.append(entry.getValue());
			sb.append(LINEND);
		}

		DataOutputStream outStream = new DataOutputStream(
				conn.getOutputStream());
		outStream.write(sb.toString().getBytes("UTF-8"));
		InputStream in = null;

		byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
		outStream.write(end_data);
		outStream.flush();

		int res = conn.getResponseCode();
		if (res == 200) {
			in = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in,
					"UTF-8"));
			String line = "";
			for (line = br.readLine(); line != null; line = br.readLine()) {
				sb2 = sb2 + line;
			}
		}
		outStream.close();
		conn.disconnect();
		return sb2;
	}

 
  /**
   * @description 采用httpclient的默认提交post请求
   * @return 返回字符串
   * @throws IllegalAccessException HttpException IOException
   */ 
  public static String postByHttpClient(String url, Object params) 
          throws HttpException, IOException, IllegalAccessException{
    return postByHttpClient(url, params, CONTENT_TYPE_PLAIN);
  }
   
  /**
   * @description 采用httpclient的默认提交post请求
   * @return 返回字符串
   * @throws IllegalAccessException HttpException IOException
   */ 
  public static String postByHttpClient(String url, Object params,String contenType) 
          throws HttpException, IOException, IllegalAccessException{
    return postByHttpClient(url, params, 1024, 1024, contenType, DEFAULT_CHARSET);
  }
  /**
   * @description 采用httpclient的默认提交post请求
   * @return 返回字符串
   * @throws IllegalAccessException HttpException IOException
   */ 
  public static String postByHttpClient(String url, Object params,NameValuePair[] valuPairs) 
          throws HttpException, IOException, IllegalAccessException{
    return postByHttpClient(url, params, valuPairs, 1024, 1024, CONTENT_TYPE_PLAIN, DEFAULT_CHARSET);
  }
  
  /**
   * @description 采用httpclient的默认提交post请求
   * @return 返回字符串
   * @throws IllegalAccessException HttpException IOException
   */ 
  public static String postByHttpClient(String url, Object params,NameValuePair[] valuPairs,String ContentType) 
          throws HttpException, IOException, IllegalAccessException{
    return postByHttpClient(url, params,valuPairs, 1024, 1024, ContentType, DEFAULT_CHARSET);
  }
  
  /**
   * @description 采用httpclient的默认提交post请求
   * @return 返回字符串
   * @throws IllegalAccessException HttpException IOException
   */ 
  public static InputStream postViaHttpClient(String url, Object params, NameValuePair[] valuPairs,String ContentType) 
          throws HttpException, IOException, IllegalAccessException{
    return postViaHttpClient(url, params,valuPairs, 1024, 1024, ContentType, DEFAULT_CHARSET);
  }
  
  /**
   * @description 采用httpclient的默认提交post请求
   * @return 返回字符串
   * @throws IllegalAccessException HttpException IOException
   */
  public static String postByHttpClient(String url, Object params, 
      int initialBufferSize, int initialCacheSize, String charset) 
          throws HttpException, IOException, IllegalAccessException{
    return postByHttpClient(url, params, null, initialBufferSize, initialCacheSize, CONTENT_TYPE_PLAIN, charset);
  }
  /**
   * @description 采用httpclient的默认提交post请求
   * @return 返回字符串
   * @throws IllegalAccessException HttpException IOException
   */
  public static String postByHttpClient(String url, Object params, 
      int initialBufferSize, int initialCacheSize, String contentType, String charset) 
          throws HttpException, IOException, IllegalAccessException{
    return postByHttpClient(url, params, null, initialBufferSize, initialCacheSize, contentType, charset);
  }
	 /**
   * @description 采用httpclient的默认提交post请求
   * @return 返回字符串
	 * @throws IllegalAccessException HttpException IOException 
   */
  public static String postByHttpClient(String url, Object params, NameValuePair[] valuPairs,
      int initialBufferSize, int initialCacheSize, String contentType, String charset) 
          throws HttpException, IOException, IllegalAccessException{
    if(StringUtils.isNotBlank(url)){
      PostMethod post = new PostMethod(url);
      if(JSON_CONTENT_TYPE.equalsIgnoreCase(contentType)){
        post.setRequestHeader("Content-Type",CONTENT_TYPE_JSON);
        post.addRequestHeader("Accept", CONTENT_TYPE_JSON);
        if(null != params){
          String requestData = null;
          if(null == params || params instanceof String){
            requestData = (String)params;
          }else{            
            JSONObject j = new JSONObject();
            for(Map.Entry<String, Object> entry:BeanUtil.objectToMap(params).entrySet()){            
              j.put(entry.getKey(), entry.getValue()); 
            }
            requestData = JSON.toJSONString(params);
          }
          StringRequestEntity entity = new StringRequestEntity(requestData,CONTENT_TYPE_JSON,DEFAULT_CHARSET);//解决中文乱码问题    
          post.setRequestEntity(entity);
        }
      }else{
        if(null != params){
          for(Map.Entry<String, Object> entry: BeanUtil.objectToMap(params).entrySet()){        
            post.addParameter(entry.getKey(), (null != entry.getValue())?entry.getValue().toString():"");
          }
        }
        post.setRequestHeader("Content-Type",CONTENT_TYPE_PLAIN);
        post.addRequestHeader("Accept", CONTENT_TYPE_PLAIN);
      }
      post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,charset);
      post.getParams().setParameter(HttpMethodParams.HTTP_ELEMENT_CHARSET,charset);
      return execute(post,valuPairs,initialBufferSize, initialCacheSize,charset);
    }else{
      logger.error("url为空");
      throw new IllegalAccessException("url为空");
    }
  }
  
  /**
   * @description 采用httpclient的默认提交post请求
   * @return 返回字符串
   * @throws IllegalAccessException HttpException IOException 
   */
  public static InputStream postViaHttpClient(String url, Object params, NameValuePair[] valuPairs,
      int initialBufferSize, int initialCacheSize, String contentType, String charset) 
          throws HttpException, IOException, IllegalAccessException{
    if(StringUtils.isNotBlank(url)){
      HttpMethod method = wrapPost(url, params, contentType, charset);
      return getResponse(method,valuPairs,initialBufferSize, initialCacheSize,charset);
    }else{
      logger.error("url为空");
      throw new IllegalAccessException("url为空");
    }
  }
  
  private static HttpMethod wrapPost(String url, Object params,String contentType, String charset) 
      throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException{
    logger.info("开始请求地址:{}",url);
    PostMethod post = new PostMethod(url);
    if(JSON_CONTENT_TYPE.equalsIgnoreCase(contentType)){
      post.setRequestHeader("Content-Type",CONTENT_TYPE_JSON);
      post.addRequestHeader("Accept", CONTENT_TYPE_JSON);
      if(null != params){
        StringRequestEntity entity = new StringRequestEntity(JSON.toJSONString(params),CONTENT_TYPE_JSON,DEFAULT_CHARSET);//解决中文乱码问题    
        post.setRequestEntity(entity);
      }
    }else{
      if(null != params){
        for(Map.Entry<String, Object> entry: BeanUtil.objectToMap(params).entrySet()){        
          post.addParameter(entry.getKey(), (null != entry.getValue())?entry.getValue().toString():"");
        }
      }
      post.setRequestHeader("Content-Type",CONTENT_TYPE_PLAIN);
      post.addRequestHeader("Accept", CONTENT_TYPE_PLAIN);
    }
    post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,charset);
    post.getParams().setParameter(HttpMethodParams.HTTP_ELEMENT_CHARSET,charset);
    return post;
  }
  /**
   * @description 执行http请求
   * @param method
   * @param valuPairs
   * @param initialBufferSize
   * @param initialCacheSize
   * @param charset
   * @return
   * @throws HttpException
   * @throws IOException
   */
	public static String execute(HttpMethod method,NameValuePair[] valuPairs,int initialBufferSize,
	    int initialCacheSize, String charset) throws HttpException, IOException{
    logger.info("开始请求地址:{}",method.getURI());
	  InputStream response = getResponse(method, valuPairs, initialBufferSize, initialCacheSize, charset);
    ByteBuffer byteBuffer = ByteBuffer.allocate(initialBufferSize);
    byte[] cache = new byte[initialCacheSize];
    int i = 0;
    int readBytes = 0;
    while((i = response.read(cache)) !=-1){
      if(byteBuffer.remaining()<i){
        byteBuffer.flip();
        ByteBuffer original = byteBuffer.slice();
        byteBuffer = ByteBuffer.allocate(byteBuffer.capacity()*2);
        byteBuffer.put(original);
      }
      byteBuffer.put(cache,0,i);
      readBytes = readBytes+i;
    }
    byteBuffer.flip();
    cache = new byte[readBytes];
    byteBuffer.get(cache);
    method.releaseConnection();
    return new String(cache,charset);
	}
	
	/**
	 * @description 获取请求的 返回流对象
	 * @param method
	 * @param valuPairs
	 * @param initialBufferSize
	 * @param initialCacheSize
	 * @param charset
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static InputStream getResponse(HttpMethod method,NameValuePair[] valuPairs, Map<String,String> headers, int initialBufferSize,
      int initialCacheSize, String charset) throws HttpException, IOException{
    HttpClient httpclient = new HttpClient();
    if(null != valuPairs){
      method.setQueryString(valuPairs);
    }
    if(null != headers){
    	for(Map.Entry<String,String> entry:headers.entrySet()){    		
    		method.addRequestHeader(entry.getKey(), entry.getValue());
    	}
    }
    httpclient.executeMethod(method);
    InputStream response = method.getResponseBodyAsStream();    
    return response;
  }
	
	/**
	 * @description 获取请求的 返回流对象
	 * @param method
	 * @param valuPairs
	 * @param initialBufferSize
	 * @param initialCacheSize
	 * @param charset
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static InputStream getResponse(HttpMethod method,NameValuePair[] valuPairs, 
			int initialBufferSize, int initialCacheSize, String charset) 
					throws HttpException, IOException{
		return getResponse(method, valuPairs, null,initialBufferSize, initialCacheSize, charset);
	}
	
  /**
   * @description Get方式的请求数据
   * @param url
   * @param paramMap
   * @param initialBufferSize
   * @param initialCacheSize
   * @param charset
   * @return
   * @throws HttpException
   * @throws IOException
   */
	public static String getByHttpClient(String url, Map<String, String> paramMap,
	    int initialBufferSize, int initialCacheSize, String charset)
	        throws HttpException, IOException{
	  HttpMethodParams params = new HttpMethodParams();
	  GetMethod get = new GetMethod(url);
    if(null != paramMap){
      for(Map.Entry<String, String> entry: paramMap.entrySet()){        
        params.setParameter(entry.getKey(), entry.getValue());
      }
    }
	  get.setParams(params);
    get.releaseConnection();  
    return execute(get,null,initialBufferSize, initialCacheSize,charset); 
	}
	 public static String getByHttpClient(String url, Map<String, String> paramMap)
	          throws HttpException, IOException{
	    return getByHttpClient(url,paramMap, 1024, 1024, "UTF-8"); 
	  }
}
