package com.message.main.letter.dao;

import java.io.Serializable;
import java.util.List;

import com.message.base.pagination.PaginationSupport;
import com.message.main.letter.pojo.LetterUserRelation;


/**
 * 站内信的DAO接口.
 * 
 * @author Danny(sunhao)(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-10-22 下午09:21:16
 */
public interface LetterDAO {
	/**
	 * 保存对象
	 * 
	 * @param <T>			对象类型
	 * @param entity		对象实体
	 * @return
	 * @throws Exception
	 */
	<T> T save(T entity) throws Exception;
	
	/**
	 * 根据用户ID获取此人的收件箱内容
	 * 
	 * @param userId		用户ID
	 * @param start
	 * @param num
	 * @return
	 * @throws Exception
	 */
	PaginationSupport getInbox(Long userId, int start, int num) throws Exception;
	
	/**
	 * 根据class和主键获取DB中的对象
	 * 
	 * @param <T>			要获取对象的类型
	 * @param clazz			要获取对象的class
	 * @param pkId			主键
	 * @return
	 * @throws Exception
	 */
	<T> T loadObject(Class clazz, Serializable pkId) throws Exception;
	
	/**
	 * 获取某一条站内信的接收者
	 * 
	 * @param letterId		站内信ID
	 * @return
	 * @throws Exception
	 */
	List<LetterUserRelation> getReleationByLetter(Long letterId) throws Exception;
	
	/**
	 * 获取某个接收者与指定站内信的关系
	 * 
	 * @param letterId		站内信ID
	 * @param receiverId	人员ID
	 * @return
	 * @throws Exception
	 */
	LetterUserRelation getRelation(Long letterId, Long receiverId) throws Exception;
	
	/**
	 * 更新对象
	 * 
	 * @param obj			要更新的对象
	 * @throws Exception
	 */
	void updateObject(Object obj) throws Exception;
	
	/**
	 * 删除收件箱中的站内信
	 * 
	 * @param letterIds		站内信IDs
	 * @param receiverId	接收者ID
	 * @return
	 * @throws Exception
	 */
	boolean deleteInbox(List<Long> letterIds, Long receiverId) throws Exception;
}
