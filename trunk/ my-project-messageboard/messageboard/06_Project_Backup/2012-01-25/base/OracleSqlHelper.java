package com.message.base.jdbc.base;

/**
 * 适用于ORACLE数据库
 * @author sunhao(sunhao.java@gmail.com)
 */
public class OracleSqlHelper extends SqlHelper {

	/**
	 * 获得ORACLE查询分页的SQL
	 * @param sql
	 * @param start
	 * @param num
	 * @return
	 */
	public String getPageSql(String sql, int start, int num) {
		if ((start < 0) || (num < 0)) 
			return sql;
		
		StringBuffer _sql = new StringBuffer();
		
		_sql.append("select * from ( ");
		_sql.append(" SELECT temp.* ,ROWNUM num FROM ( ");
		_sql.append(sql);
		int last = start + num;
		_sql.append(" ) temp where ROWNUM <= ").append(last);
		_sql.append(" ) WHERE num > ").append(start);
		
		return _sql.toString();
	}

}
