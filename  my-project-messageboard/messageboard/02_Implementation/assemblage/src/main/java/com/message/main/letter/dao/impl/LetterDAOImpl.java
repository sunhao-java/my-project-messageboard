package com.message.main.letter.dao.impl;

import com.message.base.jdbc.GenericJdbcDAO;
import com.message.main.letter.dao.LetterDAO;

/**
 * 站内信的DAO实现.
 * 
 * @author Danny(sunhao)(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-10-22 下午09:21:38
 */
public class LetterDAOImpl extends GenericJdbcDAO implements LetterDAO {

	public <T> T save(T entity) throws Exception {
		return this.genericInsert(entity);
	}

}
