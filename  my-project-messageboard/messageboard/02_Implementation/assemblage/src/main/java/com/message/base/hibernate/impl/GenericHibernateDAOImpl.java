package com.message.base.hibernate.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.aspectj.weaver.ast.Call;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.message.base.hibernate.GenericHibernateDAO;
import com.message.base.pagination.PaginationSupport;
import com.message.base.pagination.PaginationUtils;
import com.message.base.utils.SqlUtils;
import com.message.base.utils.StringUtils;

/**
 * hibernate主要方法的封装的实现
 *
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
     *
     * @param hql
     * @param params
     * @return
     */
	public List findByHQL(final String hql, final List params) throws Exception {
        List result = (List) this.hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            Query query = null;

            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                query = session.createQuery(hql);
                if (CollectionUtils.isNotEmpty(params)) {
                    for (int i = 0; i < params.size(); i++) {
                        query.setParameter(i, params.get(i));
                    }
                }
                return query.list();
            }
        });
        
        return result;
    }

    /**
     * 根据hql和参数map对象获取数值
     *
     * @param hql
     * @param params
     * @return
     */
    public List findByHQL(final String hql, final Map params) throws Exception {
        return (List) this.hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);

                Iterator iterator = params.entrySet().iterator();
                while (iterator.hasNext()) {
                    Entry entry = (Entry) iterator.next();
                    query.setParameter((String) entry.getKey(), entry.getValue());
                }

                return query.list();
            }
        });
    }

    /**
     * 执行纯正的SQL语句(update) 返回表中受影响的数据条数
     *
     * @param sql
     * @param params
     * @return 表中受影响的数据条数
     */
    public Integer updateByNativeSQL(final String sql, final Map params) throws Exception {
        return (Integer) this.hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                SQLQuery sqlQuery = session.createSQLQuery(sql);

                Iterator it = params.entrySet().iterator();
                while (it.hasNext()) {
                    Entry en = (Entry) it.next();
                    sqlQuery.setParameter((String) en.getKey(), en.getValue());
                }

                return sqlQuery.executeUpdate();
            }
        });
    }

    /**
     * 根据SQL进行查询，返回的是一个数组的list集合
     *
     * @param sql
     * @param params
     * @return
     */
    public List queryByNativeSQL(final String sql, final Map params) throws Exception {
        return (List) this.hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                SQLQuery sqlQuery = session.createSQLQuery(sql);

                Iterator it = params.entrySet().iterator();
                while (it.hasNext()) {
                    Entry entry = (Entry) it.next();
                    sqlQuery.setParameter((String) entry.getKey(), entry.getValue());
                }
                return sqlQuery.list();
            }
        });
    }

    /**
     * 通过分页来获取对象的list集合(纯正的SQL查询)
     *
     * @param sql      sql
     * @param countSql 查询数据库中总的条数的countSql
     * @param start    开始记录
     * @param num      总共查出的记录
     * @param params   参数
     * @return
     */
    public PaginationSupport getPaginationByNativeSQL(final String sql, final String countSql, final int start,
                                                      final int num, final Map params) throws Exception {
        //如果没有给定countSql，则在程序中计算出countSql
        final String countSql1 = countSql == null ? SqlUtils.getCountSql(sql) : countSql;
        PaginationSupport result = (PaginationSupport) this.hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                SQLQuery sqlQuery = session.createSQLQuery(sql);
                SQLQuery sqlQuery2 = session.createSQLQuery(countSql1);
                sqlQuery.setFirstResult(start);
                sqlQuery.setMaxResults(num);

                Iterator it = params.entrySet().iterator();
                while (it.hasNext()) {
                    Entry entry = (Entry) it.next();
                    sqlQuery.setParameter((String) entry.getKey(), entry.getValue());
                    sqlQuery2.setParameter((String) entry.getKey(), entry.getValue());
                }

                List items = sqlQuery.list();
                int pageCount = Integer.valueOf(sqlQuery2.uniqueResult().toString());

                //当start不为0时，需要进行分页查询，结果中会有一个rownum字段，加上前面需要查询的字段，这就变成了一个
                //数组的list集合，而程序只需要数组的第一项，所以要进行判断：
                //当start==0时，不须对结果处理，当不为0时，就需要处理得到每个数组的第一项的list集合
                return PaginationUtils.makePagination(start == 0 ? items : resultHandle(items), pageCount, num, start);
            }
        });

        return result;
    }

    /**
     * 保存实体
     *
     * @param entity
     * @return
     */
    public Object saveObject(Object entity) {
        this.hibernateTemplate.save(entity);
        
        return entity;
    }

    /**
     * 更新实体
     *
     * @param entity
     */
    public void updateObject(Object entity) throws Exception {
        this.hibernateTemplate.update(entity);
    }

    /**
     * 通过分页来获取对象的list集合
     *
     * @param hql      查询HQL
     * @param countHql 查询数据库中总的条数的HQL
     * @param start    开始记录
     * @param num      总共查出的记录
     * @param params   参数
     * @return
     */
    public PaginationSupport getPaginationSupport(final String hql, final String countHql, final int start,
                                                  final int num, final Map params) throws Exception {
        PaginationSupport result =  (PaginationSupport) this.hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                Query query2 = session.createQuery(countHql);
                query.setFirstResult(start);
                query.setMaxResults(num);

                Iterator it = params.entrySet().iterator();
                while (it.hasNext()) {
                    Entry en = (Entry) it.next();
                    query.setParameter((String) en.getKey(), en.getValue());
                    query2.setParameter((String) en.getKey(), en.getValue());
                }

                List items = query.list();
                int pageCount = Integer.valueOf(query2.uniqueResult().toString());

                return PaginationUtils.makePagination(items, pageCount, num, start);
            }
        });

        return result;
    }

    /**
     * 根据主键获取对象
     *
     * @param clazz 类名
     * @param id    主键
     * @return
     */
    public Object loadObject(Class clazz, Long id) throws Exception {
        return this.hibernateTemplate.get(clazz, id);
    }

    /**
     * 删除一个实体
     *
     * @param entity
     */
    public void deleteObject(Object entity) throws Exception {
        this.hibernateTemplate.delete(entity);
    }

    /**
     * 纯正的SQL分页会出现错误，这里是对查询得到的结果进行处理<br/>
     * 得到的结果是List<Object[]>类型的，即是数组的list集合<br/>
     * 取出每个数组的第一项，作为list返回
     *
     * @param result 查询得到的结果
     * @return 程序需要的结果
     */
    private List resultHandle(List result){
        List trueResult = new ArrayList();
        if(CollectionUtils.isEmpty(result))
            return Collections.EMPTY_LIST;
        for(int i = 0; i < result.size(); i++){
            Object[] obj = (Object[]) result.get(i);
            trueResult.add(obj[0]);
        }
        return trueResult;
    }

    /**
     * 从给定oracle的sequence中获取下一个值
     *
     * @param sequenceName
     * @return
     */
    public Long genericId(final String sequenceName) throws Exception{
        if(StringUtils.isEmpty(sequenceName)){
            log.warn("given sequence name is null!");
        }
        log.debug("given sequence name is '{}'!", sequenceName);

        final String sql = "select " + sequenceName + ".NextVal as id from dual";

        HibernateCallback callback = new HibernateCallback() {
            @SuppressWarnings("deprecation")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Long pkId = (Long) session.createSQLQuery(sql).addScalar("id", Hibernate.LONG).uniqueResult();
                log.debug("hibernate get nextVal from sequence named '{}' is '{}'", sequenceName, pkId);
                return pkId;
            }
        };

        return (Long) hibernateTemplate.execute(callback);
    }

	public int commUpdateByHQL(final Class clazz, final Map columnParams, final Map whereParams) throws Exception {
		if(clazz == null || columnParams == null || columnParams.size() < 1) {
			return 0;
		}
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer sql = new StringBuffer();
				sql.append("update ").append(clazz.getSimpleName()).append(" t ").append(" set ");
				
				Iterator<String> it1 = columnParams.keySet().iterator();
				while(it1.hasNext()) {
					String key = it1.next();
					Object value = columnParams.get(key);
					
					if(value instanceof String) {
						sql.append(" t.").append(key).append(" = ").append("'").append(value.toString()).append("', ");
					} else {
						sql.append(" t.").append(key).append(" = ").append(value.toString()).append(", ");
					}
				}
				
				if(sql.lastIndexOf(",") != -1){
					sql = StringUtils.substringbuffer(sql, 0, sql.length() - 2);
				}
				
				if(whereParams != null && whereParams.size() > 0){
					sql.append(" where 1 = 1 ");
					Iterator<String> it2 = whereParams.keySet().iterator();
					while(it2.hasNext()){
						String key = it2.next();
						Object value = whereParams.get(key);
						
						if(value instanceof String) {
							sql.append(" and t.").append(key).append(" = ").append("'").append(value.toString()).append("' ");
						} else {
							sql.append(" and t.").append(key).append(" = ").append(value.toString());
						}
					}
				}
				
				Query query = session.createQuery(sql.toString());
				return query.executeUpdate();
			}
		};
			
		return (Integer)this.hibernateTemplate.execute(callback);
	}

	public int commUpdateByHQL(Class clazz, Map columnParams) throws Exception {
		return this.commUpdateByHQL(clazz, columnParams, null);
	}

	public int commUpdateByNativeSQL(final String table, final Map columnParams, final Map whereParams) throws Exception {
		if(StringUtils.isEmpty(table) || columnParams == null || columnParams.size() < 1) {
			return 0;
		}
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuffer sql = new StringBuffer();
				sql.append("update ").append(table).append(" t ").append(" set ");
				
				Iterator<String> it1 = columnParams.keySet().iterator();
				while(it1.hasNext()) {
					String key = it1.next();
					Object value = columnParams.get(key);
					
					if(value instanceof String) {
						sql.append(" t.").append(key).append(" = ").append("'").append(value.toString()).append("', ");
					} else {
						sql.append(" t.").append(key).append(" = ").append(value.toString()).append(", ");
					}
				}
				
				if(sql.lastIndexOf(",") != -1){
					sql = StringUtils.substringbuffer(sql, 0, sql.length() - 2);
				}
				
				if(whereParams != null && whereParams.size() > 0){
					sql.append(" where 1 = 1 ");
					Iterator<String> it2 = whereParams.keySet().iterator();
					while(it2.hasNext()){
						String key = it2.next();
						Object value = whereParams.get(key);
						
						if(value instanceof String) {
							sql.append(" and t.").append(key).append(" = ").append("'").append(value.toString()).append("' ");
						} else {
							sql.append(" and t.").append(key).append(" = ").append(value.toString());
						}
					}
				}
				
				SQLQuery sqlQuery = session.createSQLQuery(sql.toString());
				return sqlQuery.executeUpdate();
			}
		};
			
		return (Integer)this.hibernateTemplate.execute(callback);
	}

	public int commUpdateByNativeSQL(final String table, final Map columnParams) throws Exception {
		return this.commUpdateByNativeSQL(table, columnParams, null);
	}

}
