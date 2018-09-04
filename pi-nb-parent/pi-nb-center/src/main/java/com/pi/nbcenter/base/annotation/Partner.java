package com.pi.nbcenter.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author chenmfa
 * @date 创建时间: 2018-3-24 10:00:05
 * @description 定义
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
@Inherited
public @interface Partner {
  String[] name() default "";
}
