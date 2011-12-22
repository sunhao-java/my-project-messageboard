package com.message.main.user.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.message.main.user.dao.UserDAO;
import com.message.main.user.pojo.User;
import com.message.utils.base.utils.impl.GenericHibernateDAOImpl;

public class UserDAOImpl extends GenericHibernateDAOImpl implements UserDAO {
	private Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

	public Long registerUser(User user) throws Exception {
		try {
			this.saveObject(user);
			
			return user.getPkId();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return null;
		}
	}

}
