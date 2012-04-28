package com.message.main.menu.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;

import com.message.base.spring.ExtMultiActionController;
import com.message.base.web.WebInput;
import com.message.base.web.WebOutput;
import com.message.main.ResourceType;
import com.message.main.menu.pojo.Menu;
import com.message.main.menu.service.MenuService;

/**
 * 菜单的controller
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-3-2 下午09:12:43
 */
public class MenuController extends ExtMultiActionController {
	
	/**
	 * 封装HttpServletRequest
	 */
	private WebInput in;
	
	/**
	 * 封装HttpServletResponse
	 */
	private WebOutput out;
	
	/**
	 * 菜单service接口
	 */
	private MenuService menuService;
	
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	/**
	 * 进入菜单管理页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView inMenuJsp(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> params = new HashMap<String, Object>();
		return new ModelAndView("menu.list", params);
	}
	
	/**
	 * 通过ajax请求获得菜单JSON数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView ajaxLoadMenu(HttpServletRequest request, HttpServletResponse response) throws Exception{
		in = new WebInput(request);
		out = new WebOutput(request, response);
		JSONArray jsonData = this.menuService.loadMenuJsonData();
		
		out.toJson(jsonData);
		return null;
	}
	
	/**
	 * 进入编辑或者新增页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView inEditJsp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		in = new WebInput(request);
		Long pkId = in.getLong("menuId", -1L);
		
		if(Long.valueOf(-1).equals(pkId)){
			//新增页面
			//parentId为0的菜单集合
			//params.put("parentMenu", this.menuService.listParentMenu(0L));
		} else {
			//编辑页面
			Menu menu = this.menuService.loadMenu(pkId);
			params.put("menu", menu);
			//params.put("parentMenu", this.menuService.listParentMenu(menu.getParentId()));
		}
		return new ModelAndView("menu.edit", params);
	}
	
	/**
	 * 进入显示所有ICON的弹框页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView inShowIcon(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("menu.show.icon");
	}
	
	/**
	 * 保存菜单
	 * 
	 * @param request
	 * @param response
	 * @param menu
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView saveMenu(HttpServletRequest request, HttpServletResponse response, Menu menu) throws Exception{
		in = new WebInput(request);
		out = new WebOutput(request, response);
		String[] menuPerms = in.getStrings("menuPerm");
		
		JSONObject params = new JSONObject();
		boolean status = this.menuService.saveMenu(menu, menuPerms);
		
		params.put(ResourceType.AJAX_STATUS, status ? ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE);
		
		out.toJson(params);
		return null;
	}
	
	/**
	 * ajax软删除菜单的操作
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) throws Exception{
		in = new WebInput(request);
		out = new WebOutput(request, response);
		JSONObject params = new JSONObject();
		
		Long menuId = in.getLong("menuId", Long.valueOf(0));
		
		boolean status = this.menuService.deleteMenu(menuId);
		
		params.put(ResourceType.AJAX_STATUS, status ? ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE);
		out.toJson(params);
		return null;
	}
}
