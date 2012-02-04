package com.message.base.email.impl;

import java.util.ArrayList;
import java.util.List;

import com.message.base.MessageUtils;
import com.message.base.email.MailBean;
import com.message.base.email.MailSend;
import com.message.base.email.MailUtils;
import com.message.base.email.SendMailProperties;
import com.message.utils.MD5Utils;
import com.message.utils.StringUtils;

/**
 * 用户注册后发送验证邮件接口的实现
 * @author sunhao(sunhao.java@gmail.com)
 */
public class MailSendImpl implements MailSend {
	private static List<SendMailProperties> mailProperties = new ArrayList<SendMailProperties>();
	private static final String SYS_SENDER_USERNAME = MessageUtils.getMessage("sys.sender.username", "sunhao0550@163.com");
	private static final String SYS_SENDER_PASSWORD = MessageUtils.getMessage("sys.sender.password", "sunhao1314520");
	private static final String EMAIL_AT = "@";
	private static final String EMAIL_DAO = ".";
	private static final String CONFIRM_TIP = MessageUtils.getMessage("confirm.tip", "请点击下面的链接验证用户！");
	private static final String CONFIRM_TIP_AGAIN = MessageUtils.getMessage("confirm.tip.again", "如果没有成功，请复制下面链接到浏览器地址栏！");
	private static final String URL_CONFIRM = MessageUtils.getMessage("user.confirm", "http://sunhao.wiscom.com.cn:8089/message/user/emailConfirm.do?");
	private static final String MAIL_CONFIRM_TITLE = MessageUtils.getMessage("mail.confirm.title", "邮件验证");

	public List<SendMailProperties> getMailProperties() {
		return mailProperties;
	}

	@SuppressWarnings("static-access")
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
		SendMailProperties properties = getSendProperties();
		
		MailBean bean = new MailBean(useremail, properties, MAIL_CONFIRM_TITLE, makeMailContent(pkId, username));
		
		mailUtils.sendMail(bean);
	}
	
	/**
	 * 获得SendMailProperties
	 * @return
	 */
	private static SendMailProperties getSendProperties(){
		String mailServiceDomainName = StringUtils.getBetweenTwoLetters(SYS_SENDER_USERNAME, EMAIL_AT, EMAIL_DAO);
		for(SendMailProperties properties : mailProperties) {
			if(properties.getName().equals(mailServiceDomainName)){
				properties.setSenderUsername(SYS_SENDER_USERNAME);
				properties.setSenderPassword(SYS_SENDER_PASSWORD);
				
				return properties;
			}
		}
		
		return null;
	}
	
	/**
	 * 组装要发送邮件的内容
	 * @param pkId
	 * @param username
	 * @return
	 */
	private static String makeMailContent(Long pkId, String username){
		StringBuffer sb = new StringBuffer();
		String username_md5 = MD5Utils.MD5Encode(username);
		sb.append(CONFIRM_TIP).append("<br/>");
		sb.append("<a href='").append(URL_CONFIRM).append("usernameMD5Code=").append(username_md5).append("&userid=").append(pkId);
		sb.append("'>").append(URL_CONFIRM).append("usernameMD5Code=").append(username_md5).append("&userid=").append(pkId).append("</a>");
		sb.append("<br/>");
		sb.append(CONFIRM_TIP_AGAIN).append("<br/>");
		sb.append(URL_CONFIRM).append("usernameMD5Code=").append(username_md5).append("&userid=").append(pkId);
		
		return sb.toString();
	}
	
}
