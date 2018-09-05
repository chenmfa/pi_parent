package com.pi;

import java.io.IOException;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.hibernate.validator.HibernateValidator;
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
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description Stroop启动模块
 * @author chenmfa
 * @description mvn spring-boot:run -Drun.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"
 */
@SpringBootApplication
@ServletComponentScan
@ComponentScan("com.pi")
@MapperScan(basePackages={"com.pi"})
public class StroopBootStrap {
  public static final Logger logger = LoggerFactory.getLogger(StroopBootStrap.class);
  public static ApplicationContext CONTEXT = null;
  @Value("${server.port}")
  private int HTTPS_PORT;
  @Value("${server.http.port}")
  private int HTTP_PORT;
  public static void main(String[] args) throws IOException {

//    {@link org.springframework.boot.context.event.ApplicationStartingEvent} springboot启动开始时执行的事件
//    {@link org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent}spring boot 对应Enviroment已经准备完毕，但此时上下文context还没有创建。
//    {@link org.springframework.boot.context.event.ApplicationFailedEvent} spring boot启动异常时执行事件 
    SpringApplication application = new SpringApplication(StroopBootStrap.class);
    application.addListeners((ApplicationListener<ApplicationEnvironmentPreparedEvent>) event -> {
      logger.info("----------------- Spring 环境首次初始化成功：{} -----------------------------");
    });
    application.addListeners((ApplicationListener<ApplicationStartingEvent>) event -> {
      logger.info("----------------- Spring Boot 应用正在启动：{} -----------------------------");
    });
    application.addListeners((ApplicationListener<ApplicationReadyEvent>) event -> {
      
    });
    application.addListeners((ApplicationListener<ApplicationFailedEvent>) event -> {
      logger.info("----------------- Spring Boot应用启动失败：" + event.getException().toString());
    });
    CONTEXT = 
        application.run(args);
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
  
//  配置validator为快速验证(因为默认会返回所有错误消息，这个是没必要的)
  @Bean
  public Validator validator() {
      ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
              .configure().failFast(true)
//              .addProperty("hibernate.validator.fail_fast", "true")
              .buildValidatorFactory();
      Validator validator = validatorFactory.getValidator();
      return validator;
  }
}
