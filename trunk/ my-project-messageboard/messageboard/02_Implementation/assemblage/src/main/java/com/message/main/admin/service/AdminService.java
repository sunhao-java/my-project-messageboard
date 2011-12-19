package com.message.main.admin.service;

import java.util.List;

import com.message.main.admin.pojo.Admin;

public interface AdminService {
	/**
	 * 获取所有管理员
	 * @return
	 */
	List<Admin> getAdmins() throws Exception;
	
	/**
	 * 根据ID获取管理员
	 * @param pkId
	 * @return
	 */
	Admin getAdminByPkId(Long pkId) throws Exception;
	
	/**
	 * 根据用户名获取管理员
	 * @param username
	 * @return
	 */
	Admin getAdminByName(String username) throws Exception;
	
	/**
	 * 保存管理员
	 * @param admin
	 * @return true:success false:failure
	 */
	boolean saveAdmin(Admin admin) throws Exception;
	
	/**
	 * 根据ID删除管理员
	 * @param pkId
	 * @return
	 */
	boolean deleteAdminByPkId(Long pkId) throws Exception;
	
	/**
	 * 根据ID批量删除管理员
	 * @param pkIds
	 * @return
	 */
	boolean deleteAdminByPkIds(Long[] pkIds) throws Exception;
	
	/**
	 * 编辑管理员
	 * @param admin
	 * @return true:success false:failure
	 */
	boolean updateAdmin(Admin admin) throws Exception;
}
