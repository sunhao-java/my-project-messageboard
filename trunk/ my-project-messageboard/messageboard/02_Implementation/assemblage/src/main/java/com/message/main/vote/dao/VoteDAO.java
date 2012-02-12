package com.message.main.vote.dao;

import java.util.List;

import com.message.main.vote.pojo.Vote;
import com.message.main.vote.pojo.VoteOption;

/**
 * 投票的DAO接口
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-2-12 下午12:40:04
 */
public interface VoteDAO {

	/**
	 * 保存投票实体
	 * 
	 * @param vote
	 * @return
	 * @throws Exception
	 */
	Vote saveVote(Vote vote) throws Exception;

	/**
	 * 批量保存投票选项
	 * 
	 * @param voteOptions
	 * @throws Exception
	 */
	void saveOptions(List<VoteOption> voteOptions) throws Exception;

}
