package com.message.base.jdbc.base;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * 重写spring的NamedParameterJdbcTemplate
 * @author sunhao(sunhao.java@gmail.com)
 */
public class ExtNamedParameterJdbcTemplate extends NamedParameterJdbcTemplate {

	public ExtNamedParameterJdbcTemplate(DataSource dataSource) {
		super(dataSource);
	}

	public ExtNamedParameterJdbcTemplate(JdbcOperations classicJdbcTemplate) {
		super(classicJdbcTemplate);
	}
	
	/*public int[] updateBatch(String sql, List paramSources) throws DataAccessException {
		return null;
	}
	
	//TODO 无用
	public int updateByBean(String sql, Object bean) throws DataAccessException {
		if (bean == null) 
			return -1;
		
		ParsedSql parsedSql = getParsedSql(sql);
		String sqlToUse = NamedParameterUtils.substituteNamedParameters(parsedSql, null);
		
		return -1;
	}*/
	
}
