package com.message.utils;

import org.apache.commons.lang.math.NumberUtils;

public class SqlUtils {
	
	public static String getCountSql(String sql){
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("select count(*) from ( ");
		stringBuffer.append(sql);
		stringBuffer.append(" )");
		
		return stringBuffer.toString();
	}
	
	public static int getStartNum(WebInput in, int num){
		int page = NumberUtils.toInt(StringUtils.trimToNull(in.getString("page")), 1);
        if (page < 1) {
            page = 1;
        }
        page--;
        
        return page*num;
	}
}
