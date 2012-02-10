package com.message.main.user.service.impl;

import com.message.main.user.dao.UserPrivacyDAO;
import com.message.main.user.pojo.User;
import com.message.main.user.pojo.UserPrivacy;
import com.message.main.user.service.UserPrivacyService;
import com.message.resource.ResourceType;

/**
 * 用户隐私设置的Serviced的实现
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-2-10 下午09:10:53
 */
public class UserPrivacyServiceImpl implements UserPrivacyService {
	private UserPrivacyDAO userPrivacyDAO;

	public void setUserPrivacyDAO(UserPrivacyDAO userPrivacyDAO) {
		this.userPrivacyDAO = userPrivacyDAO;
	}

	public UserPrivacy getUserPrivacyByUser(User user) throws Exception {
		return this.userPrivacyDAO.getUserPrivacyByUser(user);
	}

	public void saveUserPrivacy(UserPrivacy userPrivacy, User user) throws Exception {
		if(user != null){
			UserPrivacy dbUserPrivacy = this.getUserPrivacyByUser(user);
			if(dbUserPrivacy != null){
				this.userPrivacyDAO.deleteUserPrivacy(dbUserPrivacy);
			}
			userPrivacy.setUsername(ResourceType.LOOK_ALL_PROPLE);
			userPrivacy.setUserPkId(user.getPkId());
		}
		this.userPrivacyDAO.saveUserPrivacy(userPrivacy);
	}

}
