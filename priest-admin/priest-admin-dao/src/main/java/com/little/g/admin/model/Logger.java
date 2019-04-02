package com.little.g.admin.model;





import com.little.g.admin.common.annotation.Column;
import com.little.g.admin.common.annotation.Table;
import com.little.g.admin.common.BaseEntity;

import java.util.Date;


/**
 * 数据库表操作日志
 * */
@Table(name = "logger_action", description = "行为操作日志")
public class Logger extends BaseEntity {

	//操作ID.一个操作动作可能会涉及到多张表的日志
	@Column(name = "action_id", description = "操作行为ID")
	private Long actionId;

	// 日志类型 @LogType
	@Column(name = "log_type", description = "日志类型")
	private Integer logType;

	@Column(name = "table_name", description = "数据表名")
	private String tableName;

	@Column(name = "table_description", description = "数据表描述")
	private String tableDescription;

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

	public Long getActionId() {
		return actionId;
	}

	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}

	public Integer getLogType() {
		return logType;
	}

	public void setLogType(Integer logType) {
		this.logType = logType;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableDescription() {
		return tableDescription;
	}

	public void setTableDescription(String tableDescription) {
		this.tableDescription = tableDescription;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
