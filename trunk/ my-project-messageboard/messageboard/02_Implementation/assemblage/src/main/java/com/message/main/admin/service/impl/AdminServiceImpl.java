package com.message.main.admin.service.impl;

import java.util.List;

import com.message.main.admin.dao.AdminDAO;
import com.message.main.admin.pojo.Admin;
import com.message.main.admin.service.AdminService;

public class AdminServiceImpl implements AdminService {
	private AdminDAO adminDAO;

	public void setAdminDAO(AdminDAO adminDAO) {
		this.adminDAO = adminDAO;
	}

	public boolean deleteAdminByPkId(Long pkId) throws Exception {
		return this.adminDAO.deleteAdminByPkId(pkId);
	}

	public boolean deleteAdminByPkIds(Long[] pkIds) throws Exception {
		if(pkIds != null && pkIds.length > 0) {
			for(Long pkId : pkIds) {
				this.adminDAO.deleteAdminByPkId(pkId);
			}
		}
		return true;
	}

	public Admin getAdminByName(String username) throws Exception {
		if(username != null){
			return this.adminDAO.getAdminByName(username);
		}
		return null;
	}

	public Admin getAdminByPkId(Long pkId) throws Exception {
		if(pkId != null){
			return this.adminDAO.getAdminByPkId(pkId);
		}
		return null;
	}

	public List<Admin> getAdmins() throws Exception {
		return this.adminDAO.getAdmins();
	}

	public boolean saveAdmin(Admin admin) throws Exception {
		if(admin != null){
			return this.adminDAO.saveAdmin(admin);
		}
		return false;
	}

	public boolean updateAdmin(Admin admin) throws Exception {
		if(admin != null){
			if(this.adminDAO.getAdminByPkId(admin.getPkId()) != null){
				return this.adminDAO.updateAdmin(admin);
			}
		}
		return false;
	}

}
