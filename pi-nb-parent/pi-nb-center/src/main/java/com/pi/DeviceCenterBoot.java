package com.pi;

import java.io.IOException;
import java.util.Map;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.pi.nbcenter.base.listener.BootInitializeListener;
import com.pi.nbcenter.device.service.base.BasePlatFormService;
import com.pi.nbcenter.util.nb.Constant;
import com.pi.nbcenter.util.nb.NbUtil;

/**
 * @description Nb基础模块 启动页, 负责nb模块的初始注册
 * @author chenmfa
 * @category "com.pi.nbcenter.controller"
 * @category "com.pi.nbcenter.service"
 * @category "com.pi.nbcenter.*"
 * @description mvn spring-boot:run -Drun.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"
 */
@SpringBootApplication
@ServletComponentScan
@ComponentScan("com.pi")
@MapperScan(basePackages={"com.pi.nbcenter.device.mapper.auto"})
public class DeviceCenterBoot {
  public static final Logger logger = LoggerFactory.getLogger(DeviceCenterBoot.class);
  public static ApplicationContext CONTEXT = null;
  @Value("${server.port}")
  private int HTTPS_PORT;
  @Value("${server.http.port}")
  private int HTTP_PORT;
  public static void main(String[] args) throws IOException {

//    {@link org.springframework.boot.context.event.ApplicationStartingEvent} springboot启动开始时执行的事件
//    {@link org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent}spring boot 对应Enviroment已经准备完毕，但此时上下文context还没有创建。
//    {@link org.springframework.boot.context.event.ApplicationFailedEvent} spring boot启动异常时执行事件 
    SpringApplication application = new SpringApplication(DeviceCenterBoot.class);
    application.addListeners((ApplicationListener<ApplicationEnvironmentPreparedEvent>) event -> {
      logger.info("----------------- Spring 环境首次初始化成功：{} -----------------------------");
    });
    application.addListeners((ApplicationListener<ApplicationStartingEvent>) event -> {
      logger.info("----------------- Spring Boot 应用正在启动：{} -----------------------------");
    });
    application.addListeners((ApplicationListener<ApplicationReadyEvent>) event -> {
      logger.info("----------------- Spring 上下文准备完毕： -----------------------------");
      try {
//        BasePlatFormService bean = 
            event.getApplicationContext().getBean(BasePlatFormService.class);
//        Map<String, Map<String, String>> platformConfigs = bean.queryAllPlatformConfig();
//        startAllSubcribe(platformConfigs);
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    application.addListeners((ApplicationListener<ApplicationFailedEvent>) event -> {
      logger.info("----------------- Spring Boot应用启动失败：" + event.getException().toString());
    });
    CONTEXT = 
        application.run(args);
  }
  @Bean
  public ServletListenerRegistrationBean<BootInitializeListener>
    servletListenerRegistrationBean(){
      ServletListenerRegistrationBean<BootInitializeListener> servletListenerRegistrationBean = 
          new ServletListenerRegistrationBean<BootInitializeListener>();
      servletListenerRegistrationBean.setListener(new BootInitializeListener());
      return servletListenerRegistrationBean;
  }
  
  @Bean
  public EmbeddedServletContainerFactory servletContainer() {
    TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
        @Override
        protected void postProcessContext(Context context) {
            SecurityConstraint constraint = new SecurityConstraint();
            constraint.setUserConstraint("NONE");//CONFIDENTIAL
            SecurityCollection collection = new SecurityCollection();
            collection.addPattern("/*");
            constraint.addCollection(collection);
            context.addConstraint(constraint);
        }
    };
    tomcat.addAdditionalTomcatConnectors(httpConnector());
    return tomcat;
  }

//  配置文件配置了https端口,这里配置http端口
  @Bean
  public Connector httpConnector() {
    Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
    connector.setScheme("https");
    connector.setPort(HTTP_PORT);
    connector.setSecure(false);
    connector.setRedirectPort(HTTPS_PORT);
    return connector;
  }

//配置文件配置了http端口,这里配置https端口,上面那种更好
//  @Bean
//  public Connector httpsConnector() {
//    File keystore = new ClassPathResource("sample.jks").getFile();
//    File trustKeystore = new ClassPathResource("sample.jks").getFile();
//    Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//    Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
//    connector.setScheme("https");
//    connector.setSecure(true);
//    connector.setPort(HTTPS_PORT);
//    protocol.setSSLEnabled(true);
//    protocol.setKeystoreFile(keystore.getAbsolutePath());
//    protocol.setKeystorePass(key_store_password);
//    protocol.setKeyPass(key_password);
//    return connector;
//  }
  
  @SuppressWarnings("unused")
  private static void startAllSubcribe(Map<String, Map<String, String>> platformConfigs) 
      throws Exception{
    if(null != platformConfigs){
      for(Map.Entry<String, Map<String, String>> entry:platformConfigs.entrySet()){
        Map<String, String> config = entry.getValue();
        if(null != config && config.containsKey("appId") 
            && config.containsKey("appSecret")){
          logger.info("开启订阅：",NbUtil.subscribe(config.get("appId"), config.get("appSecret")));
          logger.info("服务器地址：{}, appId: {}, aapSecret:{},", Constant.BASE_URL, config.get("appId"), config.get("appSecret"));
        }
      }
    }
  }
}
