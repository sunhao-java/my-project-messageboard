package com.message.main.user.dao.impl;

import com.message.base.hibernate.impl.GenericHibernateDAOImpl;
import com.message.main.user.dao.UserPrivacyDAO;
import com.message.main.user.pojo.User;
import com.message.main.user.pojo.UserPrivacy;

/**
 * 用户隐私设置DAO的实现
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-2-10 下午09:11:14
 */
public class UserPrivacyDAOImpl extends GenericHibernateDAOImpl implements
		UserPrivacyDAO {

	public UserPrivacy getUserPrivacy(Long pkId) throws Exception {
		return (UserPrivacy) this.loadObject(UserPrivacy.class, pkId);
	}

	public void saveUserPrivacy(UserPrivacy userPrivacy) throws Exception {
		this.saveObject(userPrivacy);
	}

	public void deleteUserPrivacy(UserPrivacy userPrivacy) throws Exception {
		this.deleteObject(userPrivacy);
	}

}
