package com.message.main.message.service;

import com.message.base.pagination.PaginationSupport;
import com.message.main.message.pojo.Message;
import com.message.main.user.pojo.User;

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
	 * @param user
	 * @return
	 * @throws Exception
	 */
	Long saveMessage(Message message, User user) throws Exception;
	
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
	 * @param user
	 * @throws Exception
	 */
	void deleteMessage(String pkIds, User user) throws Exception;
	
	/**
	 * 获取我的留言
	 * @param start
	 * @param num
	 * @param user
	 * @param message
	 * @return
	 * @throws Exception
	 */
	PaginationSupport getMyMessages(int start, int num, User user, Message message) throws Exception;
	
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
	 * @param user
	 * @throws Exception
	 */
	void setAudit(Long messageId, String status, User user) throws Exception;

    /**
     * 获取留言sequence的下一个主键值
     *
     * @return
     * @throws Exception
     */
    Long getPkId() throws Exception;
	
}
