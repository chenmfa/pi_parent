package com.pi.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author chenmfa
 * @date 创建时间: 2018年6月23日18:02:57
 * @description 定义访问属性的别名
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
@Inherited
public @interface FieldAlia {
	String name() default "";
}
