package com.message.main.letter.service;

import com.message.main.letter.pojo.Letter;
import com.message.main.login.pojo.LoginUser;

/**
 * 站内信的service接口.
 * 
 * @author Danny(sunhao)(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-10-22 下午09:20:43
 */
public interface LetterService {
	
	/**
	 * 发送站内信
	 * 
	 * @param receiverIds		站内信的接收者
	 * @param letter			站内信
	 * @param loginUser			登录者
	 * @return
	 * @throws Exception
	 */
	boolean send(String receiverIds, Letter letter, LoginUser loginUser) throws Exception;
	
}
