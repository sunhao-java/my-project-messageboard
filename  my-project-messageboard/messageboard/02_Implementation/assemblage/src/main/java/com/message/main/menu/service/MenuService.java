package com.message.main.menu.service;

import java.util.List;

import net.sf.json.JSONArray;

import com.message.main.menu.pojo.Menu;

/**
 * 菜单service接口
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-3-2 下午09:12:01
 */
public interface MenuService {
	
	/**
	 * 获取菜单显示树的json数据
	 * 
	 * @return
	 * @throws Exception
	 */
	JSONArray loadMenuJsonData() throws Exception;
	
	/**
	 * 根据ID查到菜单
	 * 
	 * @param pkId
	 * @return
	 * @throws Exception
	 */
	Menu loadMenu(Long pkId) throws Exception;

	/**
	 * 保存菜单
	 * 
	 * @param menu			要保持的菜单
	 * @param menuPerms		菜单权限
	 * @return
	 * @throws Exception
	 */
	boolean saveMenu(Menu menu, String[] menuPerms) throws Exception;
	
	/**
	 * 获取parentId指定的菜单集合
	 * 
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	List<Menu> listParentMenu(Long parentId) throws Exception;
	
	/**
	 * 删除菜单的操作，软删除
	 * 
	 * @param menuId		菜单ID
	 * @return
	 * @throws Exception
	 */
	boolean deleteMenu(Long menuId) throws Exception;
	
	/**
	 * 当用户登录后获取其可见菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	List<Menu> getMenuTree() throws Exception;

    /**
     * 检查登录用户是否具有访问此URL的权限
     *
     * @param perm
     * @param menuUrl
     * @return
     * @throws Exception
     */
    boolean checkPerm(String perm, String menuUrl) throws Exception;

}
