/**
 * com.qicai Inc.
 * Copyright (c) 2016-2026 All Rights Reserved.
 */
package com.little.g.admin.model;


import com.little.g.admin.common.model.BaseEntity;

/**
 * 菜单信息表
 * @author  liuxl
 * @version SysMenu.java, v 0.1 2017-5-8 10:07:46
 */
public class SysMenu extends BaseEntity implements java.io.Serializable{

	/***/
	private String  name;
	
	/***/
	private String  url;
	
	/***/
	private Integer  pId;

	private String className;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
}