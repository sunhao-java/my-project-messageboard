package com.message.main.vote.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.message.base.pagination.PaginationSupport;
import com.message.base.utils.StringUtils;
import com.message.main.user.pojo.User;
import com.message.main.user.service.UserService;
import com.message.main.vote.dao.VoteDAO;
import com.message.main.vote.pojo.Vote;
import com.message.main.vote.pojo.VoteAnswer;
import com.message.main.vote.pojo.VoteOption;
import com.message.main.vote.service.VoteService;
import com.message.resource.ResourceType;

/**
 * 投票的service实现
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-2-12 下午12:41:25
 */
public class VoteServiceImpl implements VoteService {
	/**
	 * 单选
	 */
	private static final Long SINGLE_VOTE = Long.valueOf(1);
	/**
	 * 未设置截止日期
	 */
	private static final Long SET_ENDTIME_NO = Long.valueOf(1);
	
	private VoteDAO voteDAO;
	private UserService userService;

	public void setVoteDAO(VoteDAO voteDAO) {
		this.voteDAO = voteDAO;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public boolean saveVote(Vote vote, User user, String[] choices) throws Exception {
		if(vote != null){
			vote.setCreateTime(new Date());
			vote.setCreateUserId(user.getPkId());
			vote.setCreateUsername(user.getTruename());
			vote.setDeleteFlag(ResourceType.DELETE_NO);
			if(SINGLE_VOTE == vote.getType()){
				vote.setMaxOption(1);
			}
			if(SET_ENDTIME_NO == vote.getSetEndTime()){
				vote.setEndTime(null);
			}
			
			vote = this.voteDAO.saveVote(vote);
			
			if(vote != null && choices != null && choices.length > 0){
				List<VoteOption> voteOptions = new ArrayList<VoteOption>();
				for(String choice : choices){
					if(StringUtils.isNotEmpty(choice)){
						VoteOption option = new VoteOption();
						option.setVoteId(vote.getPkId());
						option.setOptionContent(choice);
						
						voteOptions.add(option);
					}
				}
				
				this.voteDAO.saveOptions(voteOptions);
			}
			
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public PaginationSupport listVotes(int start, int num, Vote vote, User user) throws Exception {
		PaginationSupport pagination = this.voteDAO.listAllVote(start, num, vote);
		List<Vote> votes = pagination.getItems();
		for(Vote v : votes){
			if(v != null){
				List<VoteOption> options = this.voteDAO.listOptionByVote(v.getPkId());
				User createUser = this.userService.getUserById(v.getCreateUserId());
				int participantNum = this.voteDAO.getParticipantNum(v.getPkId());
				List<VoteAnswer> answers = this.listAnswerByVote(v.getPkId());
				List<String> myAnswer = new ArrayList<String>();
				for(VoteAnswer answer : answers){
					if(answer.getAnswerUserId().equals(user.getPkId())){
						//登录者(即当前查看人)已经对这个投票投过票了
						v.setIsVote(1L);
						myAnswer.add(this.getOptionById(answer.getAnswer()).getOptionContent());
					}
				}
				v.setMyAnswer(myAnswer);
				v.setVoteOptions(options);
				v.setCreateUser(createUser);
				v.setParticipantNum(participantNum);
			}
		}
		return pagination;
	}

	public boolean saveAnswer(Long voteId, Long[] optionIds, User user) throws Exception {
		if(optionIds.length > 0){
			List<VoteAnswer> voteAnswers = new ArrayList<VoteAnswer>();
			for(Long optionId : optionIds){
				VoteAnswer answer = new VoteAnswer();
				answer.setVoteId(voteId);
				answer.setAnswerUserId(user.getPkId());
				answer.setAnswerUserName(user.getTruename());
				answer.setAnswer(optionId);
				answer.setAnswerDate(new Date());
				
				voteAnswers.add(answer);
			}
			
			this.voteDAO.saveVoteAnswers(voteAnswers);
			
			return true;
		}
		return false;
	}

	public List<VoteAnswer> listAnswerByVote(Long voteId) throws Exception {
		List<VoteAnswer> answers = this.voteDAO.listAnswerByVote(voteId);
		return answers;
	}

	public VoteOption getOptionById(Long pkId) throws Exception {
		return this.voteDAO.getOptionById(pkId);
	}

}
