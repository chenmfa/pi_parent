package com.pi.common.http.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {
  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {

      RestTemplate template = restTemplateBuilder.build();

      template.setRequestFactory(clientHttpRequestFactory());

//     template.setErrorHandler(errorHandler);

      return template;
  }
  
  private ClientHttpRequestFactory clientHttpRequestFactory() {
    //1.httpurlconnection
//    SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
    //2.httpclient
//    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
//    factory.setReadTimeout(2000);
//    factory.setConnectTimeout(2000);
    //3.Okhttp3
    OkHttp3ClientHttpRequestFactory factory = new OkHttp3ClientHttpRequestFactory();
    factory.setConnectTimeout(2000);
    factory.setReadTimeout(2000);
    //4.netty4
//    Netty4ClientHttpRequestFactory factory=new Netty4ClientHttpRequestFactory();
    return factory;
}
}
