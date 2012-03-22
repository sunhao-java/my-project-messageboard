package com.message.main.login.web;

import com.message.main.login.pojo.LoginUser;

/**
 * 存放当前登录者
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-3-4 上午09:08:31
 */
public class AuthContext {
	private LoginUser loginUser;

	public LoginUser getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(LoginUser loginUser) {
		this.loginUser = loginUser;
	}
}
