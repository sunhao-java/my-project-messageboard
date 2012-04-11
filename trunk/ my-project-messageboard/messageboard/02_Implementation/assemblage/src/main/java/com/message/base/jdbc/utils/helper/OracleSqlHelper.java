package com.message.base.jdbc.utils.helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.lob.LobHandler;



/**
 * oracle sql helper implement
 *
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0, 2012-4-10 上午07:16:41
 */
public class OracleSqlHelper extends SqlHelper {
	private static final Logger logger = LoggerFactory.getLogger(OracleSqlHelper.class);
	
	private LobHandler lobHandler;

	public String getPageSql(String sql, int start, int num) {
		if(start < 0 || num < 0)
			return sql;
		
		StringBuffer pageSql = new StringBuffer(" SELECT * FROM ( ");
		pageSql.append(" SELECT temp.* ,ROWNUM num FROM ( ");
		pageSql.append(sql);
        int last = start + num;
        pageSql.append(" ) temp where ROWNUM <= ").append(last);
        pageSql.append(" ) WHERE num > ").append(start);

        logger.debug("page sql:" + pageSql.toString());
		
		return pageSql.toString();
	}
	
	public void setClobStringVlaue(PreparedStatement ps, int index, String value)
			throws SQLException {
		this.lobHandler.getLobCreator().setClobAsString(ps, index, value);
	}

	public String getClobStringValue(ResultSet rs, int index)
			throws SQLException {
		return this.lobHandler.getClobAsString(rs, index);
	}

	public LobHandler getLobHandler() {
		return lobHandler;
	}

	public void setLobHandler(LobHandler lobHandler) {
		this.lobHandler = lobHandler;
	}

}
