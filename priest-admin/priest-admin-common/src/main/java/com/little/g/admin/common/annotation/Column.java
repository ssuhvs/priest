package com.little.g.admin.common.annotation;

import java.lang.annotation.*;

/**
 * 字段信息注解
 * */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Column {

	/**
	 * 字段名
	 * */
	public String name();
	
	/**
	 * 字段描述
	 * */
	public String description();
	
	/**
	 * 是否为必填字段
	 * */
	public boolean required() default false;
	
	/**
	 * 是否检查图片格式<br/>
	 * 如果是存图片的字段,需要检查.
	 * */
	public boolean checkImage() default false;
}
