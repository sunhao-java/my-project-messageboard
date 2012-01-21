package com.message.main.info.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.message.base.hibernate.impl.GenericHibernateDAOImpl;
import com.message.base.pagination.PaginationSupport;
import com.message.main.info.dao.InfoDAO;
import com.message.main.info.pojo.Info;

/**
 * 留言板描述的DAO实现
 * @author sunhao(sunhao.java@gmail.com)
 */
public class InfoDAOImpl extends GenericHibernateDAOImpl implements InfoDAO {

	public Long saveInfo(Info info) throws Exception {
		return ((Info) this.saveObject(info)).getPkId();
	}

	@SuppressWarnings("unchecked")
	public List<Info> getAllInfo() throws Exception {
		String hql = "from Info i order by i.pkId desc";
		Map<String, Object> params = new HashMap<String, Object>();
		List<Info> infos = this.findByHQL(hql, params);
		return infos;
	}

	public PaginationSupport getInfoHistroy(int start, int num, Info info)
			throws Exception {
		String hql = "from Info i order by i.pkId desc ";
		String countHql = "select count(*) " + hql;
		Map<String, Object> params = new HashMap<String, Object>();
		return this.getBeanPaginationSupport(hql, countHql, start, num, params);
	}

}
