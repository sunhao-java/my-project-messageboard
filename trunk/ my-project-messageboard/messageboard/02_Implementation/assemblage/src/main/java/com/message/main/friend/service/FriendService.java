package com.message.main.friend.service;

import java.util.List;

import com.message.base.pagination.PaginationSupport;
import com.message.main.friend.po.Friend;
import com.message.main.friend.po.FriendApply;
import com.message.main.friend.po.FriendGroup;
import com.message.main.login.pojo.LoginUser;


/**
 * 好友模块service.
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-7-21 上午03:48:48
 */
public interface FriendService {
	
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
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	int getNoGroupFriendNum(LoginUser loginUser) throws Exception;
	
	/**
	 * 获取好友
	 * 
	 * @param fid				好友对象ID
	 * @return
	 * @throws Exception
	 */
	Friend getFriend(Long fid) throws Exception;
	
	/**
	 * 获取好友申请
	 * 
	 * @param faid				好友申请对象ID
	 * @return
	 * @throws Exception
	 */
	FriendApply getFriendApply(Long faid) throws Exception;
	
	/**
	 * 获取某个用户的好友的ID集合
	 * 
	 * @param userId
	 * @param start
	 * @param num
	 * @return
	 * @throws Exception
	 */
	List<Long> listFriendIds(Long userId) throws Exception;
	
	/**
	 * 获取申请表中的好友(按result查询)
	 * 
	 * @param userId			查看者ID
	 * @param result			申请结果(仅限0和2,即申请中和已拒绝)
	 * @return
	 * @throws Exception
	 */
	List<Long> listApplyFriendIds(Long userId, Integer result) throws Exception;
	
	/**
	 * 获取正在申请中的好友
	 * 
	 * @param userId			查看者ID
	 * @param result			申请结果(仅限0和2,即申请中和已拒绝)
	 * @return
	 * @throws Exception
	 */
	PaginationSupport listApplyFriends(Long userId, Integer result, int start, int num) throws Exception;
	
	/**
	 * 保存申请好友的信息
	 * 
	 * @param selectedUserIds		选择要申请的用户ID
	 * @param applyMessage			申请好友时的附言
	 * @param isEmailNotify			是否用邮件通知此人
	 * @param loginUser				当前登录者
	 * @param faid					好友申请ID
	 * @return
	 * @throws Exception
	 */
	boolean saveApplyFriends(Long[] selectedUserIds, String applyMessage, boolean isEmailNotify, LoginUser loginUser, Long faid) throws Exception;
	
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
	 * 取消好友申请
	 * 
	 * @param faid
	 * @return
	 * @throws Exception
	 */
	boolean cancelRequest(Long faid) throws Exception;
	
	/**
     * 处理同意还是拒绝好友请求
     * 
     * @param loginUser			当前登录者
     * @param friendId          好友申请ID
     * @param agreeFlag         拒绝还是同意
     * @param disAgreeMessage   拒绝理由
     * @return
     * @throws Exception
     */
    boolean ajaxHandleRequest(LoginUser loginUser, Long friendId, Integer agreeFlag, String disAgreeMessage) throws Exception;
    
    /**
     * 删除好友
     * 
     * @param loginUser			当前登录者
     * @param friendId			好友的ID
     * @return
     * @throws Exception
     */
    boolean deleteFriend(LoginUser loginUser, Long friendId) throws Exception;
    
    /**
     * 保存用户的分组
     * 
     * @param groupName			组名
     * @param loginUser			登录者
     * @return
     * @throws Exception
     */
    Long saveGroup(String groupName, LoginUser loginUser) throws Exception;
    
    /**
     * 获取loginUser的全部分组
     * 
     * @param loginUser			登录者
     * @param start				
     * @param num
     * @return
     * @throws Exception
     */
    PaginationSupport getFriendGroups(LoginUser loginUser, int start, int num) throws Exception;
    
    /**
	 * 获取好友分组
	 * 
	 * @param fgid				好友分组对象ID
	 * @return
	 * @throws Exception
	 */
	FriendGroup getFriendGroup(Long fgid) throws Exception;
	
	/**
	 * 好友分组的删除编辑操作
	 * 
	 * @param groupId			分组ID
	 * @param groupName			组名
	 * @param action			动作名称(edit,delete)
	 * @return
	 * @throws Exception
	 */
	boolean groupFunc(Long groupId, String groupName, String action) throws Exception;
	
	/**
	 * 保存用户好友分组
	 * 
	 * @param loginUser			登录者
	 * @param friendId			好友ID
	 * @param groups			分组ID
	 * @return
	 * @throws Exception
	 */
	boolean saveFriendGroup(LoginUser loginUser, Long friendId, Long[] groups) throws Exception;
	
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
	List<FriendGroup> getGroupByFriend(Long friendId) throws Exception;
	
	
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
