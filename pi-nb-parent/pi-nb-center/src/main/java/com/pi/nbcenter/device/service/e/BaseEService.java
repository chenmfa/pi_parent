package com.pi.nbcenter.device.service.e;

import com.pi.base.util.http.HttpClientUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lusj
 * @version V1.0
 * @Type BaseEService
 * @Desc
 * @date 2018/6/25
 */
@Service("baseEService")
public class BaseEService {

    private   Log logger = LogFactory.getLog(BaseEService.class);

    @Autowired
    private HttpClientUtil httpClientUtil;


    @Value("${wisdom.e.device.access.token.url}")
    private String  wisdomAccessTokenUrl;



    @Value("${wisdom.e.device.appid}")
    private String  wisdomDeviceAppId;


    @Value("${wisdom.e.device.secret}")
    private String  wisdomDeviceSecret;

    @Value("${wisdom.e.device.post.url}")
    private String  wisdomDevicePostUrl;


    /**
     *  获取 accessToken
     *
     */
    public String  getAccessToken() {

        Map<String, Object> params = new HashMap<>();
        params.put("appId", wisdomDeviceAppId);
        params.put("secret", wisdomDeviceSecret);
        String  accessToken =  httpClientUtil.httpClientGet(wisdomAccessTokenUrl,String.class ,params);
        logger.info("获取accessToken成功,accessToken= "+ accessToken);
        return  accessToken;
    }



    /**
     * get请求拼装参数
     * @param codeUrl
     * @return
     */

    public String commonGetMethod(String codeUrl){
        String url = wisdomDevicePostUrl + getAccessToken();
        return httpClientUtil.getGetMethod( url + codeUrl);

    }


    /**
     * Post请求拼装参数
     * @param codeUrl
     * @param params
     * @return
     */
    public String commonPostMethod(String codeUrl,Map<String, Object> params){
        String url = wisdomDevicePostUrl + getAccessToken();
        return httpClientUtil.getHttpClientPost( url + codeUrl,   params);

    }


    /**
     * Post请求拼装参数
     * @param codeUrl
     * @param params
     * @return
     */
    public <T> T commonPostMultiValueMethod(String codeUrl, Class<T> clazz,MultiValueMap<String, Object> params){
        String url = wisdomDevicePostUrl + getAccessToken();
        return httpClientUtil.httpClientPost( url + codeUrl,null, clazz, params);

    }

}
