package com.message.base.email;

/**
 * 用户注册后发送验证邮件接口
 * @author sunhao(sunhao.java@gmail.com)
 */
public interface MailSend {
	/**
	 * 发送邮件
	 * @param pkId
	 * @param username
	 * @param useremail
	 * @throws Exception
	 */
	void sendMail(Long pkId, String username, String useremail) throws Exception;
}
