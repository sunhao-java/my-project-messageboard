package com.message.main.vote.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.message.base.pagination.PaginationSupport;
import com.message.base.pagination.PaginationUtils;
import com.message.base.utils.StringUtils;
import com.message.main.login.pojo.LoginUser;
import com.message.main.login.web.AuthContextHelper;
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
@SuppressWarnings("rawtypes")
public class VoteServiceImpl implements VoteService {
    /**
     * 单选
     */
    private static final Long SINGLE_VOTE = Long.valueOf(1);
    /**
     * 已经投过票了
     */
    private static final Long IS_VOTE_YES = Long.valueOf(1);

    /**
     * 投票没有过期
     */
    private static final String OVER_TIME_NO = "0";
    /**
     * 投票过期了
     */
    private static final String OVER_TIME_YES = "1";

    private VoteDAO voteDAO;
    private UserService userService;

    public void setVoteDAO(VoteDAO voteDAO) {
        this.voteDAO = voteDAO;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public boolean saveVote(Vote vote, String[] choices)
            throws Exception {
        LoginUser loginUser = AuthContextHelper.getAuthContext().getLoginUser();
        if (vote != null) {
            vote.setCreateTime(new Date());
            vote.setCreateUserId(loginUser.getPkId());
            vote.setCreateUsername(loginUser.getTruename());
            vote.setDeleteFlag(ResourceType.DELETE_NO);
            if (SINGLE_VOTE == vote.getType()) {
                vote.setMaxOption(1);
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
    public PaginationSupport listVotes(int start, int num, Vote vote)
            throws Exception {
        LoginUser loginUser = AuthContextHelper.getAuthContext().getLoginUser();
        PaginationSupport pagination = this.voteDAO.listAllVote(start, num, vote);
        List<Vote> votes = pagination.getItems();
        for (Vote v : votes) {
            if (v != null) {
                this.makeVoteWithAnswerAndOption(v, loginUser);
                Date now = new Date();
                if (v.getEndTime() != null && v.getSetEndTime() == 1L) {
                    if (now.after(v.getEndTime())) {
                        v.setIsOverTime(OVER_TIME_YES);
                    } else {
                        v.setIsOverTime(OVER_TIME_NO);
                    }
                }
            }
        }
        return pagination;
    }

    public boolean saveAnswer(Long voteId, Long[] optionIds,
                              String comment) throws Exception {
        LoginUser loginUser = AuthContextHelper.getAuthContext().getLoginUser();
        if (optionIds.length > 0) {
            List<VoteAnswer> voteAnswers = new ArrayList<VoteAnswer>();
            for (Long optionId : optionIds) {
                VoteAnswer answer = new VoteAnswer();
                answer.setVoteId(voteId);
                answer.setAnswerUserId(loginUser.getPkId());
                answer.setAnswerUserName(loginUser.getTruename());
                answer.setAnswer(optionId);
                answer.setAnswerDate(new Date());

                voteAnswers.add(answer);
            }

            if (StringUtils.isNotEmpty(comment)) {
                VoteComment voteComment = new VoteComment();
                voteComment.setCommentContent(comment);
                voteComment.setCommentDate(new Date());
                voteComment.setCommentUserId(loginUser.getPkId());
                voteComment.setCommentUserName(loginUser.getTruename());
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

    public Vote getVote(Long pkId) throws Exception {
        LoginUser loginUser = AuthContextHelper.getAuthContext().getLoginUser();
        Vote vote = this.voteDAO.getVote(pkId);
        if (vote != null) {
            this.makeVoteWithAnswerAndOption(vote, loginUser);
        }
        return vote;
    }

    public Vote getVoteResult(Long pkId) throws Exception {
        LoginUser loginUser = AuthContextHelper.getAuthContext().getLoginUser();
        
        Vote vote = this.voteDAO.getVote(pkId);
        if (vote != null) {
            this.makeVoteWithAnswerAndOption(vote, loginUser);

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

            //TODO by jiaxiuya 2012-01-19
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
                    if (repeatType == true) {
                        continue;
                    }

                    StringBuffer OptionName = new StringBuffer();
                    VoteOption option = this.voteDAO.getOptionById(answers.get(i).getAnswer());
                    OptionName.append(option.getOptionContent());
                    Long answerUserId1 = answers.get(i).getAnswerUserId();
                    for (int j = i + 1; j < answers.size(); j++) {
                        if (answers.get(j) != null && answers.get(j).getPkId() != null) {
                            Long answerUserId2 = answers.get(j).getAnswerUserId();

                            if (answerUserId2.equals(answerUserId1)) {
                                VoteOption option1 = this.voteDAO.getOptionById(answers.get(j).getAnswer());
                                OptionName.append("," + option1.getOptionContent());
                                // 标记重复的节点
                                repeats.add(j + "");
                            }
                        }
                    }

                    User answerUser = this.userService.getUserById(answers.get(i).getAnswerUserId());
                    answers.get(i).setAnswerUser(answerUser);
                    answers.get(i).setOptionName(OptionName.toString());
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
                myAnswer.add(this.getOptionById(answer.getAnswer()).getOptionContent());
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

	public PaginationSupport listMyAttendVote(int start, int num) throws Exception {
        LoginUser loginUser = AuthContextHelper.getAuthContext().getLoginUser();
        // 我回答的投票ID
        PaginationSupport pagination = this.voteDAO.listMyAnswerVoteId(loginUser.getPkId(), start, num);
        List voteIds = pagination.getItems();
        List<Vote> votes = new ArrayList<Vote>();
        if (CollectionUtils.isNotEmpty(voteIds)) {
            for (int i = 0; i < voteIds.size(); i++) {
                Long voteId = Long.valueOf(voteIds.get(i).toString());
                Vote vote = this.voteDAO.getVote(voteId);
                this.makeVoteWithAnswerAndOption(vote, loginUser);

                votes.add(vote);
            }
        }
        return PaginationUtils.makePagination(votes, pagination.getTotalRow(), num, start);
    }

    @SuppressWarnings("unchecked")
	public PaginationSupport listMyCreateVote(int start, int num) throws Exception {
        LoginUser loginUser = AuthContextHelper.getAuthContext().getLoginUser();
        PaginationSupport pagination = this.voteDAO.listVoteByCreateUser(loginUser.getPkId(), start, num);
        List<Vote> votes = pagination.getItems();
        if (CollectionUtils.isNotEmpty(votes)) {
            for (Vote vote : votes) {
                this.makeVoteWithAnswerAndOption(vote, loginUser);

                Date now = new Date();
                if (vote.getEndTime() != null && vote.getSetEndTime() == 1L) {
                    if (now.after(vote.getEndTime())) {
                        vote.setIsOverTime(OVER_TIME_YES);
                    } else {
                        vote.setIsOverTime(OVER_TIME_NO);
                    }
                }
            }
        }
        return pagination;
    }

}
