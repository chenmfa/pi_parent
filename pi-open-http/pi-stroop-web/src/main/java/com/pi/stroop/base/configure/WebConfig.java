package com.pi.stroop.base.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.pi.stroop.base.interceptor.AuthInterceptor;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{
  
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    InterceptorRegistration addedInterceptor = registry.addInterceptor(getAuthInterceptor());
 // 排除配置
    addedInterceptor.excludePathPatterns("/weChatBind**");
    
    // 拦截配置
    addedInterceptor.addPathPatterns("/**");
  }
  
  @Bean
  public AuthInterceptor getAuthInterceptor(){
    return new AuthInterceptor();
  }
}
