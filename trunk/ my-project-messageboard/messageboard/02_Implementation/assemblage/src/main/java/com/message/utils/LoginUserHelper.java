package com.message.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.message.base.utils.RequestUtils;
import com.message.base.web.WebInput;
import com.message.main.ResourceType;
import com.message.main.login.pojo.LoginUser;
import com.message.main.login.web.AuthContextHelper;
import com.message.main.user.pojo.User;

/**
 * .
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-8-21 下午09:53:27
 */
public class LoginUserHelper {
	private static final Logger logger = LoggerFactory.getLogger(LoginUserHelper.class);
	
	/**
	 * 同步session中的loginUser
	 * 
	 * @param user
	 * @param in
	 */
	public static void syncLoginUser(User user, WebInput in){
		LoginUser loginUser = AuthContextHelper.getAuthContext().getLoginUser();
		
		if(loginUser == null){
			logger.debug("loginUser is null!");
			return;
		}
		
		LoginUser lu = new LoginUser(user, loginUser.getLastLoginTime(), loginUser.getLoginCount(), 0, in.getClientIP());
		
		RequestUtils.syncToSession(in, ResourceType.LOGIN_USER_KEY_IN_SESSION, lu, 1 * 60 * 60 * 1000);
	}
}
