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

import com.message.utils.PaginationSupport;
import com.message.utils.PaginationUtils;
import com.message.utils.base.utils.GenericHibernateDAO;

/**
 * hibernate主要方法的封装的实现
 * @author sunhao(sunhao.java@gmail.com)
 */
public class GenericHibernateDAOImpl implements GenericHibernateDAO {
	private static final Logger log = LoggerFactory.getLogger(GenericHibernateDAOImpl.class);
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
	 * 根据hql和参数map对象获取数值
	 * @param hql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findByHQL(final String hql, final Map params){
		return this.hibernateTemplate.execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				
				Iterator iterator = params.entrySet().iterator();
				while(iterator.hasNext()){
					Entry entry = (Entry) iterator.next();
					query.setParameter((String)entry.getKey(), entry.getValue());
				}
				
				return query.list();
			}
		});
	}
	
	/**
	 * 执行纯正的SQL语句(update)
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
	 * 根据SQL进行查询，返回的是一个数组的list集合
	 * @param sql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List queryByNativeSQL(final String sql, final Map params){
		return this.hibernateTemplate.execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				
				Iterator it = params.entrySet().iterator();
				while(it.hasNext()){
					Entry entry = (Entry) it.next();
					sqlQuery.setParameter((String)entry.getKey(), entry.getValue());
				}
				return sqlQuery.list();
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
	
	/**
	 * 通过分页来获取对象的list集合
	 * @param hql		查询HQL
	 * @param countHql	查询数据库中总的条数的HQL
	 * @param start		开始记录
	 * @param num		总共查出的记录
	 * @param params	参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PaginationSupport getBeanPaginationSupport(final String hql, final String countHql, final int start, final int num, final Map params) {
		PaginationSupport result = null;
		try{
			result = this.hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					Query query = session.createQuery(hql);
					Query query2 = session.createQuery(countHql);
					query.setFirstResult(start);
					query.setMaxResults(num);
					
					Iterator it = params.entrySet().iterator();
					while(it.hasNext()){
						Entry en = (Entry) it.next();
						query.setParameter((String)en.getKey(), en.getValue());
						query2.setParameter((String)en.getKey(), en.getValue());
					}
					
					List items = query.list();
					int pageCount = Integer.valueOf(query2.uniqueResult().toString());
					
					return PaginationUtils.makePagination(items, pageCount, num, start);
				}
			});
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	/**
	 * 根据主键获取对象
	 * @param clazz		类名
	 * @param id		主键
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object loadObject(Class clazz, Long id){
		return this.hibernateTemplate.get(clazz, id);
	}
	
}
