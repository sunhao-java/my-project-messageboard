package com.message.base.jdbc.dynamic;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import org.springframework.core.CollectionFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;

import com.message.base.jdbc.utils.helper.SqlHelper;

/**
 * row mapper
 *
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0, 2012-4-10 上午12:37:24
 */
public class ColumnMapRowMapper implements RowMapper {
	
	private SqlHelper sqlHelper;

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		/**
		 * ResultSetMetaData : 可用于获取关于 ResultSet 对象中列的类型和属性信息的对象
		 * getMetaDate() : 获取此 ResultSet 对象的列的编号、类型和属性
		 */
		ResultSetMetaData metaData = rs.getMetaData();
		//获取查询得到的行数
		int columnCount = metaData.getColumnCount();
		Map valueMap = this.createColumnMap(columnCount);
		
		for(int i = 0; i < columnCount; i++){
			//获取每一列的标题
			String key = JdbcUtils.lookupColumnName(metaData, i);
			//数据库的字段名转成类的字段名(pk_id --> pkId)
			String otherKey = DynamicBeanUtils.underscoreName(key);
			//获取指定列的 SQL 类型
			int type = metaData.getColumnType(i);
			
			Object obj = null;
			if(type == Types.CHAR || type == Types.VARCHAR){
				//字符类型的
				obj = this.getStringValue(rs, i);
			} else if(type == Types.LONGVARCHAR) {
				//long
				obj = this.getLongStringValue(rs, i);
			} else if(type == Types.CLOB) {
				//clob
				obj = this.getColumnValue(rs, i);
			} else {
				//other
				obj = this.getColumnValue(rs, i);
			}
			
			valueMap.put(key, obj);
			valueMap.put(otherKey, obj);
		}
		
		return valueMap;
	}
	
	protected Map createColumnMap(int columnCount){
		//TODO what is LinkedCaseInsensitiveMap?
		return CollectionFactory.createLinkedCaseInsensitiveMapIfPossible(columnCount);
	}
	
	protected String getStringValue(ResultSet rs, int column) throws SQLException{
		return rs.getString(column);
	}
	
	protected String getLongStringValue(ResultSet rs, int column) throws SQLException{
		return sqlHelper.getLongStringValue(rs, column);
	}
	
	protected String getClobStringValue(ResultSet rs, int column) throws SQLException{
		return sqlHelper.getClobStringValue(rs, column);
	}
	
	protected Object getColumnValue(ResultSet rs, int column) throws SQLException{
		return JdbcUtils.getResultSetValue(rs, column);
	}

	public void setSqlHelper(SqlHelper sqlHelper) {
		this.sqlHelper = sqlHelper;
	}

	public SqlHelper getSqlHelper() {
		return sqlHelper;
	}

}
