package com.message.base.email;

import java.io.Serializable;

/**
 * 自定义的邮件发送配置类
 * @author sunhao(sunhao.java@gmail.com)
 */
public class SendMailProperties implements Serializable {
	private static final long serialVersionUID = 1245472715337874695L;
	
	private String name;				//邮件名称
	private String smtp;				//smtp服务器名称
	private int port;					//端口号
	private String description; 		//描述
	private String senderUsername;		//发送者用户名
	private String senderPassword;		//发送者密码

	public SendMailProperties() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSmtp() {
		return smtp;
	}

	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSenderUsername() {
		return senderUsername;
	}

	public void setSenderUsername(String senderUsername) {
		this.senderUsername = senderUsername;
	}

	public String getSenderPassword() {
		return senderPassword;
	}

	public void setSenderPassword(String senderPassword) {
		this.senderPassword = senderPassword;
	}
}
