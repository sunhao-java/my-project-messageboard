package com.message.base.jdbc.dynamic;

import org.springframework.jdbc.core.RowMapper;

import com.message.base.jdbc.utils.helper.SqlHelper;

/**
 * 动态行映射
 *
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0, 2012-4-10 上午02:19:20
 */
@SuppressWarnings("rawtypes")
public interface DynamicRowMapper extends RowMapper {

    public SqlHelper getSqlHelper();

    public void setSqlHelper(SqlHelper sh);
}
