/**
 * com.qicai Inc.
 * Copyright (c) 2016-2026 All Rights Reserved.
 */
package com.little.g.admin.dto;

import java.io.Serializable;

/**
 * 后台管理用户信息表
 * @author  liuxl
 * @version AdminUserDTO.java, v 0.1 2017-5-8 9:54:53
 */
public class AdminUserDTO implements Serializable{

	private Long id;

	/**用户名称*/
	private String  username;
	
	/**密码*/
	private String  password;
	
	/**状态 0正常 1 锁定*/
	private Byte  status;
	
	/**地区id*/
	private String  areaCode;
	
	/**城市id*/
	private String  cityCode;
	
	/**省份id*/
	private String  provCode;
	
	/**联系电话*/
	private String  mobile;
	
	/**联系地址*/
	private String  address;
	
	/**创建时间*/
	private Long  createTime;
	
	/**修改时间*/
	private Long  updateTime;
	
	/**用户类型 1管理员 2区域管理员 3 校长 4 教师*/
	private Integer  userType;
	
	/**加入时间*/
	private String  joinTime;
	
	/**角色id*/
	private Integer  roleId;
	
	/**email*/
	private String  email;
	
	/***/
	private String  openid;
	
	/**头像*/
	private String  avatar;
	
	/**职位*/
	private String  position;
	
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
     * Setter method for property <tt>username</tt>.
     * 
     * @param username value to be assigned to property username
     */
	public void setUsername(String username) {
		this.username = username;
	}
	
	 /**
     * Getter method for property <tt>username</tt>.
     * 
     * @return username value of username
     */
	public String getUsername() {
		return this.username;
	}
	
	 /**
     * Setter method for property <tt>password</tt>.
     * 
     * @param password value to be assigned to property password
     */
	public void setPassword(String password) {
		this.password = password;
	}
	
	 /**
     * Getter method for property <tt>password</tt>.
     * 
     * @return password value of password
     */
	public String getPassword() {
		return this.password;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	/**
     * Setter method for property <tt>areaCode</tt>.
     * 
     * @param areaCode value to be assigned to property areaCode
     */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
	 /**
     * Getter method for property <tt>areaCode</tt>.
     * 
     * @return areaCode value of areaCode
     */
	public String getAreaCode() {
		return this.areaCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/**
     * Setter method for property <tt>provCode</tt>.
     * 
     * @param provCode value to be assigned to property provCode
     */
	public void setProvCode(String provCode) {
		this.provCode = provCode;
	}
	
	 /**
     * Getter method for property <tt>provCode</tt>.
     * 
     * @return provCode value of provCode
     */
	public String getProvCode() {
		return this.provCode;
	}
	
	 /**
     * Setter method for property <tt>mobile</tt>.
     * 
     * @param mobile value to be assigned to property mobile
     */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	 /**
     * Getter method for property <tt>mobile</tt>.
     * 
     * @return mobile value of mobile
     */
	public String getMobile() {
		return this.mobile;
	}
	
	 /**
     * Setter method for property <tt>address</tt>.
     * 
     * @param address value to be assigned to property address
     */
	public void setAddress(String address) {
		this.address = address;
	}
	
	 /**
     * Getter method for property <tt>address</tt>.
     * 
     * @return address value of address
     */
	public String getAddress() {
		return this.address;
	}
	
	 /**
     * Setter method for property <tt>createTime</tt>.
     * 
     * @param createTime value to be assigned to property createTime
     */
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	
	 /**
     * Getter method for property <tt>createTime</tt>.
     * 
     * @return createTime value of createTime
     */
	public Long getCreateTime() {
		return this.createTime;
	}
	
	 /**
     * Setter method for property <tt>updateTime</tt>.
     * 
     * @param updateTime value to be assigned to property updateTime
     */
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	
	 /**
     * Getter method for property <tt>updateTime</tt>.
     * 
     * @return updateTime value of updateTime
     */
	public Long getUpdateTime() {
		return this.updateTime;
	}
	
	 /**
     * Setter method for property <tt>userType</tt>.
     * 
     * @param userType value to be assigned to property userType
     */
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	
	 /**
     * Getter method for property <tt>userType</tt>.
     * 
     * @return userType value of userType
     */
	public Integer getUserType() {
		return this.userType;
	}
	
	 /**
     * Setter method for property <tt>joinTime</tt>.
     * 
     * @param joinTime value to be assigned to property joinTime
     */
	public void setJoinTime(String joinTime) {
		this.joinTime = joinTime;
	}
	
	 /**
     * Getter method for property <tt>joinTime</tt>.
     * 
     * @return joinTime value of joinTime
     */
	public String getJoinTime() {
		return this.joinTime;
	}
	
	 /**
     * Setter method for property <tt>roleId</tt>.
     * 
     * @param roleId value to be assigned to property roleId
     */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	 /**
     * Getter method for property <tt>roleId</tt>.
     * 
     * @return roleId value of roleId
     */
	public Integer getRoleId() {
		return this.roleId;
	}
	
	 /**
     * Setter method for property <tt>email</tt>.
     * 
     * @param email value to be assigned to property email
     */
	public void setEmail(String email) {
		this.email = email;
	}
	
	 /**
     * Getter method for property <tt>email</tt>.
     * 
     * @return email value of email
     */
	public String getEmail() {
		return this.email;
	}
	
	 /**
     * Setter method for property <tt>openid</tt>.
     * 
     * @param openid value to be assigned to property openid
     */
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	 /**
     * Getter method for property <tt>openid</tt>.
     * 
     * @return openid value of openid
     */
	public String getOpenid() {
		return this.openid;
	}
	
	 /**
     * Setter method for property <tt>avatar</tt>.
     * 
     * @param avatar value to be assigned to property avatar
     */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	 /**
     * Getter method for property <tt>avatar</tt>.
     * 
     * @return avatar value of avatar
     */
	public String getAvatar() {
		return this.avatar;
	}
	
	 /**
     * Setter method for property <tt>position</tt>.
     * 
     * @param position value to be assigned to property position
     */
	public void setPosition(String position) {
		this.position = position;
	}
	
	 /**
     * Getter method for property <tt>position</tt>.
     * 
     * @return position value of position
     */
	public String getPosition() {
		return this.position;
	}

	@Override
	public String toString() {
		return "AdminUserDTO{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", status=" + status +
				", areaCode='" + areaCode + '\'' +
				", cityCode='" + cityCode + '\'' +
				", provCode='" + provCode + '\'' +
				", mobile='" + mobile + '\'' +
				", address='" + address + '\'' +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				", userType=" + userType +
				", joinTime='" + joinTime + '\'' +
				", roleId=" + roleId +
				", email='" + email + '\'' +
				", openid='" + openid + '\'' +
				", avatar='" + avatar + '\'' +
				", position='" + position + '\'' +
				", page=" + page +
				", pageCount=" + pageCount +
				'}';
	}
}