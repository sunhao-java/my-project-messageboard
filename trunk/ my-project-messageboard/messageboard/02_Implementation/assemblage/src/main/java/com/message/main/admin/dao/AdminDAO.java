package com.message.main.admin.dao;

import java.util.List;

import com.message.main.admin.pojo.Admin;

/**
 * 对管理员的数据库持久化操作的DAO
 * @author sunhao(sunhao.java@gmail.com)
 */
public interface AdminDAO {
	/**
	 * 获取所有管理员
	 * @return
	 */
	List<Admin> getAdmins();
	
	/**
	 * 根据ID获取管理员
	 * @param pkId
	 * @return
	 */
	Admin getAdminByPkId(Long pkId);
	
	/**
	 * 根据用户名获取管理员
	 * @param username
	 * @return
	 */
	Admin getAdminByName(String username);
	
	/**
	 * 保存管理员
	 * @param admin
	 * @return true:success false:failure
	 */
	boolean saveAdmin(Admin admin);
	
	/**
	 * 根据ID删除管理员
	 * @param pkId
	 * @return
	 */
	boolean deleteAdminByPkId(Long pkId);
	
	/**
	 * 编辑管理员
	 * @param admin
	 * @return true:success false:failure
	 */
	boolean updateAdmin(Admin admin);
	
}
