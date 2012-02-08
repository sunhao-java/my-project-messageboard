package com.message.main.user.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.message.base.hibernate.impl.GenericHibernateDAOImpl;
import com.message.base.pagination.PaginationSupport;
import com.message.main.user.dao.UserDAO;
import com.message.main.user.pojo.User;
import com.message.resource.ResourceType;

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

	public PaginationSupport listAllUser(int start, int num, User user)
			throws Exception {
		String hql = "from User u where u.deleteFlag = :deleteFlag order by u.pkId desc ";
		String countHql = "select count(*) from User u where u.deleteFlag = :deleteFlag order by u.pkId desc ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deleteFlag", ResourceType.DELETE_NO);
		return this.getPaginationSupport(hql, countHql, start, num, params);
	}

}
