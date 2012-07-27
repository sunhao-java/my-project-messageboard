package com.message.main.friend.dao;

import java.util.List;

import com.message.base.pagination.PaginationSupport;
import com.message.main.friend.po.Friend;
import com.message.main.login.pojo.LoginUser;

/**
 * .
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-7-21 上午03:49:29
 */
public interface FriendDAO {
	
	/**
	 * 保存好友申请
	 * 
	 * @param friend		好友申请
	 * @return
	 */
	Long saveApplyFriends(Friend friend) throws Exception;
	
	/**
	 * 获取当前登录者已经发出申请或者已经是好友的用户ID
	 * 
	 * @param applyUserId	当前登录人的ID
	 * @return
	 */
	List<Long> getAppliedIds(Long applyUserId) throws Exception;
	
	/**
	 * 根据定制的条件获取好友
	 * 
	 * @param start
	 * @param num
	 * @param inviteType	邀请类型(send:我发出的,receive:我收到的)
	 * @param agreeFlag		是否同意(0未回答1同意2拒绝)
	 * @param loginUser		当前登录人
	 * @return
	 * @throws Exception
	 */
	PaginationSupport getFriendsByCustom(int start, int num, String inviteType, Integer agreeFlag, LoginUser loginUser) throws Exception;
	
	/**
	 * 取消请求
	 * 
	 * @param pkId
	 * @return
	 * @throws Exception
	 */
	boolean cancelRequest(Long pkId) throws Exception;
}
