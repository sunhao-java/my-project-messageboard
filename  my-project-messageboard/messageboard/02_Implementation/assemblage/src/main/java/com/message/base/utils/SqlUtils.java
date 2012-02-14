package com.message.base.utils;

import org.apache.commons.lang.math.NumberUtils;

import com.message.base.web.WebInput;

/**
 * 对sql/hql语句的处理类
 *
 * @author sunhao(sunhao.java@gmail.com)
 */
public class SqlUtils {

    /**
     * 获得query.setFirstResult(start)中的start值
     *
     * @param in
     * @param num
     * @return
     */
    public static int getStartNum(WebInput in, int num) {
        int page = NumberUtils.toInt(StringUtils.trimToNull(in.getString("page")), 1);
        if (page < 1) {
            page = 1;
        }
        page--;

        return page * num;
    }

    /**
     * 获得query.setFirstResult(start)中的start值
     *
     * @param in
     * @param num
     * @return
     */
    public static int getStartNum_(WebInput in, int num) {
        int page = NumberUtils.toInt(StringUtils.trimToNull(in.getString("page_")), 1);
        if (page < 1) {
            page = 1;
        }
        page--;

        return page * num;
    }

    /**
     * 组装sql中like后的字符串(%...%)
     *
     * @param likeString
     * @return
     */
    public static String makeLikeString(String likeString) {
        if (StringUtils.isNotEmpty(StringUtils.trim(likeString))) {
            likeString = "%" + StringUtils.trim(likeString) + "%";
        }
        return likeString;
    }

    public static String getCountSql(String sql) {
        StringBuffer sb = new StringBuffer();
        if (StringUtils.isNotEmpty(sql)) {
            sb.append("select count(*) from (").append(sql).append(")");
            return sb.toString();
        } else {
            return StringUtils.EMPTY;
        }
    }
}
