package com.message.base.jdbc.base;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi radix(10) lradix(10) 
// Source File Name:   ColumnMapRowMapper.java

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.core.CollectionFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;

// Referenced classes of package com.wiscom.generic.base.jdbc:
//            DynamicBeanUtils, SqlHelper

public class ColumnMapRowMapper implements RowMapper {

	public ColumnMapRowMapper() {
	}

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		Map mapOfColValues = createColumnMap(columnCount);
		for (int i = 1; i <= columnCount; i++) {
			String key = getColumnKey(JdbcUtils.lookupColumnName(rsmd, i));
			String otherKey = DynamicBeanUtils.decodeUnderscoreName(key);
			int type = rsmd.getColumnType(i);
			Object obj = null;
			if (type == 1 || type == 12)
				obj = getStringValue(rs, i);
			else if (type == -1)
				obj = getLongStringValue(rs, i);
			else if (type == 2005)
				obj = getClobStringValue(rs, i);
			else
				obj = getColumnValue(rs, i);
			mapOfColValues.put(key, obj);
			mapOfColValues.put(otherKey, obj);
		}

		return mapOfColValues;
	}

	protected String getStringValue(ResultSet rs, int i) throws SQLException {
		return rs.getString(i);
	}

	protected String getLongStringValue(ResultSet rs, int i)
			throws SQLException {
		return sqlHelper.getLongStringValue(rs, i);
	}

	protected String getClobStringValue(ResultSet rs, int i)
			throws SQLException {
		return sqlHelper.getClobStringValue(rs, i);
	}

	protected Map createColumnMap(int columnCount) {
		return CollectionFactory
				.createLinkedCaseInsensitiveMapIfPossible(columnCount);
	}

	protected String getColumnKey(String columnName) {
		return columnName;
	}

	protected Object getColumnValue(ResultSet rs, int index)
			throws SQLException {
		return JdbcUtils.getResultSetValue(rs, index);
	}

	public SqlHelper getSqlHelper() {
		return sqlHelper;
	}

	public void setSqlHelper(SqlHelper sqlHelper) {
		this.sqlHelper = sqlHelper;
	}

	private SqlHelper sqlHelper;
}