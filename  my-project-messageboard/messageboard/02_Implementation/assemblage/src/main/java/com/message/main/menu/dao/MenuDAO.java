package com.message.main.menu.dao;

import java.util.List;

import com.message.main.menu.pojo.Menu;

/**
 * 菜单DAO接口
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-3-2 下午09:10:53
 */
public interface MenuDAO {
	
	/**
	 * 获取所有的菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	List<Menu> listAllMenu() throws Exception;
	
	/**
	 * 获取所有的菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	List<Long> listAllMenuIds() throws Exception;
	
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
	 * @return
	 * @throws Exception
	 */
	Menu saveMenu(Menu menu) throws Exception;
	
	/**
	 * 更新菜单
	 * 
	 * @param menu
	 * @throws Exception
	 */
	void updateMenu(Menu menu) throws Exception;
	
	/**
	 * 获取parentId指定的菜单Id集合
	 * 
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	List<Long> listMenuByParentId(Long parentId) throws Exception;

    /**
     * 获得当前登录者能看到的菜单ID
     *
     * @param perm
     * @return
     * @throws Exception
     */
    List<Long> listPermMenu(String perm) throws Exception;

    /**
     * 根据URL获取菜单
     *
     * @param menuUrl
     * @return
     * @throws Exception
     */
    Menu findMenu(String menuUrl) throws Exception;
    
    /**
     * 根据pkId删除菜单
     * 
     * @param pkId
     * @throws Exception
     */
    void deleteMenu(Long pkId) throws Exception;
	
}
