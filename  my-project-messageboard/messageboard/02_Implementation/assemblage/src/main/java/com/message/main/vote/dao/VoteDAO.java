package com.message.main.vote.dao;

import java.util.List;

import com.message.base.pagination.PaginationSupport;
import com.message.main.user.pojo.User;
import com.message.main.vote.pojo.Vote;
import com.message.main.vote.pojo.VoteAnswer;
import com.message.main.vote.pojo.VoteComment;
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
	
	/**
	 * 获得所有投票，只有投票实体
	 * 
	 * @param start
	 * @param num
	 * @param vote
	 * @return
	 * @throws Exception
	 */
	PaginationSupport listAllVote(int start, int num, Vote vote) throws Exception;
	
	/**
	 * 根据投票ID获得选项的list集合
	 * 
	 * @param voteId
	 * @return
	 * @throws Exception
	 */
	List<VoteOption> listOptionByVote(Long voteId) throws Exception;
	
	/**
	 * 批量保存投票答案
	 * 
	 * @param answers
	 * @throws Exception
	 */
	void saveVoteAnswers(List<VoteAnswer> answers) throws Exception;
	
	/**
	 * 根据投票的ID获得参与信息
	 * 
	 * @param voteId
	 * @return
	 * @throws Exception
	 */
	List<VoteAnswer> listAnswerByVote(Long voteId) throws Exception;
	
	/**
	 * 根据投票的ID获得参与人数
	 * 
	 * @param voteId
	 * @return
	 * @throws Exception
	 */
	int getParticipantNum(Long voteId) throws Exception;
	
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
	Vote getVote(Long pkId) throws Exception;
	
	/**
	 * 根据投票创建者获得投票
	 * 
	 * @param user
     * @param start
     * @param num
	 * @return
	 * @throws Exception
	 */
	PaginationSupport listVoteByCreateUser(User user, int start, int num) throws Exception;
	
	/**
	 * 返回我回答的投票ID
	 * 
	 * @param user
     * @param start
     * @param num
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	PaginationSupport listMyAnswerVoteId(User user, int start, int num) throws Exception;
	
	/**
	 * 保存投票评论
	 * 
	 * @param voteComment
	 * @return
	 * @throws Exception
	 */
	public Long saveComment(VoteComment voteComment) throws Exception;
	
	/**
	 * 根据投票的ID获得评论
	 * 
	 * @param voteId
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public VoteComment getComment(Long voteId, User user) throws Exception;

}
