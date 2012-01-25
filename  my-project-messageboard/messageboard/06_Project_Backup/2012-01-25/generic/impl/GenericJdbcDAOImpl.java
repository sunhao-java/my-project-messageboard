package com.message.base.jdbc.generic.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.message.base.jdbc.base.ExtNamedParameterJdbcDaoSupport;
import com.message.base.jdbc.base.SqlHelper;
import com.message.base.jdbc.generic.GenericJdbcDAO;
import com.message.base.pagination.PaginationSupport;
import com.message.base.pagination.PaginationUtils;

/**
 * spring中使用SQL主要查询等的封装的实现
 * @author sunhao(sunhao.java@gmail.com)
 */
@SuppressWarnings("unchecked")
public class GenericJdbcDAOImpl extends ExtNamedParameterJdbcDaoSupport implements GenericJdbcDAO {
	
	private SqlHelper sqlHelper;
	private RowMapper rowMapper;
	
	public void setSqlHelper(SqlHelper sqlHelper) {
		this.sqlHelper = sqlHelper;
	}

	public SqlHelper getSqlHelper() {
		return sqlHelper;
	}

	public RowMapper getRowMapper() {
		return rowMapper;
	}

	public void setRowMapper(RowMapper rowMapper) {
		this.rowMapper = rowMapper;
	}

	public GenericJdbcDAOImpl(){
		
	}
	
	protected void initDao() throws Exception {
		super.initDao();
		
	}

	public int queryForInt(String sql, Map params) throws DataAccessException {
		return this.getExtNamedParameterJdbcTemplate().queryForInt(sql, params);
		//return this.getNamedParameterJdbcTemplate().queryForInt(sql, params);
	}
	
	public long queryForLong(String sql, Map params) throws DataAccessException {
		return this.getNamedParameterJdbcTemplate().queryForLong(sql, params);
	}
	
	public List queryForList(String sql, Map params, RowMapper mapper) throws DataAccessException {
		try {
			return this.getExtNamedParameterJdbcTemplate().query(sql, params, mapper);
		} catch (EmptyResultDataAccessException e) {
		}
		return Collections.EMPTY_LIST;
	}
	
	public int update(String sql, Object[] args) throws DataAccessException {
		return this.getJdbcTemplate().update(sql, args);
	}
	
	public int update(String sql) throws DataAccessException {
		return this.getJdbcTemplate().update(sql);
	}
	
	public int update(String sql, PreparedStatementSetter pss) throws DataAccessException {
		return this.getJdbcTemplate().update(sql, pss);
	}
	
	public int update(String sql, SqlParameterSource paramSource) throws DataAccessException {
		return this.getNamedParameterJdbcTemplate().update(sql, paramSource);
	}
	
	public Object queryForObject(String sql, Map params, RowMapper mapper) {
		try {
			return this.getNamedParameterJdbcTemplate().queryForObject(sql, params, mapper);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public List queryForMapList(String sql, int start, int num, Map params) throws DataAccessException {
		sql = this.getSqlHelper().getPageSql(sql, start, num);
		return this.queryForList(sql, params, this.getRowMapper());
	}
	
	public List queryForObjectList(String sql, int start, int num, Map params, RowMapper mapper) throws DataAccessException {
		sql = this.getSqlHelper().getPageSql(sql, start, num);
		return this.queryForList(sql, params, mapper);
	}
	
	public PaginationSupport getMapPaginationSupport(String sql, String countSql, int start, int num, Map params)
			throws DataAccessException {
		int count = this.queryForInt(countSql != null ? countSql : this.sqlHelper.getCountSql(sql), params);
		List resultList; 
		if(count > 0){
			resultList = queryForMapList(sql, start, num, params);
		} else 
			resultList = Collections.EMPTY_LIST;
		
		return PaginationUtils.makePagination(resultList, count, num, start);
	}
	
	public PaginationSupport getObjectPaginationSupport(String sql,
			String countSql, int start, int num, Map params, RowMapper mapper)
			throws DataAccessException {
		int count = this.queryForInt(countSql != null ? countSql : this.sqlHelper.getCountSql(sql), params);
		List resultList;
		if(count > 0)
			resultList = this.queryForObjectList(sql, start, num, params, mapper);
		else 
			resultList = Collections.EMPTY_LIST;
		
		return PaginationUtils.makePagination(resultList, count, num, start);
	}
}
