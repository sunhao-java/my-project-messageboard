package com.message.base.jdbc;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.message.base.jdbc.dynamic.ColumnMapRowMapper;
import com.message.base.jdbc.dynamic.DynamicBeanRowMapper;
import com.message.base.jdbc.ext.ExtNamedParameterJdbcDaoSupport;
import com.message.base.jdbc.key.IDGenerator;
import com.message.base.jdbc.utils.helper.SqlHelper;
import com.message.base.pagination.PaginationSupport;
import com.message.base.pagination.PaginationUtils;
import com.message.base.utils.StringUtils;

/**
 * query for more types
 *
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0, 2012-4-10 上午12:32:41
 */
public class AbstractJdbcDAO extends ExtNamedParameterJdbcDaoSupport {
	private RowMapper rowMapper;
	private SqlHelper sqlHelper;
	private IDGenerator idGenerator;
	
	/**
	 * 查询得到int型
	 * 
	 * @param sql		query sql
	 * @param params	need parameter
	 * @return			result(int)
	 * @throws DataAccessException
	 */
	public int queryForInt(String sql, Map params) throws DataAccessException {
		return this.getNamedParameterJdbcTemplate().queryForInt(sql, params);
	}
	
	/**
	 * 查询得到long型
	 * 
	 * @param sql		query sql
	 * @param params	need parameter
	 * @return			result(long)
	 * @throws DataAccessException
	 */
	public long queryForLong(String sql, Map params) throws DataAccessException {
		return this.getNamedParameterJdbcTemplate().queryForLong(sql, params);
	}
	
	/**
	 * query for map
	 * 
	 * @param sql		query sql
	 * @param params	need parameter
	 * @return			result(map)
	 * @throws DataAccessException
	 */
	public Map queryForMapList(String sql, Map params) throws DataAccessException {
		return (Map) this.queryForObject(sql, params, getRowMapper());
	}
	
	/**
	 * query for list
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws DataAccessException
	 */
	public List queryForList(String sql, Map params) throws DataAccessException {
		return this.queryForList(sql, params, getRowMapper());
	}
	
	/**
	 * update by sql with given parameters
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws DataAccessException
	 */
	public int update(String sql, Object[] params) throws DataAccessException {
		return this.getJdbcTemplate().update(sql, params);
	}
	
	/**
	 * update by sql
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws DataAccessException
	 */
	public int update(String sql) throws DataAccessException {
		return this.getJdbcTemplate().update(sql);
	}
	
	/**
	 * update by sql with preparedStatementSetter
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws DataAccessException
	 */
	public int update(String sql, PreparedStatementSetter setter) throws DataAccessException {
		return this.getJdbcTemplate().update(sql, setter);
	}
	
	/**
	 * query by sql, given params type is <code>java.util.Map</code><br/>
	 * TODO need to study <code>ExtMapSqlParameterSource</code>
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws DataAccessException
	 */
	public int update(String sql, Map params) throws DataAccessException {
		return 0;
	}
	
	/**
	 * update by sql when given SqlParameterSource
	 * 
	 * @param sql
	 * @param paramSource
	 * @return
	 * @throws DataAccessException
	 */
	public int update(String sql, SqlParameterSource paramSource) throws DataAccessException {
		return this.getNamedParameterJdbcTemplate().update(sql, paramSource);
	}
	
	/**
	 * update sql when given parameter in an object
	 * TODO need to study ExtBeanPropertySqlParameterSource
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 * @throws DataAccessException
	 */
	public int updateByBean(String sql, Object obj) throws DataAccessException {
		return 0;
	}
	
	/**
	 * TODO the same as top method
	 * 
	 * @param sql
	 * @param objs
	 * @return
	 * @throws DataAccessException
	 */
	public int[] updateBatchByBean(final String sql, final List objs) throws DataAccessException {
		return null;
	}
	
	/**
	 * query for list
	 * 
	 * @param sql		query sql
	 * @param params	need parameter
	 * @param rowMapper	
	 * @return
	 * @throws DataAccessException
	 */
	public List queryForList(String sql, Map params, RowMapper rowMapper) throws DataAccessException {
		return this.getNamedParameterJdbcTemplate().query(sql, params, rowMapper);
	}
	
	/**
	 * query for object
	 * 
	 * @param sql
	 * @param params
	 * @param rowMapper
	 * @return
	 * @throws DataAccessException
	 */
	public Object queryForObject(String sql, Map params, RowMapper rowMapper) {
		try{
			return this.getNamedParameterJdbcTemplate().queryForObject(sql, params, rowMapper);
		} catch (EmptyResultDataAccessException e){
			return null;
		}
	}
	
	/**
	 * query, return will be make as the class bean what you given
	 * 
	 * @param sql
	 * @param params
	 * @param clazz
	 * @return
	 * @throws DataAccessException
	 */
	public Object queryForBean(String sql, Map params, Class clazz) throws DataAccessException {
		return this.queryForObject(sql, params, DynamicBeanRowMapper.getInstance(clazz, this.getSqlHelper(), sql));
	}
	
	/**
	 * query for a list, begin index is start, offset is num
	 * 
	 * @param sql			query sql
	 * @param start			begin index
	 * @param num			offset
	 * @param params
	 * @param rowMapper
	 * @return
	 * @throws DataAccessException
	 */
	public List queryForObjectList(String sql, int start, int num, Map params, RowMapper rowMapper) throws DataAccessException {
		sql = this.getSqlHelper().getPageSql(sql, start, num);
		return this.queryForList(sql, params, rowMapper);
	}
	
	/**
	 * query for a bean list as given class begin index is start, offset is num
	 * 
	 * @param sql			query sql
	 * @param start			begin index
	 * @param num			offset
	 * @param params
	 * @param clazz			bean class
	 * @return
	 * @throws DataAccessException
	 */
	public List queryForBeanList(String sql, int start, int num, Map params, Class clazz) throws DataAccessException {
		String pageSql = this.getSqlHelper().getPageSql(sql, start, num);
		
		return this.queryForList(pageSql, params, DynamicBeanRowMapper.getInstance(clazz, this.getSqlHelper(), sql));
	}
	
	/**
	 * query for pagination
	 * 
	 * @param sql			query sql, must be given
	 * @param countSql		query count sql, maybe null
	 * @param start			begin index
	 * @param num			offset
	 * @param params
	 * @param clazz
	 * @return
	 * @throws DataAccessException
	 */
	public PaginationSupport getBeanPaginationSupport(String sql, String countSql, int start, int num, Map params, Class clazz) 
			throws DataAccessException {
		countSql = countSql == null ? this.getSqlHelper().getCountSql(sql) : countSql;
		int count = this.queryForInt(countSql, params);
		
		List result = null;
		if(count == 0)
			result = Collections.EMPTY_LIST;
		else
			result = this.queryForBeanList(sql, start, num, params, clazz);
		
		PaginationSupport ps = PaginationUtils.makePagination(result, count, num, start);
		return ps;
	}
	
	/**
	 * query for object pagination
	 * 
	 * @param sql			query sql, must be given
	 * @param countSql		query count sql, maybe null
	 * @param start			begin index
	 * @param num			offset
	 * @param params
	 * @param rowMapper
	 * @return
	 * @throws DataAccessException
	 */
	public PaginationSupport getObjectPaginationSupport(String sql, String countSql, int start, int num, Map params, RowMapper rowMapper)
			throws DataAccessException {
		countSql = countSql == null ? this.getSqlHelper().getCountSql(sql) : countSql;
		int count = this.queryForInt(countSql, params);
		
		List result = null;
		if(count == 0)
			result = Collections.EMPTY_LIST;
		else
			result = this.queryForObjectList(sql, start, num, params, rowMapper);
		
		PaginationSupport ps = PaginationUtils.makePagination(result, count, num, start);
		return ps;
	}
	
	/**
	 * get next long pkId from database by given sequence
	 * 
	 * @param name	sequence name
	 * @return
	 * @throws DataAccessException
	 */
	public long generateLongId(String name) throws DataAccessException {
		return this.idGenerator.nextLongValue(name);
	}
	
	/**
	 * get next int pkId from database by given sequence
	 * 
	 * @param name	sequence name
	 * @return
	 * @throws DataAccessException
	 */
	public int generateIntId(String name) throws DataAccessException {
		return this.idGenerator.nextIntValue(name);
	}
	
	/**
	 * get next string pkId from database by given sequence
	 * 
	 * @param name	sequence name
	 * @return
	 * @throws DataAccessException
	 */
	public String generateStringId(String name) throws DataAccessException {
		return this.idGenerator.nextStringValue(name);
	}
	
	/**
	 * update
	 * 
	 * @param table				table name
	 * @param columnParams		colum
	 * @param whereParams		where
	 * @return					update column rows num
	 * @throws DataAccessException
	 */
	public int commUpdate(String tableName, Map columnParams, Map whereParams) throws DataAccessException {
		if(StringUtils.isEmpty(tableName) || columnParams == null || columnParams.size() < 1) {
			return 0;
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append("update ").append(tableName).append(" t ").append(" set ");
		
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
		
		return this.update(sql.toString());
	}
	
	/**
	 * update
	 * 
	 * @param tableName
	 * @param columnParams
	 * @return
	 * @throws DataAccessException
	 */
	public int commUpdate(String tableName, Map columnParams) throws DataAccessException {
		return this.commUpdate(tableName, columnParams, null);
	}
	

	protected void initDao() throws Exception {
		super.initDao();
		
		ColumnMapRowMapper rm = new ColumnMapRowMapper();
		rm.setSqlHelper(sqlHelper);
		
		this.setRowMapper(rm);
	}

	public RowMapper getRowMapper() {
		return rowMapper;
	}

	public void setRowMapper(RowMapper rowMapper) {
		this.rowMapper = rowMapper;
	}

	public SqlHelper getSqlHelper() {
		return sqlHelper;
	}

	public void setSqlHelper(SqlHelper sqlHelper) {
		this.sqlHelper = sqlHelper;
	}

	public void setIdGenerator(IDGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}
	
}
