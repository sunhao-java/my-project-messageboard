package com.message.main.info.dao;

import java.util.List;

import com.message.base.pagination.PaginationSupport;
import com.message.main.info.pojo.Info;

/**
 * 留言板描述的DAO
 * @author sunhao(sunhao.java@gmail.com)
 */
public interface InfoDAO {
	/**
	 * 保存留言板描述
	 * @param info
	 * @return
	 * @throws Exception
	 */
	Long saveInfo(Info info) throws Exception;
	
	/**
	 * 获取所有留言板描述
	 * @return
	 * @throws Exception
	 */
	List<Info> getAllInfo() throws Exception;
	
	/**
	 * 获取留言板描述历史的分页对象
	 * @param start
	 * @param num
	 * @param info
	 * @return
	 * @throws Exception
	 */
	PaginationSupport getInfoHistroy(int start, int num, Info info) throws Exception;
}
