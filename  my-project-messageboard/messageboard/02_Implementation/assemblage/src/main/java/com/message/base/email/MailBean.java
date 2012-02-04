package com.message.base.email;

/**
 * 自定义的邮件bean
 * @author sunhao(sunhao.java@gmail.com)
 */
public class MailBean {
	private String receiver;						//接收者
	private SendMailProperties sendMailProperties;	//自定义的邮件配置类
	private String mailTitle;						//邮件主题
	private String mailContent;						//邮件内容
	
	/**
	 * 默认构造器
	 */
	public MailBean() {
	}

	/**
	 * 带参数构造器
	 * @param receiver
	 * @param sendMailProperties
	 * @param mailTitle
	 * @param mailContent
	 */
	public MailBean(String receiver, SendMailProperties sendMailProperties,
			String mailTitle, String mailContent) {
		this.receiver = receiver;
		this.sendMailProperties = sendMailProperties;
		this.mailTitle = mailTitle;
		this.mailContent = mailContent;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public SendMailProperties getSendMailProperties() {
		return sendMailProperties;
	}

	public void setSendMailProperties(SendMailProperties sendMailProperties) {
		this.sendMailProperties = sendMailProperties;
	}

	public String getMailTitle() {
		return mailTitle;
	}

	public void setMailTitle(String mailTitle) {
		this.mailTitle = mailTitle;
	}

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}
}
