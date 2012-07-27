package com.message.main.friend.service;

import java.util.List;

import com.message.base.pagination.PaginationSupport;

/**
 * .
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-7-21 上午03:48:48
 */
public interface FriendService {
	
	/**
	 * 保存好友申请
	 * 
	 * @param selectedUserIds	被申请的用户ID
	 * @param applyMessage		申请时的留言
	 * @param isEmailNotify		是否发邮件通知
	 * @return
	 * @throws Exception
	 */
	boolean saveApplyFriends(Long[] selectedUserIds, String applyMessage, boolean isEmailNotify) throws Exception;
	
	/**
	 * 获取当前登录者已经发出申请或者已经是好友的用户ID
	 * 
	 * @return
	 * @throws Exception
	 */
	List<Long> getAppliedIds() throws Exception;
	
	/**
	 * 我发出的邀请
	 * 
	 * @param start
	 * @param num
	 * @return
	 * @throws Exception
	 */
	PaginationSupport getMySendInvite(int start, int num) throws Exception;
	
	/**
	 * 取消请求
	 * 
	 * @param pkId
	 * @return
	 * @throws Exception
	 */
	boolean cancelRequest(Long pkId) throws Exception;
}
