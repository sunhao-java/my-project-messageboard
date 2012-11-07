package com.message.main.letter.service;

import java.util.List;

import com.message.base.pagination.PaginationSupport;
import com.message.main.letter.pojo.Letter;
import com.message.main.letter.pojo.LetterUserRelation;
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
	
	/**
	 * 获取当前登录人的收件箱内容
	 * 
	 * @param loginUser			当前登录人
	 * @param read				为空取全部;为1取已读;为0取未读
	 * @param start
	 * @param num
	 * @return
	 * @throws Exception
	 */
	PaginationSupport getInbox(LoginUser loginUser, Integer read, int start, int num) throws Exception;
	
	/**
	 * 获取站内信
	 * 
	 * @param pkId				主键
	 * @return
	 * @throws Exception
	 */
	Letter getLetter(Long pkId) throws Exception;
	
	/**
	 * 获取某一条站内信的接收者
	 * 
	 * @param letterId		站内信ID
	 * @return
	 * @throws Exception
	 */
	List<LetterUserRelation> getReleationByLetter(Long letterId) throws Exception;
	
	/**
	 * 将未读站内信设置为已读
	 * 
	 * @param letterIds		站内信ID
	 * @param loginUser		当前登录人
	 * @param setRead		true:设置成已读;false:设置成未读
	 * @return
	 * @throws Exception
	 */
	boolean setReadOrUnRead(String letterIds, LoginUser loginUser, boolean setRead) throws Exception;
	
	/**
	 * 删除站内信
	 * 
	 * @param letterIds		站内信的ID(以,分开)
	 * @param isInbox		是否是收件箱删除(true收件箱false发件箱)
	 * @param loginUser		登录人
	 * @return
	 * @throws Exception
	 */
	boolean delete(String letterIds, boolean isInbox, LoginUser loginUser) throws Exception;
}
