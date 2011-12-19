package com.message.utils.spring;

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
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * 封装spring的hibernateTemplate
 * @author sunhao(sunhao.java@gmail.com)
 */
public class SpringHibernateUtils {
	private static final Logger log = LoggerFactory.getLogger(SpringHibernateUtils.class);
	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	/**
	 * 根据hql和参数list对象获取数值.
	 * @param hql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findByHQL(String hql, List params){
		Query query = null;
		List result = null;
		try{
			//query = this.hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(hql);
			SessionFactory s = this.hibernateTemplate.getSessionFactory();
			Session ss = s.openSession();
			query = ss.createQuery(hql);
			if(CollectionUtils.isNotEmpty(params)) {
				for(int i = 0; i < params.size(); i++) {
					query.setParameter(i, params.get(i));
				}
			}
			result = query.list();
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
