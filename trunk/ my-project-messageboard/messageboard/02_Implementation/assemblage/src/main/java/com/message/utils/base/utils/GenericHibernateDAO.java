package com.message.utils.base.utils;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public interface GenericHibernateDAO {
	/**
	 * 根据hql和参数list对象获取数值.
	 * @param hql
	 * @param params
	 * @return
	 */
	public List findByHQL(String hql, List params);
	
	/**
	 * 执行纯正的SQL语句
	 * @param sql
	 * @param params
	 * @return 表中受影响的数据条数
	 */
	public Integer updateByNativeSQL(final String sql, final Map params);
	
	/**
	 * 保存实体
	 * @param entity
	 * @return
	 */
	public Object saveObject(Object entity);
	
	/**
	 * 更新实体
	 * @param entity
	 */
	public void updateObject(Object entity);
}
