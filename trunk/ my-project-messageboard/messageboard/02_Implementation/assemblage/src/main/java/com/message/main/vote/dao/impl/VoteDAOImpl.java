package com.message.main.vote.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.message.base.hibernate.impl.GenericHibernateDAOImpl;
import com.message.main.vote.dao.VoteDAO;
import com.message.main.vote.pojo.Vote;
import com.message.main.vote.pojo.VoteOption;

/**
 * 投票的DAO实现
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-2-12 下午12:40:44
 */
public class VoteDAOImpl extends GenericHibernateDAOImpl implements VoteDAO {

	public Vote saveVote(Vote vote) throws Exception {
		return (Vote) this.saveObject(vote);
	}

	public void saveOptions(List<VoteOption> voteOptions) throws Exception {
		if(CollectionUtils.isNotEmpty(voteOptions)) {
			for(VoteOption option : voteOptions) {
				this.saveObject(option);
			}
		}
	}

}
