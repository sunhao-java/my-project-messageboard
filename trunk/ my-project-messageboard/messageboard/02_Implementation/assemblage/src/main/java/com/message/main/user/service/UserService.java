package com.message.main.user.service;

import com.message.main.user.pojo.User;

public interface UserService {
	/**
	 * 用户注册
	 * @param user
	 * @return
	 * @throws Exception
	 */
	boolean registerUser(User user) throws Exception;
	
	/**
	 * 用户登录
	 * @param user
	 * @return 0:成功	1:用户名错误		2:密码错误
	 * @throws Exception
	 */
	int userLogin(User user) throws Exception;
	
	/**
	 * 通过ID获取用户
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	User getUserById(Long userId) throws Exception;
	
	/**
	 * 判断用户名是否存在
	 * @param user
	 * @return
	 * @throws Exception
	 */
	boolean checkUser(User user) throws Exception;
	
}
