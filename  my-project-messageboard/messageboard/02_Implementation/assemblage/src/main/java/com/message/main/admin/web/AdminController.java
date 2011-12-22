package com.message.main.admin.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.message.main.admin.pojo.Admin;
import com.message.main.admin.service.AdminService;

public class AdminController extends MultiActionController {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	private AdminService adminService;

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}
	
	/**
	 * 进入管理员列表页面
	 * @param in
	 * @param out
	 * @param admin
	 * @return
	 */
	public ModelAndView inListAdminJsp(HttpServletRequest request, HttpServletResponse response, Admin admin){
		Map<String, Object> params = new HashMap<String, Object>();
		List<Admin> admins = null;
		try {
			admins = this.adminService.getAdmins();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		params.put("admins", admins);
		return new ModelAndView("message.list.admin", params);
	}
	
	/**
	 * 进入编辑管理员的页面
	 * @param request
	 * @param response
	 * @param admin
	 * @return
	 */
	public ModelAndView inEditAdminJsp(HttpServletRequest request, HttpServletResponse response, Admin admin) {
		Map<String, Object> params = new HashMap<String, Object>();
		return new ModelAndView("", params);
	}

}
