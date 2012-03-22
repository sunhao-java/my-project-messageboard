package com.message.main.user.service.impl;

import com.message.main.login.pojo.LoginUser;
import com.message.main.login.web.AuthContextHelper;
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

	public UserPrivacy getUserPrivacy(Long pkId) throws Exception {
        LoginUser loginUser = AuthContextHelper.getAuthContext().getLoginUser();
		return this.userPrivacyDAO.getUserPrivacy(pkId == null ? loginUser.getPkId() : pkId);
	}

	public void saveUserPrivacy(UserPrivacy userPrivacy) throws Exception {
        LoginUser loginUser = AuthContextHelper.getAuthContext().getLoginUser();

        UserPrivacy dbUserPrivacy = this.getUserPrivacy(loginUser.getPkId());
        if(dbUserPrivacy != null){
            this.userPrivacyDAO.deleteUserPrivacy(dbUserPrivacy);
        }
        userPrivacy.setUsername(ResourceType.LOOK_ALL_PROPLE);
        userPrivacy.setUserPkId(loginUser.getPkId());
		this.userPrivacyDAO.saveUserPrivacy(userPrivacy);
	}

}
