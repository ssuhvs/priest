/**
 * com.qicai Inc.
 * Copyright (c) 2016-2026 All Rights Reserved.
 */
package com.little.g.admin.model;


import com.little.g.admin.common.BaseEntity;

/**
 * 角色信息表
 * @author  liuxl
 * @version SysRole.java, v 0.1 2017-5-8 10:07:46
 */
public class SysRole extends BaseEntity implements java.io.Serializable{

	/***/
	private String  name;
	
	/***/
	private String  menus;
	
	
	
	 /**
     * Setter method for property <tt>name</tt>.
     * 
     * @param name value to be assigned to property name
     */
	public void setName(String name) {
		this.name = name;
	}
	
	 /**
     * Getter method for property <tt>name</tt>.
     * 
     * @return name value of name
     */
	public String getName() {
		return this.name;
	}
	
	 /**
     * Setter method for property <tt>menus</tt>.
     * 
     * @param menus value to be assigned to property menus
     */
	public void setMenus(String menus) {
		this.menus = menus;
	}
	
	 /**
     * Getter method for property <tt>menus</tt>.
     * 
     * @return menus value of menus
     */
	public String getMenus() {
		return this.menus;
	}
}