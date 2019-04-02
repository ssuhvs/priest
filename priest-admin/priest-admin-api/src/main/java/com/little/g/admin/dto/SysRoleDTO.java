/**
 * com.qicai Inc.
 * Copyright (c) 2016-2026 All Rights Reserved.
 */
package com.little.g.admin.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表
 *
 * @author liuxl
 * @version SysRoleDTO.java, v 0.1 2017-5-8 9:54:53
 */
public class SysRoleDTO implements Serializable {

    private Long id;

    /***/
    private String name;

    /***/
    private String menus;

    private Integer page = 1;

    private Integer pageCount = 20;

    private List<Long> menuIdList;

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

    public List<Long> getMenuList() {
        if(menus!=null && !"".equals(menus)){
            menuIdList = new ArrayList<Long>();
            for(String id : menus.split(",")){
                menuIdList.add(Long.valueOf(id));
            }
        }
        return menuIdList;
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

    @Override
    public String toString() {
        return "SysRoleDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", menus='" + menus + '\'' +
                ", page=" + page +
                ", pageCount=" + pageCount +
                ", menuIdList=" + menuIdList +
                '}';
    }
}