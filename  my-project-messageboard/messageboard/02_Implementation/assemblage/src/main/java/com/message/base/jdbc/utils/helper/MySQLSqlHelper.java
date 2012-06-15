package com.message.base.jdbc.utils.helper;


/**
 *
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0, 2012-4-11 上午10:48:58
 */
public class MySQLSqlHelper extends SqlHelper {

	public String getPageSql(String sql, int start, int num) {
		if(start < 0 || num < 0)
			return sql;
		
		StringBuffer result = new StringBuffer();
		result.append(sql);
		result.append(" limit ").append(num);
		result.append(" offset ").append(start);
		
		return result.toString();
	}

}
