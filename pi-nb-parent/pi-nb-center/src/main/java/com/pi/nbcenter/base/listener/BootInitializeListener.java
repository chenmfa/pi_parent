package com.pi.nbcenter.base.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @description 初始化监听器/过滤器/servlet
 * @description 1. 注解方式 {@link BootInitializeListener#contextDestroyed} 
 * @description 2. spring bean注解{@link com.pi.DeviceCenterBoot#servletListenerRegistrationBean}
 * @description 
 * @author chenmfa
 *
 */
//@WebServlet(name = "IndexServlet",urlPatterns = "/hello")
//@WebFilter(urlPatterns = "/*", filterName = "indexFilter")
@WebListener
public class BootInitializeListener implements ServletContextListener{

  @Override
  public void contextDestroyed(ServletContextEvent context) {
    
  }
  @Override
  public void contextInitialized(ServletContextEvent context) {
    
  }
}
