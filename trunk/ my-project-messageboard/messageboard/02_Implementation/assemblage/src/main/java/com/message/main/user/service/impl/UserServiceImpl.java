package com.message.main.user.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.message.main.user.dao.UserDAO;
import com.message.main.user.pojo.User;
import com.message.main.user.service.UserService;

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
	
}
