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
}
