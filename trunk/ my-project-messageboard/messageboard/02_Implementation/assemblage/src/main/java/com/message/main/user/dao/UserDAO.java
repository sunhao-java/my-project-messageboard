package com.message.main.user.dao;

import com.message.main.user.pojo.User;
import com.message.utils.PaginationSupport;

/**
 * 用户实体的操作DAO
 * @author sunhao(sunhao.java@gmail.com)
 */
public interface UserDAO {
	/**
	 * 用户注册
	 * @param user
	 * @return
	 * @throws Exception
	 */
	Long registerUser(User user) throws Exception;
	
	/**
	 * 根据用户名获取用户
	 * @param username
	 * @return
	 * @throws Exception
	 */
	User getUserByName(String username) throws Exception;
	
	/**
	 * 通过ID获取用户
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	User getUserById(Long userId) throws Exception;
	
	/**
	 * 更新用户
	 * @param user
	 * @throws Exception
	 */
	void updateUser(User user) throws Exception;
	
	/**
	 * 获取所有用户
	 * @param start
	 * @param num
	 * @param user
	 * @return
	 * @throws Exception
	 */
	PaginationSupport listAllUser(int start, int num, User user) throws Exception;
	
}
