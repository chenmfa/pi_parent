package com.pi.common.http.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author chenmfa
 * @date 创建时间: 2018年9月13日16:49:25
 * @description 定义是否拦截的注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD})
@Inherited
public @interface InterceptorIgnore {
  boolean value() default true;
  boolean filterQuote() default false;
  boolean logParam() default true;//判断是否需要全局输出请求日志,由于app端需要调试，需要问服务端接收到了什么，这个暂时全局开启
  boolean debugOutPut() default true;//是否全局输出返回日志(用于关闭像固件查询等的接口返回大数据的情况, 影响错误排查)
  String desc()  default "";
}
