/**
 * com.qicai Inc.
 * Copyright (c) 2016-2026 All Rights Reserved.
 */
package com.little.g.admin.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 日志
 * @author  liuxl
 * @version LoggerDTO.java, v 0.1 2017-6-21 19:57:08
 */
public class LoggerDTO implements Serializable{

        private Long id;

	/***/
	private Date gmtCreate;
	
	/***/
	private Date  gmtModified;
	
	/***/
	private Long  actionId;
	
	/***/
	private String  comment;
	
	/***/
	private Integer  logType;
	
	/***/
	private String  tableDescription;
	
	/***/
	private String  tableName;
	
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
     * Setter method for property <tt>actionId</tt>.
     * 
     * @param actionId value to be assigned to property actionId
     */
	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}
	
	 /**
     * Getter method for property <tt>actionId</tt>.
     * 
     * @return actionId value of actionId
     */
	public Long getActionId() {
		return this.actionId;
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
     * Setter method for property <tt>logType</tt>.
     * 
     * @param logType value to be assigned to property logType
     */
	public void setLogType(Integer logType) {
		this.logType = logType;
	}
	
	 /**
     * Getter method for property <tt>logType</tt>.
     * 
     * @return logType value of logType
     */
	public Integer getLogType() {
		return this.logType;
	}
	
	 /**
     * Setter method for property <tt>tableDescription</tt>.
     * 
     * @param tableDescription value to be assigned to property tableDescription
     */
	public void setTableDescription(String tableDescription) {
		this.tableDescription = tableDescription;
	}
	
	 /**
     * Getter method for property <tt>tableDescription</tt>.
     * 
     * @return tableDescription value of tableDescription
     */
	public String getTableDescription() {
		return this.tableDescription;
	}
	
	 /**
     * Setter method for property <tt>tableName</tt>.
     * 
     * @param tableName value to be assigned to property tableName
     */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	 /**
     * Getter method for property <tt>tableName</tt>.
     * 
     * @return tableName value of tableName
     */
	public String getTableName() {
		return this.tableName;
	}
}