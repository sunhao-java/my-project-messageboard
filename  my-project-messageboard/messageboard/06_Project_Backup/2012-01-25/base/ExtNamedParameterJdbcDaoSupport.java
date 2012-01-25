package com.message.base.jdbc.base;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

/**
 * 重写spring的NamedParameterJdbcDaoSupport
 * @author sunhao(sunhao.java@gmail.com)
 */
public class ExtNamedParameterJdbcDaoSupport extends NamedParameterJdbcDaoSupport {
	
	private ExtNamedParameterJdbcTemplate extNamedParameterJdbcTemplate;
	
	public ExtNamedParameterJdbcDaoSupport(){
		
	}
	
	protected void initTemplateConfig(){
		extNamedParameterJdbcTemplate = new ExtNamedParameterJdbcTemplate(getJdbcTemplate());
	}

	public ExtNamedParameterJdbcTemplate getExtNamedParameterJdbcTemplate() {
		return extNamedParameterJdbcTemplate;
	}

	public ExtNamedParameterJdbcTemplate getExtNamedParameterJdbcTemplate(
			ExtNamedParameterJdbcTemplate extNamedParameterJdbcTemplate) {
		return extNamedParameterJdbcTemplate;
	}

}
