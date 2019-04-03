/**
 * com.qicai Inc.
 * Copyright (c) 2016-2026 All Rights Reserved.
 */
package com.little.g.admin.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 日志内容
 * @author  liuxl
 * @version LoggerContentDTO.java, v 0.1 2017-6-21 19:57:08
 */
public class LoggerContentDTO implements Serializable{

        private Long id;

	/***/
	private Date gmtCreate;
	
	/***/
	private Date  gmtModified;
	
	/***/
	private String  columnDescription;
	
	/***/
	private String  columnName;
	
	/***/
	private String  comment;
	
	/***/
	private Long  loggerId;
	
	/***/
	private String  newValue;
	
	/***/
	private String  oldValue;
	
        private Integer page = 1;

        private Integer pageCount = 20;

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
	
	
	 /**
     * Setter method for property <tt>gmtCreate</tt>.
     * 
     * @param gmtCreate value to be assigned to property gmtCreate
     */
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	
	 /**
     * Getter method for property <tt>gmtCreate</tt>.
     * 
     * @return gmtCreate value of gmtCreate
     */
	public Date getGmtCreate() {
		return this.gmtCreate;
	}
	
	 /**
     * Setter method for property <tt>gmtModified</tt>.
     * 
     * @param gmtModified value to be assigned to property gmtModified
     */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	
	 /**
     * Getter method for property <tt>gmtModified</tt>.
     * 
     * @return gmtModified value of gmtModified
     */
	public Date getGmtModified() {
		return this.gmtModified;
	}
	
	 /**
     * Setter method for property <tt>columnDescription</tt>.
     * 
     * @param columnDescription value to be assigned to property columnDescription
     */
	public void setColumnDescription(String columnDescription) {
		this.columnDescription = columnDescription;
	}
	
	 /**
     * Getter method for property <tt>columnDescription</tt>.
     * 
     * @return columnDescription value of columnDescription
     */
	public String getColumnDescription() {
		return this.columnDescription;
	}
	
	 /**
     * Setter method for property <tt>columnName</tt>.
     * 
     * @param columnName value to be assigned to property columnName
     */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	 /**
     * Getter method for property <tt>columnName</tt>.
     * 
     * @return columnName value of columnName
     */
	public String getColumnName() {
		return this.columnName;
	}
	
	 /**
     * Setter method for property <tt>comment</tt>.
     * 
     * @param comment value to be assigned to property comment
     */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	 /**
     * Getter method for property <tt>comment</tt>.
     * 
     * @return comment value of comment
     */
	public String getComment() {
		return this.comment;
	}
	
	 /**
     * Setter method for property <tt>loggerId</tt>.
     * 
     * @param loggerId value to be assigned to property loggerId
     */
	public void setLoggerId(Long loggerId) {
		this.loggerId = loggerId;
	}
	
	 /**
     * Getter method for property <tt>loggerId</tt>.
     * 
     * @return loggerId value of loggerId
     */
	public Long getLoggerId() {
		return this.loggerId;
	}
	
	 /**
     * Setter method for property <tt>newValue</tt>.
     * 
     * @param newValue value to be assigned to property newValue
     */
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	
	 /**
     * Getter method for property <tt>newValue</tt>.
     * 
     * @return newValue value of newValue
     */
	public String getNewValue() {
		return this.newValue;
	}
	
	 /**
     * Setter method for property <tt>oldValue</tt>.
     * 
     * @param oldValue value to be assigned to property oldValue
     */
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	
	 /**
     * Getter method for property <tt>oldValue</tt>.
     * 
     * @return oldValue value of oldValue
     */
	public String getOldValue() {
		return this.oldValue;
	}

	@Override
	public String toString() {
		return "LoggerContentDTO{" +
				"id=" + id +
				", gmtCreate=" + gmtCreate +
				", gmtModified=" + gmtModified +
				", columnDescription='" + columnDescription + '\'' +
				", columnName='" + columnName + '\'' +
				", comment='" + comment + '\'' +
				", loggerId=" + loggerId +
				", newValue='" + newValue + '\'' +
				", oldValue='" + oldValue + '\'' +
				", page=" + page +
				", pageCount=" + pageCount +
				'}';
	}
}