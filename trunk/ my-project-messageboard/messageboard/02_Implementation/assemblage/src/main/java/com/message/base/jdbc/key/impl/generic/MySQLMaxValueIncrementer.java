package com.message.base.jdbc.key.impl.generic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;

import com.message.base.jdbc.key.impl.AbstractMaxValueIncrementer;

/**
 *
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0, 2012-4-11 上午09:15:08
 * @see
 */
public class MySQLMaxValueIncrementer extends AbstractMaxValueIncrementer {
	/** The SQL string for retrieving the new sequence value */
	private static final String VALUE_SQL = "select last_insert_id()";

	/** The next id to serve */
	private long nextId = 0;

	/** The max id to serve */
	private long maxId = 0;
	
	/** The number of keys buffered in a cache */
	private int cacheSize = 1;

	protected synchronized long getNextKey(String name) throws DataAccessException {
		if (this.maxId == this.nextId) {
			/*
			* Need to use straight JDBC code because we need to make sure that the insert and select
			* are performed on the same connection (otherwise we can't be sure that last_insert_id()
			* returned the correct value)
			*/
			Connection con = DataSourceUtils.getConnection(getDataSource());
			Statement stmt = null;
			try {
				stmt = con.createStatement();
				DataSourceUtils.applyTransactionTimeout(stmt, getDataSource());
				// Increment the sequence column...
				stmt.executeUpdate("update "+ name + " set " + name +
						" = last_insert_id(" + name + " + " + getCacheSize() + ")");
				// Retrieve the new max of the sequence column...
				ResultSet rs = stmt.executeQuery(VALUE_SQL);
				try {
					if (!rs.next()) {
						throw new DataAccessResourceFailureException("last_insert_id() failed after executing an update");
					}
					this.maxId = rs.getLong(1);
				}
				finally {
					JdbcUtils.closeResultSet(rs);
				}
				this.nextId = this.maxId - getCacheSize() + 1;
			}
			catch (SQLException ex) {
				throw new DataAccessResourceFailureException("Could not obtain last_insert_id()", ex);
			}
			finally {
				JdbcUtils.closeStatement(stmt);
				DataSourceUtils.releaseConnection(con, getDataSource());
			}
		}
		else {
			this.nextId++;
		}
		return this.nextId;
	}

	/**
	 * Return the number of buffered keys.
	 */
	public int getCacheSize() {
		return cacheSize;
	}

	/**
	 * Set the number of buffered keys.
	 */
	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}

}
