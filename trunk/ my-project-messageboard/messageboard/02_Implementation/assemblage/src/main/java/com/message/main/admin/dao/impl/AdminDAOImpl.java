package com.message.main.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.message.main.admin.dao.AdminDAO;
import com.message.main.admin.pojo.Admin;
import com.message.utils.resource.ResourceType;
import com.message.utils.spring.SpringHibernateUtils;

/**
 * 对管理员的数据库持久化操作的DAO
 * @author sunhao(sunhao.java@gmail.com)
 */
public class AdminDAOImpl extends SpringHibernateUtils implements AdminDAO {
	private static final Logger log = LoggerFactory.getLogger(AdminDAOImpl.class);

	public boolean deleteAdminByList(List<Long> pkIds) {
		return false;
	}

	public boolean deleteAdminByPkId(Long pkId) {
		return false;
	}

	public Admin getAdminByName(String username) {
		return null;
	}

	public Admin getAdminByPkId(Long pkId) {
		
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Admin> getAdmins() {
		String hql = "From Admin where deleteFlag = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(ResourceType.DELETE_NO);
		List<Admin> adminList = null;
		try {
			adminList = (List<Admin>) this.findByHQL(hql, params);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return adminList;
	}

	public boolean saveAdmin(Admin admin) {
		return false;
	}

	public boolean updateAdmin(Admin admin) {
		return false;
	}

}
