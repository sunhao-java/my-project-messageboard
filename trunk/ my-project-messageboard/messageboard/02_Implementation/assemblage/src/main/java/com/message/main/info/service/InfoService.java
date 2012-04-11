package com.message.main.info.service;

import com.message.base.pagination.PaginationSupport;
import com.message.main.info.pojo.Info;

/**
 * 留言板描述的service
 * @author sunhao(sunhao.java@gmail.com)
 */
public interface InfoService {
	/**
	 * 保存留言板描述
	 * @param info
	 * @return
	 * @throws Exception
	 */
	Long saveInfo(Info info) throws Exception;
	
	/**
	 * 获取最新的留言板描述
	 * @return
	 * @throws Exception
	 */
	Info getNewestInfo() throws Exception;
	
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
