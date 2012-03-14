package com.message.interceptor;

import com.message.main.user.pojo.User;

/**
 * 存放当前登录者
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-3-4 上午09:08:31
 */
public class AuthContext {
	private User loginUser;

	public User getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}
}
