package com.message.base.email;

import java.util.List;
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

import com.message.base.MessageUtils;
import com.message.utils.MD5Utils;
import com.message.utils.StringUtils;

/**
 * 邮件发送工具类
 * @author sunhao(sunhao.java@gmail.com)
 */
public class MailUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(MailUtils.class);
	
	private final String SYS_SENDER_USERNAME = MessageUtils.getMessage("sys.sender.username", "sunhao0550@163.com");
	private final String SYS_SENDER_PASSWORD = MessageUtils.getMessage("sys.sender.password", "sunhao1314520");
	private final String EMAIL_AT = "@";
	private final String EMAIL_DAO = ".";
	private final String CONFIRM_TIP = MessageUtils.getMessage("confirm.tip", "请点击下面的链接验证用户！");
	private final String CONFIRM_TIP_AGAIN = MessageUtils.getMessage("confirm.tip.again", "如果没有成功，请复制下面链接到浏览器地址栏！");
	private final String URL_CONFIRM = MessageUtils.getMessage("user.confirm", "http://sunhao.wiscom.com.cn:8089/message/user/emailConfirm.do?");
	private final String MAIL_CONFIRM_TITLE = MessageUtils.getMessage("mail.confirm.title", "邮件验证");
	
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
	
	/**
	 * 组装要发送邮件的内容
	 * @param pkId
	 * @param username
	 * @return
	 */
	public String makeMailContent(Long pkId, String username){
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
	
	/**
	 * 获得SendMailProperties
	 * 
	 * @param mailProperties
	 * @return
	 */
	public SendMailProperties getSendProperties(List<SendMailProperties> mailProperties){
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
	 * 获取邮件主题
	 * @return
	 */
	public String getMailTitle(){
		return MAIL_CONFIRM_TITLE;
	}
}
