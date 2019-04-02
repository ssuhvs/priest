/**
 * com.qicai Inc.
 * Copyright (c) 2016-2026 All Rights Reserved.
 */
package com.little.g.admin.dto;

import java.io.Serializable;

/**
 * 日志方法
 * @author  liuxl
 * @version LoggerActionDTO.java, v 0.1 2017-6-21 19:57:08
 */
public class LoggerActionDTO implements Serializable{

        private Long id;

	/**操作人*/
	private String  staffName;
	
	/**操作描述*/
	private String  comment;
	
	/**模块名称*/
	private String  moduleName;
	
	/**操作类型描述*/
	private String  operationDescription;
	
	/**操作类型*/
	private Integer  operationType;
	
	/**详细内容*/
	private String  content;
	
	/**操作人id*/
	private Integer  userId;
	
	/**创建时间*/
	private Long  gmtCreate;
	
	/**修改时间*/
	private Long  gmtModified;
	
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
     * Setter method for property <tt>staffName</tt>.
     * 
     * @param staffName value to be assigned to property staffName
     */
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	
	 /**
     * Getter method for property <tt>staffName</tt>.
     * 
     * @return staffName value of staffName
     */
	public String getStaffName() {
		return this.staffName;
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
     * Setter method for property <tt>moduleName</tt>.
     * 
     * @param moduleName value to be assigned to property moduleName
     */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	 /**
     * Getter method for property <tt>moduleName</tt>.
     * 
     * @return moduleName value of moduleName
     */
	public String getModuleName() {
		return this.moduleName;
	}
	
	 /**
     * Setter method for property <tt>operationDescription</tt>.
     * 
     * @param operationDescription value to be assigned to property operationDescription
     */
	public void setOperationDescription(String operationDescription) {
		this.operationDescription = operationDescription;
	}
	
	 /**
     * Getter method for property <tt>operationDescription</tt>.
     * 
     * @return operationDescription value of operationDescription
     */
	public String getOperationDescription() {
		return this.operationDescription;
	}
	
	 /**
     * Setter method for property <tt>operationType</tt>.
     * 
     * @param operationType value to be assigned to property operationType
     */
	public void setOperationType(Integer operationType) {
		this.operationType = operationType;
	}
	
	 /**
     * Getter method for property <tt>operationType</tt>.
     * 
     * @return operationType value of operationType
     */
	public Integer getOperationType() {
		return this.operationType;
	}
	
	 /**
     * Setter method for property <tt>content</tt>.
     * 
     * @param content value to be assigned to property content
     */
	public void setContent(String content) {
		this.content = content;
	}
	
	 /**
     * Getter method for property <tt>content</tt>.
     * 
     * @return content value of content
     */
	public String getContent() {
		return this.content;
	}
	
	 /**
     * Setter method for property <tt>userId</tt>.
     * 
     * @param userId value to be assigned to property userId
     */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	 /**
     * Getter method for property <tt>userId</tt>.
     * 
     * @return userId value of userId
     */
	public Integer getUserId() {
		return this.userId;
	}
	
	 /**
     * Setter method for property <tt>gmtCreate</tt>.
     * 
     * @param gmtCreate value to be assigned to property gmtCreate
     */
	public void setGmtCreate(Long gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	
	 /**
     * Getter method for property <tt>gmtCreate</tt>.
     * 
     * @return gmtCreate value of gmtCreate
     */
	public Long getGmtCreate() {
		return this.gmtCreate;
	}
	
	 /**
     * Setter method for property <tt>gmtModified</tt>.
     * 
     * @param gmtModified value to be assigned to property gmtModified
     */
	public void setGmtModified(Long gmtModified) {
		this.gmtModified = gmtModified;
	}
	
	 /**
     * Getter method for property <tt>gmtModified</tt>.
     * 
     * @return gmtModified value of gmtModified
     */
	public Long getGmtModified() {
		return this.gmtModified;
	}

	@Override
	public String toString() {
		return "LoggerActionDTO{" +
				"id=" + id +
				", staffName='" + staffName + '\'' +
				", comment='" + comment + '\'' +
				", moduleName='" + moduleName + '\'' +
				", operationDescription='" + operationDescription + '\'' +
				", operationType=" + operationType +
				", content='" + content + '\'' +
				", userId=" + userId +
				", gmtCreate=" + gmtCreate +
				", gmtModified=" + gmtModified +
				", page=" + page +
				", pageCount=" + pageCount +
				'}';
	}
}