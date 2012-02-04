package com.message.base.email;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 邮件发送工具类
 * @author sunhao(sunhao.java@gmail.com)
 */
public class MailUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(MailUtils.class);
	
	private static MailUtils instance = new MailUtils();
	
	/**
	 * 初始化实例
	 * @return
	 */
	public static MailUtils getInstance() {
		return instance == null ? new MailUtils() : instance;
	}

	/**
	 * 发送邮件
	 * 
	 * @param bean
	 * @return
	 */
	public boolean sendMail(MailBean bean){
		Properties props = getProps("true", "smtp");
		Session session = Session.getInstance(props);
		session.setDebug(true);
		
		SendMailProperties properties = bean.getSendMailProperties();
		
		Message message = makeMail(session, bean.getMailTitle(), bean.getMailContent(), properties.getSenderUsername());
		
		Transport transport = null;
		try {
			transport = session.getTransport();
			transport.connect(bean.getSendMailProperties().getSmtp(), bean.getSendMailProperties().getPort(), properties.getSenderUsername(), 
					properties.getSenderPassword());
			
			LOGGER.info("the receiver is {}", bean.getReceiver());
			Address[] addresses = {
					new InternetAddress(bean.getReceiver())
			};
			transport.sendMessage(message, addresses);
			transport.close();
			
			return true;
		} catch (NoSuchProviderException e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			return false;
		} catch (MessagingException e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 发送邮件的配置文件
	 * @param auth 认证方式
	 * @param smtp 发送邮件服务器
	 **/
	private static Properties getProps(String auth, String smtp){
		Properties props = new Properties();
		props.setProperty("mail.smtp.auth", auth);
		props.setProperty("mail.transport.protocol", smtp);
		
		return props;
	}
	
	/**
	 * 创建邮件message
	 * @param session 根据配置获得的session
	 * @param title 邮件主题
	 * @param content 邮件的内容
	 * @param fromAddress 发送者地址
	 **/
	private static Message makeMail(Session session, String title, String content, String fromAddress){
		Message message = new MimeMessage(session);
		try {
			LOGGER.info("this mail's title is {}", title);
			message.setSubject(title);
			LOGGER.info("this mail's content is {}", content);
			message.setContent(content, "text/html;charset=utf-8");
			LOGGER.info("this mail's sender is {}", fromAddress);
			Address address = new InternetAddress(fromAddress);
			message.setFrom(address);
		} catch (MessagingException e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
		
		return message;
	}
}
