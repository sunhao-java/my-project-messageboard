package com.message.main.menu.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;

import com.message.main.menu.dao.MenuDAO;
import com.message.main.menu.pojo.Menu;
import com.message.main.menu.service.MenuService;
import com.message.main.user.pojo.User;
import com.message.resource.ResourceType;

/**
 * 菜单service实现
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-3-2 下午09:12:34
 */
public class MenuServiceImpl implements MenuService {
	
	/**
	 * 菜单DAO接口
	 */
	private MenuDAO menuDAO;

	public void setMenuDAO(MenuDAO menuDAO) {
		this.menuDAO = menuDAO;
	}

	public JSONArray loadMenuJsonData() throws Exception {
		List<Menu> menus = this.getOrderMenu(0L);
		JSONArray jsons = new JSONArray();
		if (menus.size() > 0) {
			for (Menu menu : menus) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", menu.getPkId());
				map.put("text", menu.getMenuName());
				if (CollectionUtils.isNotEmpty(menu.getChildren())) {
					map.put("children", this.makeMenuChildrenJson(menu.getPkId(), menu.getChildren()));
				}
				jsons.add(map);
			}
		}
		return jsons;
	}

	/**
	 * 组装某个菜单的子的json数据
	 * 
	 * @param typeId
	 * @param menus
	 * @return
	 * @throws Exception
	 */
	private JSONArray makeMenuChildrenJson(Long typeId, List<Menu> menus) throws Exception {
		JSONArray jsons = new JSONArray();
		if (CollectionUtils.isNotEmpty(menus)) {
			for (Menu menu : menus) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", menu.getPkId());
				map.put("text", menu.getMenuName());
				if(CollectionUtils.isNotEmpty(menus)){
					map.put("children", this.makeMenuChildrenJson(menu.getPkId(), menu.getChildren()));
				}
				jsons.add(map);
			}
		}

		return jsons;
	}
	
	/**
	 * 获得已排序的菜单树
	 * 
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Menu> getOrderMenu(Long parentId) throws Exception{
		List menus = this.menuDAO.listAllMenu();
		Collections.sort(menus);
		return this.orderMenu(menus, parentId);
	}
	
	/**
	 * 递归排序菜单
	 * 
	 * @param unOrderMenu
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Menu> orderMenu(List unOrderMenu, Long parentId) throws Exception {
		List<Menu> orderMenu = new ArrayList<Menu>();
		
		for(int i = 0; i < unOrderMenu.size(); i++){
			Menu menu = (Menu) unOrderMenu.get(i);
			if(parentId.equals(menu.getParentId())) {
				List children = new ArrayList();
				children = this.orderMenu(unOrderMenu, menu.getPkId());
				menu.setChildren(children);
				orderMenu.add(menu);
			}
		}
		
		return orderMenu;
	}

	public Menu loadMenu(Long pkId) throws Exception {
		return this.menuDAO.loadMenu(pkId);
	}

	public boolean saveMenu(Menu menu, User loginUser) throws Exception {
		if(menu.getPkId() == null){
			//新建菜单
			menu.setCreateDate(new Date());
			menu.setCreateUserId(loginUser.getPkId());
			menu.setDeleteStatus(ResourceType.DELETE_NO);
			
			this.menuDAO.saveMenu(menu);
			
			return menu.getPkId() == null ? false : true;
		} else {
			//编辑菜单
			this.menuDAO.updateMenu(menu);
			
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Menu> listParentMenu(Long parentId) throws Exception {
		if(parentId == null){
			return Collections.EMPTY_LIST;
		}
		
		return this.menuDAO.listMenuByParentId(parentId);
	}

}
