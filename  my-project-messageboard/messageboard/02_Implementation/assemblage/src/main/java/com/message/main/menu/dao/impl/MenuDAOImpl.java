package com.message.main.menu.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.message.base.cache.utils.ObjectCache;
import com.message.base.hibernate.GenericHibernateDAO;
import com.message.base.jdbc.GenericJdbcDAO;
import com.message.base.utils.SqlUtils;
import com.message.main.ResourceType;
import com.message.main.menu.dao.MenuDAO;
import com.message.main.menu.pojo.Menu;

/**
 * 菜单DAO实现
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-3-2 下午09:11:10
 */
public class MenuDAOImpl extends GenericHibernateDAO implements MenuDAO {
    private ObjectCache cache;
    private GenericJdbcDAO genericJdbcDAO;

    public void setCache(ObjectCache cache) {
        this.cache = cache;
    }

	public void setGenericJdbcDAO(GenericJdbcDAO genericJdbcDAO) {
		this.genericJdbcDAO = genericJdbcDAO;
	}

	public List<Menu> listAllMenu() throws Exception {
		String hql = "from Menu m where m.deleteStatus = :flag order by m.menuSort desc ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("flag", ResourceType.DELETE_NO);
		return this.findByHQL(hql, params);
	}
	
	public List<Long> listAllMenuIds() throws Exception {
		String sql = "select m.pk_id as pk from t_message_menu m where m.delete_status = :flag order by m.menu_sort desc ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("flag", ResourceType.DELETE_NO);
		
		return this.genericJdbcDAO.queryForList(sql, params, new RowMapper(){
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("pk");
			}
		});
	}

    public Menu loadMenu(Long pkId) throws Exception {
        Menu menu = (Menu) this.cache.get(Menu.class, pkId);
        if(menu == null){
            menu = (Menu) this.loadObject(Menu.class, pkId);
            this.cache.put(menu, pkId);
        }
		return menu;
	}

	public Menu saveMenu(Menu menu) throws Exception {
		menu = (Menu) this.saveObject(menu);
		
		this.cache.put(menu, menu.getPkId());
		return menu;
	}

	public void updateMenu(Menu menu) throws Exception {
		this.updateObject(menu);
		//update the cache
		this.cache.remove(Menu.class, menu.getPkId());
		this.cache.put(menu, menu.getPkId());
	}

	public List<Long> listMenuByParentId(Long parentId) throws Exception {
		String sql = "select m.pk_id as pk from t_message_menu m where m.parent_id = :parentId " +
				" and m.delete_status = :flag order by m.menu_sort asc";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("flag", ResourceType.DELETE_NO);
		params.put("parentId", parentId);
		
		return this.genericJdbcDAO.queryForList(sql, params, new RowMapper(){
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("pk");
			}});
	}

	public List<Long> listPermMenu(String perm) throws Exception {
    	String sql = "select m.pk_id as pk from t_message_menu m where m.delete_status = :flag and m.menu_status = :status " +
    			" and m.menu_perm like :perm order by m.menu_sort asc ";

        Map<String, Object> params = new HashMap<String, Object>();
		params.put("flag", ResourceType.DELETE_NO);
		params.put("status", 1L);
        params.put("perm", SqlUtils.makeLikeString(perm));
        
        return this.genericJdbcDAO.queryForList(sql, params, new RowMapper(){
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("pk");
			}});
    }

	public Menu findMenu(String menuUrl) throws Exception {
        String hql = "from Menu m where m.deleteStatus = :flag and m.menuStatus = :status and m.menuUrl = :url";
        Map<String, Object> params = new HashMap<String, Object>();
		params.put("flag", ResourceType.DELETE_NO);
		params.put("status", 1L);
        params.put("url", menuUrl);
        List<Menu> menus = this.findByHQL(hql, params);
        if(menus != null && menus.size() > 0){
            return menus.get(0);
        }
        
        return null;
    }

	public void deleteMenu(Long pkId) throws Exception {
		String sql = "update t_message_menu m set m.delete_status = :status where m.pk_id = :pkId ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", ResourceType.DELETE_YES);
		params.put("pkId", pkId);
		
		this.updateByNativeSQL(sql, params);
		//remove from cache
		this.cache.remove(Menu.class, pkId);
	}

}
