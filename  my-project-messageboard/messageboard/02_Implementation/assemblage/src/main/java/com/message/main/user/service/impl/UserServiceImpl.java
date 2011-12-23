package com.message.main.user.service.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.message.main.user.dao.UserDAO;
import com.message.main.user.pojo.User;
import com.message.main.user.service.UserService;
import com.message.utils.DigestUtil;

public class UserServiceImpl implements UserService{
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private UserDAO userDAO;

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public boolean registerUser(User user) throws Exception {
		if(user != null){
			Long pkId = null;
			try {
				pkId = this.userDAO.registerUser(user);
				if(pkId != null){
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
				return false;
			}
		}
		return false;
	}

	public int userLogin(User user) throws Exception {
		if(StringUtils.isEmpty(user.getUsername())){
			return 3;		//用户名为空
		}
		User dbUser = this.userDAO.getUserByName(user.getUsername());
		String loginPsw = DigestUtil.MD5Encode(user.getPassword());
		if(dbUser == null){
			return 1;		//用户名错误
		} else {
			if(loginPsw.equals(dbUser.getPassword())){
				return 0;	//正确
			} else {
				return 2;	//密码错误
			}
		}
	}
	
}
