package com.little.g.admin.common.annotation;

import java.lang.annotation.*;

/**
 * 数据库表信息注解
 * */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Table {

	/**
	 * 数据库表名称
	 * */
	public String name();
	
	/**
	 * 数据库表描述
	 * */
	public String description();
}
