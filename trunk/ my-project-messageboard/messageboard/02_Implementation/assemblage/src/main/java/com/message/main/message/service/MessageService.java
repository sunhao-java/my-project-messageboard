package com.message.main.message.service;

import com.message.base.pagination.PaginationSupport;
import com.message.main.login.pojo.LoginUser;
import com.message.main.message.pojo.Message;

/**
 * 留言操作的service 
 * @author sunhao(sunhao.java@gmail.com)
 */
public interface MessageService {
	
	/**
	 * 获取所有的留言
	 * @return
	 * @throws Exception
	 */
	PaginationSupport getAllMessages(int start, int num, Message message) throws Exception;
	
	/**
	 * 保存留言
	 * @param message
	 * @return
	 * @throws Exception
	 */
	Long saveMessage(Message message) throws Exception;
	
	/**
	 * 获取某个用户的留言条数
	 * @param pkId
	 * @return
	 * @throws Exception
	 */
	int getLoginUserMessageCount(Long pkId) throws Exception;
	
	/**
	 * 根据ID获取某个留言
	 * @param pkId
	 * @return
	 */
	Message getMessageByPkId(Long pkId) throws Exception;
	
	/**
	 * 根据pkId的集合批量删除留言
	 * @param pkIds
	 * @throws Exception
	 */
	void deleteMessage(String pkIds) throws Exception;
	
	/**
	 * 获取我的留言
	 * @param start
	 * @param num
	 * @param userId
	 * @param message
	 * @return
	 * @throws Exception
	 */
	PaginationSupport getMyMessages(int start, int num, Long userId, Message message) throws Exception;
	
	/**
	 * 列出所有待审核的或者审核未通过的留言
	 * @param start
	 * @param num
	 * @param message
	 * @param flag		标识是获取待审核的还是审核未通过的留言
	 * @return
	 * @throws Exception
	 */
	PaginationSupport listToAuditMessage(int start, int num, Message message, boolean flag) throws Exception;
	
	/**
	 * 对留言进行审批
	 * @param messageId
	 * @param status
	 * @throws Exception
	 */
	void setAudit(Long messageId, String status) throws Exception;

    /**
     * 获取留言sequence的下一个主键值
     *
     * @return
     * @throws Exception
     */
    Long getPkId() throws Exception;
	
    /**
     * 列出当前登录人好友的所有博客
     * 
     * @param loginUser
     * @return
     * @throws Exception
     */
    PaginationSupport listFriendsMessage(LoginUser loginUser) throws Exception;
}
