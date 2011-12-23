package com.message.main.info.pojo;

import java.io.Serializable;

public class Info implements Serializable {
	private static final long serialVersionUID = -2829715795986492266L;

	private Long pkId; 			// 唯一标识
	private String description; // 描述

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
}
