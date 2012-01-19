package com.message.main.admin.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.message.base.hibernate.impl.GenericHibernateDAOImpl;
import com.message.main.admin.dao.AdminDAO;
import com.message.main.admin.pojo.Admin;
import com.message.utils.resource.ResourceType;

/**
 * 对管理员的数据库持久化操作的DAO
 * @author sunhao(sunhao.java@gmail.com)
 */
public class AdminDAOImpl extends GenericHibernateDAOImpl implements AdminDAO {
	private static final Logger log = LoggerFactory.getLogger(AdminDAOImpl.class);

	public boolean deleteAdminByPkId(Long pkId) {
		String sql = "update t_message_admin set delete_flag = :deleteFlag where pk_id = :pkId";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deleteFlag", ResourceType.DELETE_YES);
		params.put("pkId", pkId);
		try {
			int result = this.updateByNativeSQL(sql, params);
			
			return result == 1 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public Admin getAdminByName(String username) {
		String hql = "From Admin where username = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(username);
		List<Admin> adminList = null;
		try {
			adminList = this.findByHQL(hql, params);
			if(CollectionUtils.isNotEmpty(adminList)){
				Admin admin = adminList.get(0);
				
				return admin;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			return null;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Admin getAdminByPkId(Long pkId) {
		String hql = "From Admin where pkId = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(pkId);
		List<Admin> adminList = null;
		try {
			adminList = this.findByHQL(hql, params);
			if(CollectionUtils.isNotEmpty(adminList)){
				Admin admin = adminList.get(0);
				
				return admin;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			return null;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Admin> getAdmins() {
		String hql = "From Admin where deleteFlag = ?";
		List params = new ArrayList();
		params.add(ResourceType.DELETE_NO);
		List<Admin> adminList = null;
		try {
			adminList = this.findByHQL(hql, params);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return adminList;
	}

	public boolean saveAdmin(Admin admin) {
		this.saveObject(admin);
		return admin.getPkId() == null ? false : true;
	}

	public boolean updateAdmin(Admin admin) {
		try {
			this.updateObject(admin);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			return false;
		}
	}

}
