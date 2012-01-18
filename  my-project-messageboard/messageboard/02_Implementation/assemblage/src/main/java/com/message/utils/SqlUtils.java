package com.message.utils;

import org.apache.commons.lang.math.NumberUtils;

/**
 * 对sql/hql语句的处理类
 * @author sunhao(sunhao.java@gmail.com)
 */
public class SqlUtils {
	
	/**
	 * 获得query.setFirstResult(start)中的start值
	 * @param in
	 * @param num
	 * @return
	 */
	public static int getStartNum(WebInput in, int num){
		int page = NumberUtils.toInt(StringUtils.trimToNull(in.getString("page")), 1);
        if (page < 1) {
            page = 1;
        }
        page--;
        
        return page*num;
	}
	
	/**
	 * 组装sql中like后的字符串(%...%)
	 * @param likeString
	 * @return
	 */
	public static String makeLikeString(String likeString){
		if(StringUtils.isNotEmpty(StringUtils.trim(likeString))){
			likeString = "%" + StringUtils.trim(likeString) + "%";
		}
		return likeString;
	}
}
