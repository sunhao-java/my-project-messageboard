package com.message.main.vote.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.message.base.pagination.PaginationUtils;
import org.apache.commons.collections.CollectionUtils;

import com.message.base.pagination.PaginationSupport;
import com.message.base.utils.StringUtils;
import com.message.main.user.pojo.User;
import com.message.main.user.service.UserService;
import com.message.main.vote.dao.VoteDAO;
import com.message.main.vote.pojo.Vote;
import com.message.main.vote.pojo.VoteAnswer;
import com.message.main.vote.pojo.VoteComment;
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
	/**
	 * 已经投过票了
	 */
	private static final Long IS_VOTE_YES = Long.valueOf(1);

	private VoteDAO voteDAO;
	private UserService userService;

	public void setVoteDAO(VoteDAO voteDAO) {
		this.voteDAO = voteDAO;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public boolean saveVote(Vote vote, User user, String[] choices)
			throws Exception {
		if (vote != null) {
			vote.setCreateTime(new Date());
			vote.setCreateUserId(user.getPkId());
			vote.setCreateUsername(user.getTruename());
			vote.setDeleteFlag(ResourceType.DELETE_NO);
			if (SINGLE_VOTE == vote.getType()) {
				vote.setMaxOption(1);
			}
			if (SET_ENDTIME_NO == vote.getSetEndTime()) {
				vote.setEndTime(null);
			}

			vote = this.voteDAO.saveVote(vote);

			if (vote != null && choices != null && choices.length > 0) {
				List<VoteOption> voteOptions = new ArrayList<VoteOption>();
				for (String choice : choices) {
					if (StringUtils.isNotEmpty(choice)) {
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
	public PaginationSupport listVotes(int start, int num, Vote vote, User user)
			throws Exception {
		PaginationSupport pagination = this.voteDAO.listAllVote(start, num,
				vote);
		List<Vote> votes = pagination.getItems();
		for (Vote v : votes) {
			if (v != null) {
				/*
				 * List<VoteOption> options =
				 * this.voteDAO.listOptionByVote(v.getPkId()); User createUser =
				 * this.userService.getUserById(v.getCreateUserId()); int
				 * participantNum = this.voteDAO.getParticipantNum(v.getPkId());
				 * List<VoteAnswer> answers =
				 * this.listAnswerByVote(v.getPkId()); List<String> myAnswer =
				 * new ArrayList<String>(); for(VoteAnswer answer : answers){
				 * if(answer.getAnswerUserId().equals(user.getPkId())){
				 * //登录者(即当前查看人)已经对这个投票投过票了 v.setIsVote(IS_VOTE_YES);
				 * myAnswer.add
				 * (this.getOptionById(answer.getAnswer()).getOptionContent());
				 * } } v.setMyAnswer(myAnswer); v.setVoteOptions(options);
				 * v.setCreateUser(createUser);
				 * v.setParticipantNum(participantNum);
				 */
				this.makeVoteWithAnswerAndOption(v, user);
			}
		}
		return pagination;
	}

	public boolean saveAnswer(Long voteId, Long[] optionIds, User user,
			String comment) throws Exception {
		if (optionIds.length > 0) {
			List<VoteAnswer> voteAnswers = new ArrayList<VoteAnswer>();
			for (Long optionId : optionIds) {
				VoteAnswer answer = new VoteAnswer();
				answer.setVoteId(voteId);
				answer.setAnswerUserId(user.getPkId());
				answer.setAnswerUserName(user.getTruename());
				answer.setAnswer(optionId);
				answer.setAnswerDate(new Date());

				voteAnswers.add(answer);
			}

			if (StringUtils.isNotEmpty(comment)) {
				VoteComment voteComment = new VoteComment();
				voteComment.setCommentContent(comment);
				voteComment.setCommentDate(new Date());
				voteComment.setCommentUserId(user.getPkId());
				voteComment.setCommentUserName(user.getTruename());
				voteComment.setDeleteFlag(ResourceType.DELETE_NO);
				voteComment.setVoteId(voteId);

				this.voteDAO.saveComment(voteComment);
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

	public Vote getVote(Long pkId, User user) throws Exception {
		Vote vote = this.voteDAO.getVote(pkId);
		if (vote != null) {
			this.makeVoteWithAnswerAndOption(vote, user);
		}
		return vote;
	}

	public Vote getVoteResult(Long pkId, User user) throws Exception {
		Vote vote = this.voteDAO.getVote(pkId);
		if (vote != null) {
			this.makeVoteWithAnswerAndOption(vote, user);

			List<VoteOption> options = vote.getVoteOptions();
			List<VoteAnswer> answers = vote.getAnswers();

			for (int i = 0; i < options.size(); i++) {
				VoteOption option = options.get(i);
				int m = 0;
				for (int j = 0; j < answers.size(); j++) {
					VoteAnswer answer = answers.get(j);
					if (option.getPkId().equals(answer.getAnswer())) {
						m += 1;
					}
				}
				option.setSelectNum(m);
				int percent = 0;
				if (answers.size() != 0) {
					percent = m * 100 / answers.size();
				}

				option.setSelectPercent(percent);
			}

			// for(VoteAnswer answer : answers){
			// this.makeAnswer(answer, user);
			// }

			//TODO by jiabi 2012-01-19
			List<String> repeats = new ArrayList<String>();
			for (int i = 0; i < answers.size(); i++) {
				if (answers.get(i) != null && answers.get(i).getPkId() != null) {
					Boolean repeatType = false;
					if (repeats != null) {
						for (String repeat : repeats) {
							if (i == Integer.parseInt(repeat)) {
								repeatType = true;
								break;
							}
						}
					}
					if (repeatType == true)
						continue;
					StringBuffer OptionName = new StringBuffer();
					VoteOption option = this.voteDAO.getOptionById(answers.get(
							i).getAnswer());
					OptionName.append(option.getOptionContent());
					Long answerUserId1 = answers.get(i).getAnswerUserId();
					for (int j = i + 1; j < answers.size(); j++) {
						if (answers.get(j) != null
								&& answers.get(j).getPkId() != null) {
							Long answerUserId2 = answers.get(j)
									.getAnswerUserId();

							if (answerUserId2.equals(answerUserId1)) {
								VoteOption option1 = this.voteDAO
										.getOptionById(answers.get(j)
												.getAnswer());
								OptionName.append(","
										+ option1.getOptionContent());
								// 标记重复的节点
								repeats.add(j + "");
							}
						}
					}

					User answerUser = this.userService.getUserById(answers.get(
							i).getAnswerUserId());
					answers.get(i).setAnswerUser(answerUser);
					answers.get(i).setOptionName(OptionName.toString());
					// this.makeAnswer(answer, user);
				}
			}
			// 去除重复的选项
			int flags = 0;
			for (int i = 0; i < repeats.size(); i++) {
				int j = Integer.parseInt(repeats.get(i));
				answers.remove(j - (flags++));
			}
		}

		return vote;
	}

	/**
	 * 往vote中放入一些信息
	 * 
	 * @param vote
	 * @param user
	 * @return
	 * @throws Exception
	 */
	private Vote makeVoteWithAnswerAndOption(Vote vote, User user)
			throws Exception {
		List<VoteOption> options = this.voteDAO
				.listOptionByVote(vote.getPkId());
		List<VoteAnswer> answers = this.listAnswerByVote(vote.getPkId());
		User createUser = this.userService.getUserById(vote.getCreateUserId());
		int participantNum = this.voteDAO.getParticipantNum(vote.getPkId());
		VoteComment voteComment = this.voteDAO.getComment(vote.getPkId(), user);
		List<VoteComment> comments = this.voteDAO
				.getAllComments(vote.getPkId());

		List<String> myAnswer = new ArrayList<String>();
		for (VoteAnswer answer : answers) {
			if (answer.getAnswerUserId().equals(user.getPkId())) {
				// 登录者(即当前查看人)已经对这个投票投过票了
				vote.setIsVote(IS_VOTE_YES);
				myAnswer.add(this.getOptionById(answer.getAnswer())
						.getOptionContent());
			}
		}
		vote.setMyAnswer(myAnswer);

		vote.setAnswers(answers);
		vote.setVoteOptions(options);
		vote.setCreateUser(createUser);
		vote.setParticipantNum(participantNum);
		vote.setComment(voteComment);
		vote.setComments(comments);

		return vote;
	}

	/**
	 * 往投票中加入所有投票回答（除了当前登录人的自己的回答）
	 * 
	 * @param vote
	 *            需要处理的投票
	 * @param user
	 *            当前登录人
	 * @return
	 */
	private Vote setAllAnswerInVote(Vote vote, User user) {
		return vote;
	}

	@SuppressWarnings("unchecked")
	public PaginationSupport listMyAttendVote(User user, int start, int num)
			throws Exception {
		// 我回答的投票ID
		PaginationSupport pagination = this.voteDAO.listMyAnswerVoteId(user,
				start, num);
		List voteIds = pagination.getItems();
		List<Vote> votes = new ArrayList<Vote>();
		if (CollectionUtils.isNotEmpty(voteIds)) {
			for (int i = 0; i < voteIds.size(); i++) {
				Long voteId = Long.valueOf(voteIds.get(i).toString());
				Vote vote = this.voteDAO.getVote(voteId);
				this.makeVoteWithAnswerAndOption(vote, user);

				votes.add(vote);
			}
		}
		return PaginationUtils.makePagination(votes, pagination.getTotalRow(),
				num, start);
	}

	public PaginationSupport listMyCreateVote(User user, int start, int num)
			throws Exception {
		PaginationSupport pagination = this.voteDAO.listVoteByCreateUser(user,
				start, num);
		List<Vote> votes = pagination.getItems();
		if (CollectionUtils.isNotEmpty(votes)) {
			for (Vote vote : votes) {
				this.makeVoteWithAnswerAndOption(vote, user);
			}
		}
		return pagination;
	}

}
