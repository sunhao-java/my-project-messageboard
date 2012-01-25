package com.message.base.jdbc.generic;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.message.base.pagination.PaginationSupport;

/**
 * spring中使用SQL主要查询等的封装
 * @author sunhao(sunhao.java@gmail.com)
 */
@SuppressWarnings("unchecked")
public interface GenericJdbcDAO {
	/**
	 * 根据sql和MAP的参数查询得到int
	 * @param sql
	 * @param params
	 * @return
	 * @throws DataAccessException
	 */
	public int queryForInt(String sql, Map params) throws DataAccessException;

	/**
	 * 根据sql和MAP的参数查询得到long
	 * @param sql
	 * @param params
	 * @return
	 * @throws DataAccessException
	 */
	public long queryForLong(String sql, Map params) throws DataAccessException;

	/**
	 * 根据sql,Map的参数,rowmapper获得一个list集合
	 * @param sql
	 * @param params
	 * @param mapper
	 * @return
	 * @throws DataAccessException
	 */
	public List queryForList(String sql, Map params, RowMapper mapper)
			throws DataAccessException;

	/**
	 * 根据sql和数组的参数进行update
	 * @param sql
	 * @param args
	 * @return
	 * @throws DataAccessException
	 */
	public int update(String sql, Object[] args) throws DataAccessException;

	/**
	 * 根据sql进行update
	 * @param sql
	 * @return
	 * @throws DataAccessException
	 */
	public int update(String sql) throws DataAccessException;

	/**
	 * 根据sql和PreparedStatementSetter进行update
	 * @param sql
	 * @param pss
	 * @return
	 * @throws DataAccessException
	 */
	public int update(String sql, PreparedStatementSetter pss)
			throws DataAccessException;

	/**
	 * 根据sql和SqlParameterSource进行update
	 * @param sql
	 * @param paramSource
	 * @return
	 * @throws DataAccessException
	 */
	public int update(String sql, SqlParameterSource paramSource)
			throws DataAccessException;

	/**
	 * 根据sql,Map,rowmapper查询得到一个Object
	 * @param sql
	 * @param params
	 * @param mapper
	 * @return
	 */
	public Object queryForObject(String sql, Map params, RowMapper mapper);

	/**
	 * 查询得到map的list集合
	 * @param sql
	 * @param start
	 * @param num
	 * @param params
	 * @return
	 * @throws DataAccessException
	 */
	public List queryForMapList(String sql, int start, int num, Map params)
			throws DataAccessException;

	/**
	 * 查询得到object的list集合
	 * @param sql
	 * @param start
	 * @param num
	 * @param params
	 * @param mapper
	 * @return
	 * @throws DataAccessException
	 */
	public List queryForObjectList(String sql, int start, int num, Map params,
			RowMapper mapper) throws DataAccessException;

	/**
	 * 查询得到分页结果
	 * @param sql
	 * @param countSql
	 * @param start
	 * @param num
	 * @param params
	 * @return
	 * @throws DataAccessException
	 */
	public PaginationSupport getMapPaginationSupport(String sql,
			String countSql, int start, int num, Map params)
			throws DataAccessException;

	/**
	 * 查询得到分页结果
	 * @param sql
	 * @param countSql
	 * @param start
	 * @param num
	 * @param params
	 * @param mapper
	 * @return
	 * @throws DataAccessException
	 */
	public PaginationSupport getObjectPaginationSupport(String sql,
			String countSql, int start, int num, Map params, RowMapper mapper)
			throws DataAccessException;
}
