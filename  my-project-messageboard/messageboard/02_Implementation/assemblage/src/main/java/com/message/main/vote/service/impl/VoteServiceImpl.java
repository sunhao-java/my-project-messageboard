package com.message.main.vote.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.message.base.utils.StringUtils;
import com.message.main.user.pojo.User;
import com.message.main.vote.dao.VoteDAO;
import com.message.main.vote.pojo.Vote;
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

	public void setVoteDAO(VoteDAO voteDAO) {
		this.voteDAO = voteDAO;
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

}
