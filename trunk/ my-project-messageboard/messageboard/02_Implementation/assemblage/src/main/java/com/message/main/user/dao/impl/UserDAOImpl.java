package com.message.main.user.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
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

	@SuppressWarnings("unchecked")
	public User getUserByName(String username) throws Exception {
		String hql = "from User where username = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(username);
		List list = this.findByHQL(hql, params);
		if(CollectionUtils.isNotEmpty(list)){
			return (User) list.get(0);
		}
		return null;
	}

	public User getUserById(Long userId) throws Exception {
		return (User) this.loadObject(User.class, userId);
	}

	public void updateUser(User user) throws Exception {
		this.updateObject(user);
	}

}
