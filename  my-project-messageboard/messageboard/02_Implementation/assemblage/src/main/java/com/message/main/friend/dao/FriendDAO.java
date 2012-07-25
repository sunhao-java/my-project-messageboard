package com.message.main.friend.dao;

import java.util.List;

import com.message.main.friend.po.Friend;

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
}
