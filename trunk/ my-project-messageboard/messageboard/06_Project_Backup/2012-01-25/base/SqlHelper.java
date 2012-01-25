package com.message.base.jdbc.base;

/**
 * 数据库工具类
 * @author sunhao(sunhao.java@gmail.com)
 */
public abstract class SqlHelper {
	
	/**
	 * 获得查询分页的SQL
	 * @param paramString
	 * @param paramInt1
	 * @param paramInt2
	 * @return
	 */
	public abstract String getPageSql(String paramString, int paramInt1, int paramInt2);
	
	/**
	 * 组装查询总记录数的sql
	 * @param sql
	 * @return
	 */
	public String getCountSql(String sql){
		StringBuffer sql_ = new StringBuffer();
		sql_.append("select count(*) from ( ");
		sql_.append(sql);
		sql_.append(" )");
		
		return sql_.toString();
	}
}
