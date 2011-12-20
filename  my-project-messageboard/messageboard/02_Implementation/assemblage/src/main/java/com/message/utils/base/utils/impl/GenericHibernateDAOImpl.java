package com.message.utils.base.utils.impl;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.message.utils.base.utils.GenericHibernateDAO;
import com.message.utils.spring.SpringHibernateUtils;

public class GenericHibernateDAOImpl implements GenericHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(SpringHibernateUtils.class);
	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	/**
	 * 根据hql和参数list对象获取数值<br/>
	 * 原来直接用this.hibernateTemplate.getSessionFactory.getCurrentSession.createQuery()
	 * 这样报错是因为此时的session由hibernate管理，并没有交给spring管理
	 * 而hibernate管理session是在一个事务transaction中的，这里并没有transaction，所以报错
	 * 改为用HibernateCallback!
	 * @param hql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findByHQL(final String hql, final List params){
		List result = null;
		try{
			result = this.hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
				Query query = null;
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					query = session.createQuery(hql);
					if(CollectionUtils.isNotEmpty(params)) {
						for(int i = 0; i < params.size(); i++) {
							query.setParameter(i, params.get(i));
						}
					}
					return query.list();
				}
			});
		} catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return result;
	}
	
	/**
	 * 执行纯正的SQL语句
	 * @param sql
	 * @param params
	 * @return 表中受影响的数据条数
	 */
	@SuppressWarnings("unchecked")
	public Integer updateByNativeSQL(final String sql, final Map params){
		return this.hibernateTemplate.execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				
				Iterator it = params.entrySet().iterator();
				while(it.hasNext()){
					Entry en = (Entry) it.next();
					sqlQuery.setParameter((String)en.getKey(), en.getValue());
				}
				
				return sqlQuery.executeUpdate();
			}
		});
	}
	
	/**
	 * 保存实体
	 * @param entity
	 * @return
	 */
	public Object saveObject(Object entity){
		this.hibernateTemplate.save(entity);
		return entity;
	}
	
	/**
	 * 更新实体
	 * @param entity
	 */
	public void updateObject(Object entity){
		this.hibernateTemplate.update(entity);
	}
}
