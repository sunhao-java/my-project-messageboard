package com.message.base.jdbc.key.impl.sequence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;

import com.message.base.jdbc.key.impl.AbstractMaxValueIncrementer;

/**
 *
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0, 2012-4-11 上午08:45:39
 */
public abstract class AbstractSequenceMaxValueIncrementer extends AbstractMaxValueIncrementer {
	
	protected long getNextKey(String name) {
		Connection conn = DataSourceUtils.getConnection(getDataSource());
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			DataSourceUtils.applyTransactionTimeout(stmt, getDataSource());
			rs = stmt.executeQuery(getQuerySequence(name));
			
			if(rs.next())
				return rs.getLong(1);
			else
				throw new DataAccessResourceFailureException("can not get any return from sequence '" + name + "' you give");
			
		} catch(SQLException e) {
			throw new DataAccessResourceFailureException("not found sequence you give '" + name + "'");
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(stmt);
			DataSourceUtils.releaseConnection(conn, getDataSource());
		}
	}
	
	public abstract String getQuerySequence(String name);

}
