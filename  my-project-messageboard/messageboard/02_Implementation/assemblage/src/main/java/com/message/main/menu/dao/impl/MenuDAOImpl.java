package com.message.main.menu.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.message.base.hibernate.impl.GenericHibernateDAOImpl;
import com.message.main.menu.dao.MenuDAO;
import com.message.main.menu.pojo.Menu;
import com.message.resource.ResourceType;

/**
 * 菜单DAO实现
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-3-2 下午09:11:10
 */
public class MenuDAOImpl extends GenericHibernateDAOImpl implements MenuDAO {

	@SuppressWarnings("unchecked")
	public List<Menu> listAllMenu() throws Exception {
		String hql = "from Menu m where m.deleteStatus = :flag order by m.menuSort desc ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("flag", ResourceType.DELETE_NO);
		return this.findByHQL(hql, params);
	}

	public Menu loadMenu(Long pkId) throws Exception {
		return (Menu) this.loadObject(Menu.class, pkId);
	}

	public Menu saveMenu(Menu menu) throws Exception {
		return (Menu) this.saveObject(menu);
	}

	public void updateMenu(Menu menu) throws Exception {
		this.updateObject(menu);
	}

	@SuppressWarnings("unchecked")
	public List<Menu> listMenuByParentId(Long parentId) throws Exception {
		String hql = "from Menu m where m.parentId = :parentId and m.deleteStatus = :flag order by m.menuSort asc ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("flag", ResourceType.DELETE_NO);
		params.put("parentId", parentId);
		return this.findByHQL(hql, params);
	}

}
