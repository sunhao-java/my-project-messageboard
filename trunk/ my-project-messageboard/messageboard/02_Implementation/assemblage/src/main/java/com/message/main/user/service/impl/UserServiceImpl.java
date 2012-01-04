package com.message.main.user.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.message.main.history.service.HistoryService;
import com.message.main.user.dao.UserDAO;
import com.message.main.user.pojo.User;
import com.message.main.user.service.UserService;
import com.message.utils.DigestUtil;
import com.message.utils.WebInput;
import com.message.utils.resource.ResourceType;

public class UserServiceImpl implements UserService{
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private UserDAO userDAO;
	private HistoryService historyService;
	
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	public boolean registerUser(User user) throws Exception {
		if(user != null){
			Long pkId = null;
			try {
				if(StringUtils.isNotEmpty(user.getPassword())){
					user.setPassword(DigestUtil.MD5Encode(user.getPassword()));
				}
				user.setCreateDate(new Date());
				user.setDeleteFlag(ResourceType.DELETE_NO);
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

	public int userLogin(User user, WebInput in) throws Exception {
		if(StringUtils.isEmpty(user.getUsername())){
			return 3;		//用户名为空
		}
		User dbUser = this.userDAO.getUserByName(user.getUsername());
		String loginPsw = DigestUtil.MD5Encode(user.getPassword());
		if(dbUser == null){
			return 1;		//用户名错误
		} else {
			if(loginPsw.equals(dbUser.getPassword())){
				this.historyService.saveLoginHistory(in, dbUser);
				return 0;	//正确
			} else {
				return 2;	//密码错误
			}
		}
	}

	public User getUserById(Long userId) throws Exception {
		return this.userDAO.getUserById(userId);
	}

	public boolean checkUser(User user) throws Exception {
		String name = user.getUsername();
		User dbUser = null;
		if(StringUtils.isNotEmpty(name)){
			dbUser = this.userDAO.getUserByName(name);
		}
		return dbUser == null ? true : false;
	}

	public User getUserByName(String username) throws Exception {
		User dbUser = null;
		if(StringUtils.isNotEmpty(username)){
			dbUser = this.userDAO.getUserByName(username);
		}
		return dbUser;
	}
	
}
