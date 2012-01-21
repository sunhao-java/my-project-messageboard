package com.message.main.info.pojo;

import java.io.Serializable;
import java.util.Date;

import com.message.main.user.pojo.User;

public class Info implements Serializable {
	private static final long serialVersionUID = -2829715795986492266L;

	private Long pkId; 				// 唯一标识
	private String description; 	// 描述
	private Long modifyUserId;		// 修改的用户的ID
	private String modifyUserName;	// 修改的用户的truename
	private Date modifyDate;		// 修改的时间
	
	//VO Fields
	private User modifyUser;		// 修改人

	public Long getPkId() {
		return pkId;
	}

	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getModifyUserId() {
		return modifyUserId;
	}

	public void setModifyUserId(Long modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public User getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(User modifyUser) {
		this.modifyUser = modifyUser;
	}
}
