package com.little.g.admin.model;




import com.little.g.admin.common.annotation.Column;
import com.little.g.admin.common.annotation.Table;
import com.little.g.admin.common.BaseEntity;

import java.util.Date;

@Table(name = "logger_content", description = "字段操作日志")
public class LoggerContent extends BaseEntity {

	// 对应的日志ID
	@Column(name = "logger_id", description = "相关日志ID")
	private Long loggerId;

	// 字段名(数据库里字段名)
	@Column(name = "column_name", description = "字段名")
	private String columnName;

	// 字段描述(举例:由于数据库里字段名是business_id这样的,看不懂,因此为字段起了一个名字,如企业用户ID)
	@Column(name = "column_description", description = "字段描述")
	private String columnDescription;

	@Column(name = "old_value", description = "旧值")
	private String oldValue;

	@Column(name = "new_value", description = "新值")
	private String newValue;
	
	@Column(name = "comment", description = "备注")
	private String comment;

	/**
	 * 创建时间
	 * */
	@Column(name = "gmt_create", description = "创建时间")
	private Date gmtCreate;

	/**
	 * 上次修改时间
	 * */
	@Column(name = "gmt_modified", description = "上次修改时间")
	private Date gmtModified;

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Long getLoggerId() {
		return loggerId;
	}

	public void setLoggerId(Long loggerId) {
		this.loggerId = loggerId;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnDescription() {
		return columnDescription;
	}

	public void setColumnDescription(String columnDescription) {
		this.columnDescription = columnDescription;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
