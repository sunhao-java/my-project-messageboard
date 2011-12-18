package com.message.utils.spring;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 封装spring的hibernateTemplate
 * @author sunhao(sunhao.java@gmail.com)
 */
public class SpringHibernateUtils {
	private static final Logger log = LoggerFactory.getLogger(SpringHibernateUtils.class);
	private GenericHibernateDAO genericHibernateDAO;

	public void setGenericHibernateDAO(GenericHibernateDAO genericHibernateDAO) {
		this.genericHibernateDAO = genericHibernateDAO;
	}
	
	/**
	 * 根据hql和参数list对象获取数值.
	 * @param hql
	 * @param params
	 * @return
	 */
	public List<?> findByHQL(String hql, List<Object> params){
		Query query = null;
		List<?> result = null;
		try{
			query = this.genericHibernateDAO.getSessionFactory().getCurrentSession().createQuery(hql);
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
	
	
}
