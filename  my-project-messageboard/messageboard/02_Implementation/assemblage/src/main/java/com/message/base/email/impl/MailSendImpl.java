package com.message.base.email.impl;

import java.util.ArrayList;
import java.util.List;

import com.message.base.email.MailBean;
import com.message.base.email.MailSend;
import com.message.base.email.MailUtils;
import com.message.base.email.SendMailProperties;

/**
 * 用户注册后发送验证邮件接口的实现
 * @author sunhao(sunhao.java@gmail.com)
 */
public class MailSendImpl implements MailSend {
	private List<SendMailProperties> mailProperties = new ArrayList<SendMailProperties>();

	public List<SendMailProperties> getMailProperties() {
		return mailProperties;
	}

	public void setMailProperties(List<SendMailProperties> mailProperties) {
		this.mailProperties = mailProperties;
	}

	/**
	 * 发送邮件
	 * @param pkId
	 * @param username
	 * @param useremail
	 * @throws Exception
	 */
	public void sendMail(Long pkId, String username, String useremail) throws Exception {
		MailUtils mailUtils = MailUtils.getInstance();
		SendMailProperties properties = mailUtils.getSendProperties(getMailProperties());
		
		MailBean bean = new MailBean(useremail, properties, mailUtils.getMailTitle(), mailUtils.makeMailContent(pkId, username));
		
		mailUtils.sendMail(bean);
	}
	
}
