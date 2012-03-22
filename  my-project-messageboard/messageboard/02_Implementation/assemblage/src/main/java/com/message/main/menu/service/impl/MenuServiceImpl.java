package com.message.main.menu.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.message.main.login.pojo.LoginUser;
import com.message.main.login.web.AuthContextHelper;
import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.message.base.utils.StringUtils;
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
	private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);
	
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
		Menu menu = this.menuDAO.loadMenu(pkId);
		
		if(menu != null){
			String menuPerms = menu.getMenuPerm();
			List<Long> menuPerm = new ArrayList<Long>();
			
			if(StringUtils.isNotEmpty(menuPerms)){
				String[] perm = menuPerms.split(",");
				for(String p : perm){
					menuPerm.add(Long.valueOf(p));
				}
			}
			
			menu.setPerms(menuPerm);
		}
		return menu;
	}

	public boolean saveMenu(Menu menu, String[] menuPerms) throws Exception {
        LoginUser loginUser = AuthContextHelper.getAuthContext().getLoginUser();
		String menuPerm = StringUtils.EMPTY;
		if(menuPerms != null && menuPerms.length > 0){
			for(String perm : menuPerms){
				menuPerm += perm + ",";
			}
			
			menuPerm = menuPerm.substring(0, menuPerm.length() - 1);
		}
		if(menu.getPkId() == null){
			//新建菜单
			menu.setCreateDate(new Date());
			menu.setCreateUserId(loginUser.getPkId());
			menu.setDeleteStatus(ResourceType.DELETE_NO);
			menu.setMenuPerm(menuPerm);
			
			this.menuDAO.saveMenu(menu);
			
			return menu.getPkId() == null ? false : true;
		} else {
			//编辑菜单
			menu.setMenuPerm(menuPerm);
			this.menuDAO.updateMenu(menu);
			
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Menu> listParentMenu(Long parentId) throws Exception {
		if(parentId == null){
			logger.error("the parentId is null, this is error!");
			return Collections.EMPTY_LIST;
		}
		
		return this.menuDAO.listMenuByParentId(parentId);
	}

	public boolean deleteMenu(Long menuId) throws Exception {
		if(menuId == null){
			logger.error("the menuId is null, this is error!");
			return false;
		}
		
		Menu dbMenu = this.menuDAO.loadMenu(menuId);
		
		if(dbMenu == null){
			logger.error("there is no menu entry what pkId is " + menuId);
			return false;
		}
		
		dbMenu.setDeleteStatus(ResourceType.DELETE_YES);
		this.menuDAO.updateMenu(dbMenu);
		
		return true;
	}

	public List<Menu> getMenuTree() throws Exception {
		LoginUser loginUser = AuthContextHelper.getAuthContext().getLoginUser();
        String perm = loginUser.getIsAdmin().toString();

        List menus = this.menuDAO.listPermMenu(perm);
        Collections.sort(menus);
        menus = this.orderMenu(menus, 0L);
		return menus;
	}

    public boolean checkPerm(String perm, String menuUrl) throws Exception {
        Menu menu = null;
        try {
            menu = this.menuDAO.findMenu(menuUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(menu == null) {
            if(menuUrl.indexOf("home") != -1){
                return true;
            } else {
                return true;
                //throw new NoPermException("不存在\"" + menuUrl + "\"链接");
            }
        } else {
            String menuPerm = menu.getMenuPerm();
            String[] menuPerms = menuPerm.split(",");
            List<String> perms = Arrays.asList(menuPerms);

            if(perms.contains(perm)){
                return true;
            } else {
                return false;
            }
        }
    }

}
