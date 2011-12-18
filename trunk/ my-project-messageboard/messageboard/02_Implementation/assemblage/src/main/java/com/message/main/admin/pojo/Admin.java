package com.message.main.admin.pojo;

import java.io.Serializable;

/**
 * 管理员实体类
 * @author sunhao(sunhao.java@gmail.com)
 */
public class Admin implements Serializable {
	private static final long serialVersionUID = 8119976899856759913L;

	// Fields
	private Long pkId; 			// 唯一标识，主键
	private String username; 	// 用户名
	private String password; 	// 登录密码，MD5加密
	private Long deleteFlag;	//软删除，0未删除，1已删除

	public Admin() {
	}

	public Long getPkId() {
		return pkId;
	}

	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Long deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

}
