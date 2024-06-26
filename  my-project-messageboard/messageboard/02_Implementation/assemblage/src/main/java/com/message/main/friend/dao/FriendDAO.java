package com.message.main.friend.dao;

import java.util.List;

import com.message.base.pagination.PaginationSupport;
import com.message.main.friend.po.Friend;
import com.message.main.friend.po.FriendApply;
import com.message.main.friend.po.FriendGroup;
import com.message.main.login.pojo.LoginUser;


/**
 * 好友模块DAO.
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-7-21 上午03:49:29
 */
public interface FriendDAO {
	
	/**
	 * 获取某个用户的好友
	 * 
	 * @param userId			用户ID
	 * @param groupId			分组ID
	 * @param start
	 * @param num
     * @param keyword         好友用户名的关键字(USER_NAME,TRUE_NAME)
	 * @return
	 * @throws Exception
	 */
	PaginationSupport listFriends(Long userId, Long groupId, int start, int num, String keyword) throws Exception;
	
	/**
	 * 获取登录者的未分组好友数目
	 * 
	 * @param loginUserId
	 * @return
	 * @throws Exception
	 */
	int getNoGroupFriendNum(Long loginUserId) throws Exception;
	
	/**
	 * 获取正在申请中的好友
	 * 
	 * @param userId			查看者ID
	 * @param result			申请结果(仅限0和2,即申请中和已拒绝)
	 * @return
	 * @throws Exception
	 */
	PaginationSupport listApplyFriends(Long userId, Integer result, Integer returnType, int start, int num) throws Exception;
	
	/**
	 * 获取好友
	 * 
	 * @param fid				好友对象ID
	 * @return
	 * @throws Exception
	 */
	Friend getFriend(Long fid) throws Exception;
	
	/**
	 * 保存实体
	 * 
	 * @param entity			实体
	 * @throws Exception
	 */
	<T> T saveEntity(T entity) throws Exception;
	
	/**
	 * 获取好友申请
	 * 
	 * @param faid				好友申请对象ID
	 * @return
	 * @throws Exception
	 */
	FriendApply getFriendApply(Long faid) throws Exception;
	
	/**
	 * 按照申请类型获取别人给自己的申请(receive)和我的申请(send)
	 * 
	 * @param loginUser				当前登录者
	 * @param applyType				类型(receive， send)
	 * @param start
	 * @param num
	 * @return
	 * @throws Exception
	 */
	PaginationSupport getAllMyReceiveOrSend(LoginUser loginUser, String applyType, int start, int num) throws Exception;
	
	/**
	 * 根据申请的ID和用户ID获取好友
	 * 
	 * @param friendApplyId		申请的ID
	 * @param userId			用户ID
	 * @return
	 * @throws Exception
	 */
	Friend getFriend(Long friendApplyId, Long userId) throws Exception;
	
	/**
	 * 删除一个实体
	 * 
	 * @param pkId				此实体的ID
	 * @param deleteClazz		此实体的类型
	 * @throws Exception
	 */
	int deleteObject(Long pkId, Class deleteClazz) throws Exception;
	
	/**
	 * 更新实体
	 * 
	 * @param entity			实体
	 * @throws Exception
	 */
	void updateEntity(Object entity) throws Exception;
	
	/**
     * 删除好友
     * 
     * @param userId			当前登录者ID
     * @param friendId			好友的ID
     * @return
     * @throws Exception
     */
    boolean deleteFriend(Long userId, Long friendId) throws Exception;
    
    /**
     * 获取loginUser的全部分组
     * 
     * @param userId			登录者ID
     * @param start				
     * @param num
     * @return
     * @throws Exception
     */
    PaginationSupport getFriendGroups(Long userId, int start, int num) throws Exception;
    
    /**
	 * 获取好友分组
	 * 
	 * @param fgid				好友分组ID
	 * @return
	 * @throws Exception
	 */
    FriendGroup getFriendGroup(Long fgid) throws Exception;
    
    /**
	 * 获取一个分组下的好友数目
	 * 
	 * @param groupId			分组ID
	 * @return
	 * @throws Exception
	 */
	int getGroupUserNum(Long groupId) throws Exception;
	
	/**
	 * 根据好友对象获取其所在的分组
	 * 
	 * @param friendId			好友ID
	 * @return
	 * @throws Exception
	 */
	List<Long> getGroupByFriend(Long friendId) throws Exception;
	
	/**
	 * 根据好友ID获取此好友与分组的关系
	 * 
	 * @param friendId			好友ID
	 * @return
	 * @throws Exception
	 */
	List<Long> getGroupUserByFriendId(Long friendId) throws Exception;
	
	/**
	 * 判断两个人是否是好友
	 * 
	 * @param fristUserId			第一个用户ID
	 * @param secondUserId			第二个用户ID
	 * @return
	 * @throws Exception
	 */
	boolean isFriend(Long fristUserId, Long secondUserId) throws Exception;
}
