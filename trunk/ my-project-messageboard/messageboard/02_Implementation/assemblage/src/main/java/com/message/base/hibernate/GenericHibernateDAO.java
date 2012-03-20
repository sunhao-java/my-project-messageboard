package com.message.base.hibernate;

import com.message.base.pagination.PaginationSupport;

import java.util.List;
import java.util.Map;

/**
 * hibernate主要方法的封装
 * @author sunhao(sunhao.java@gmail.com)
 */
@SuppressWarnings("unchecked")
public interface GenericHibernateDAO {
	/**
	 * 根据hql和参数list对象获取数值.
	 * @param hql
	 * @param params
	 * @return
	 */
	public List findByHQL(String hql, List params) throws Exception;

    /**
     * 根据hql和参数map对象获取数值
     *
     * @param hql
     * @param params
     * @return
     */
    public List findByHQL(final String hql, final Map params) throws Exception;

	/**
	 * 执行纯正的SQL语句
	 * @param sql
	 * @param params
	 * @return 表中受影响的数据条数
	 */
	public Integer updateByNativeSQL(final String sql, final Map params) throws Exception;

    /**
     * 根据SQL进行查询，返回的是一个数组的list集合
     *
     * @param sql
     * @param params
     * @return
     */
    List queryByNativeSQL(final String sql, final Map params) throws Exception;

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
    PaginationSupport getPaginationByNativeSQL(final String sql, final String countSql, final int start,
                                                      final int num, final Map params) throws Exception;

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
    PaginationSupport getPaginationSupport(final String hql, final String countHql, final int start,
                                           final int num, final Map params) throws Exception;

	/**
	 * 保存实体
	 * @param entity
	 * @return
	 */
	public Object saveObject(Object entity) throws Exception;

	/**
	 * 更新实体
	 * @param entity
	 */
	public void updateObject(Object entity) throws Exception;

	/**
	 * 删除一个实体
	 * @param entity
	 */
	public void deleteObject(Object entity) throws Exception;

    /**
     * 根据主键获取对象
     *
     * @param clazz 类名
     * @param id    主键
     * @return
     */
    Object loadObject(Class clazz, Long id) throws Exception;

    /**
     * 从给定oracle的sequence中获取下一个值
     *
     * @param sequenceName
     * @return
     */
    Long genericId(final String sequenceName) throws Exception;
}
