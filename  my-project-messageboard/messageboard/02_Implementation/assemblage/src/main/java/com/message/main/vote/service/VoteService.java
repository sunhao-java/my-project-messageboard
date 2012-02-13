package com.message.main.vote.service;

import java.util.List;

import com.message.base.pagination.PaginationSupport;
import com.message.main.user.pojo.User;
import com.message.main.vote.pojo.Vote;
import com.message.main.vote.pojo.VoteAnswer;
import com.message.main.vote.pojo.VoteOption;

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
	 * @param user
	 * @return
	 * @throws Exception
	 */
	PaginationSupport listVotes(int start, int num, Vote vote, User user) throws Exception;
	
	/**
	 * 保存用户对某个投票的回答
	 * 
	 * @param voteId
	 * @param optionIds
	 * @param user
	 * @return
	 * @throws Exception
	 */
	boolean saveAnswer(Long voteId, Long[] optionIds, User user) throws Exception;
	
	/**
	 * 根据投票的ID获得参与信息
	 * 
	 * @param voteId
	 * @return
	 * @throws Exception
	 */
	List<VoteAnswer> listAnswerByVote(Long voteId) throws Exception;
	
	/**
	 * 根据ID获得选项
	 * 
	 * @param pkId
	 * @return
	 * @throws Exception
	 */
	VoteOption getOptionById(Long pkId) throws Exception;
	
	/**
	 * 根据ID取得投票实体
	 * 
	 * @param pkId
	 * @return
	 * @throws Exception
	 */
	Vote getVote(Long pkId, User user) throws Exception;
	
	/**
	 * 获得投票结果
	 * 
	 * @param pkId
	 * @return
	 * @throws Exception
	 */
	Vote getVoteResult(Long pkId, User user) throws Exception;
}
