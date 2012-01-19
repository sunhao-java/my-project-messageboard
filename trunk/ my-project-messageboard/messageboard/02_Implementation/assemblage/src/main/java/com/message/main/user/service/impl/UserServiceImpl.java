package com.message.main.user.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.message.base.pagination.PaginationSupport;
import com.message.base.web.WebInput;
import com.message.main.history.service.HistoryService;
import com.message.main.message.service.MessageService;
import com.message.main.user.dao.UserDAO;
import com.message.main.user.pojo.User;
import com.message.main.user.service.UserService;
import com.message.utils.MD5Utils;
import com.message.utils.StringUtils;
import com.message.utils.resource.ResourceType;

public class UserServiceImpl implements UserService{
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private UserDAO userDAO;
	private HistoryService historyService;
	private MessageService messageService;
	
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public boolean registerUser(User user) throws Exception {
		if(user != null){
			Long pkId = null;
			try {
				if(StringUtils.isNotEmpty(user.getPassword())){
					user.setPassword(MD5Utils.MD5Encode(user.getPassword()));
				}
				user.setCreateDate(new Date());
				user.setDeleteFlag(ResourceType.DELETE_NO);
				user.setIsAdmin(ResourceType.IS_ADMIN_NO);
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
		String loginPsw = MD5Utils.MD5Encode(user.getPassword());
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

	public User addLoginInfo(User user) throws Exception {
		if(user != null){
			user.setLastLoginTime(this.historyService.getLastLoginTime(user.getPkId()));
			user.setLoginCount(this.historyService.getLoginCount(user.getPkId()));
			user.setMessageCount(this.messageService.getLoginUserMessageCount(user.getPkId()));
		}
		return user;
	}

	public void saveEdit(User user) throws Exception {
		User dbUser = null;
		if(user != null){
			if(user.getPkId() != null){
				dbUser = this.userDAO.getUserById(user.getPkId());
				if(dbUser != null){
					//TODO by sunhao: 头像是可以修改的，暂时这样
					dbUser.setAddress(user.getAddress());
					dbUser.setPhoneNum(user.getPhoneNum());
					dbUser.setEmail(user.getEmail());
					dbUser.setQq(user.getQq());
					dbUser.setHomePage(user.getHomePage());
					
					this.userDAO.updateUser(dbUser);
				}
			}
		}
	}

	public boolean savePassword(User user) throws Exception {
		if(StringUtils.isNotEmpty(user.getPassword()) && user.getPkId() != null){
			User dbUser = this.userDAO.getUserById(user.getPkId());
			if(dbUser != null){
				dbUser.setPassword(MD5Utils.MD5Encode(user.getPassword()));
				this.userDAO.updateUser(dbUser);
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public PaginationSupport listAllUser(int start, int num, User user)
			throws Exception {
		PaginationSupport paginationSupport = this.userDAO.listAllUser(start, num, user);
		return paginationSupport;
	}

	public boolean deleteUser(String pkids) throws Exception {
		String[] pkIds = pkids.split(",");
		if(pkIds != null && pkIds.length > 0){
			for(String pkId : pkIds){
				User dbUser = this.userDAO.getUserById(Long.valueOf(pkId));
				
				if(dbUser != null){
					dbUser.setDeleteFlag(ResourceType.DELETE_YES);
					this.userDAO.updateUser(dbUser);
				}
			}
			return true;
		} 
		
		return false;
	}
	
}
