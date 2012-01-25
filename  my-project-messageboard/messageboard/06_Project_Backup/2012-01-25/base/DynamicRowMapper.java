package com.message.base.jdbc.base;

import org.springframework.jdbc.core.RowMapper;

public abstract interface DynamicRowMapper extends RowMapper
{
  public abstract SqlHelper getSqlHelper();

  public abstract void setSqlHelper(SqlHelper paramSqlHelper);
}