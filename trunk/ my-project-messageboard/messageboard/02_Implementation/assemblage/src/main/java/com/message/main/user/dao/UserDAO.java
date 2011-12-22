package com.message.main.user.dao;

import com.message.main.user.pojo.User;

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
	
}
