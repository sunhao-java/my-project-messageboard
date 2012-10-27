package com.message.main.letter.dao;


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
}
