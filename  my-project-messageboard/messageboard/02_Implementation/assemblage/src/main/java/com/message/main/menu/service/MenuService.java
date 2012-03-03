package com.message.main.menu.service;

import java.util.List;

import net.sf.json.JSONArray;

import com.message.main.menu.pojo.Menu;
import com.message.main.user.pojo.User;

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
	 * @param loginUser		当前登录者
	 * @return
	 * @throws Exception
	 */
	boolean saveMenu(Menu menu, User loginUser) throws Exception;
	
	/**
	 * 获取parentId指定的菜单集合
	 * 
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	List<Menu> listParentMenu(Long parentId) throws Exception;

}
