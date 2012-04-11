package com.message.base.jdbc.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 不同数据库处理的辅助类
 *
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0, 2012-4-10 上午01:23:20
 */
public abstract class SqlHelper {
	
	/**
	 * 设置long型值
	 * 
	 * @param ps
	 * @param index
	 * @param value
	 * @throws SQLException
	 */
	public void setLongStringValue(PreparedStatement ps, int index, String value) throws SQLException{
		ps.setString(index, value);
	}
	
	/**
	 * 设置clob型值
	 * 
	 * @param ps
	 * @param index
	 * @param value
	 * @throws SQLException
	 */
	public void setClobStringVlaue(PreparedStatement ps, int index, String value) throws SQLException{
		ps.setString(index, value);
	}
	
	/**
	 * 获取long型值
	 * 
	 * @param rs
	 * @param index
	 * @return
	 * @throws SQLException
	 */
	public String getLongStringValue(ResultSet rs, int index) throws SQLException{
		return rs.getString(index);
	}
	
	/**
	 * 获取clob型值
	 * 
	 * @param rs
	 * @param index
	 * @return
	 * @throws SQLException
	 */
	public String getClobStringValue(ResultSet rs, int index) throws SQLException{
		return rs.getString(index);
	}
	
	/**
	 * 获得查询所有条数的sql
	 * 
	 * @param sql
	 * @return
	 */
	public String getCountSql(String sql){
		StringBuffer countSql = new StringBuffer("select count(*) from (");
		countSql.append(sql).append(")");
		
		return countSql.toString();
	}
	
	/**
	 * 获得分页的sql
	 * 由于各个数据库分页语句不同，故让子类自己实现此方法
	 * 
	 * @param sql
	 * @param start
	 * @param num
	 * @return
	 */
	public abstract String getPageSql(String sql, int start, int num);

}
