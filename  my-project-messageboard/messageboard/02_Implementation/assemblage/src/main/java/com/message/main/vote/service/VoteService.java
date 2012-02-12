package com.message.main.vote.service;

import com.message.base.pagination.PaginationSupport;
import com.message.main.user.pojo.User;
import com.message.main.vote.pojo.Vote;

/**
 * 投票的service接口
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-2-12 下午12:41:04
 */
public interface VoteService {
	
	/**
	 * 保存投票以及投票选项实体
	 * 
	 * @param vote
	 * @param user
	 * @param choices
	 * @return
	 * @throws Exception
	 */
	boolean saveVote(Vote vote, User user, String[] choices) throws Exception;
	
	/**
	 * 列出所有投票
	 * 
	 * @param start
	 * @param num
	 * @param vote
	 * @return
	 * @throws Exception
	 */
	PaginationSupport listVotes(int start, int num, Vote vote) throws Exception;
}
