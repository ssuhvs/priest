/**
 * com.qicai Inc.
 * Copyright (c) 2016-2026 All Rights Reserved.
 */
package com.little.g.admin.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单信息表
 * @author  liuxl
 * @version SysMenuDTO.java, v 0.1 2017-5-8 9:54:53
 */
public class SysMenuDTO implements Serializable{

        private Long id;

	/***/
	private String  name;
	
	/***/
	private String  url;
	
	/***/
	private Integer  pId;

	private String className;
	
        private Integer page = 1;

        private Integer pageCount = 20;

        private List<SysMenuDTO> subMenus;

	public List<SysMenuDTO> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(List<SysMenuDTO> subMenus) {
		this.subMenus = subMenus;
	}

	public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Integer getPage() {
            return page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }

        public Integer getPageCount() {
            return pageCount;
        }

        public void setPageCount(Integer pageCount) {
            this.pageCount = pageCount;
        }

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

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
     * Setter method for property <tt>url</tt>.
     * 
     * @param url value to be assigned to property url
     */
	public void setUrl(String url) {
		this.url = url;
	}
	
	 /**
     * Getter method for property <tt>url</tt>.
     * 
     * @return url value of url
     */
	public String getUrl() {
		return this.url;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	@Override
	public String toString() {
		return "SysMenuDTO{" +
				"id=" + id +
				", name='" + name + '\'' +
				", url='" + url + '\'' +
				", pId=" + pId +
				", className='" + className + '\'' +
				", page=" + page +
				", pageCount=" + pageCount +
				", subMenus=" + subMenus +
				'}';
	}
}