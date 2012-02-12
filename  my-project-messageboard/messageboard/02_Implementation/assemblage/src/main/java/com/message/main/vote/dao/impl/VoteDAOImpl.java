package com.message.main.vote.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.message.base.hibernate.impl.GenericHibernateDAOImpl;
import com.message.base.pagination.PaginationSupport;
import com.message.main.vote.dao.VoteDAO;
import com.message.main.vote.pojo.Vote;
import com.message.main.vote.pojo.VoteOption;
import com.message.resource.ResourceType;

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

	public PaginationSupport listAllVote(int start, int num, Vote vote) throws Exception {
		String hql = "from Vote v where v.deleteFlag = :deleteFlag order by v.pkId desc ";
		String countHql = "select count(*) " + hql;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deleteFlag", ResourceType.DELETE_NO);
		return this.getPaginationSupport(hql, countHql, start, num, params);
	}

	@SuppressWarnings("unchecked")
	public List<VoteOption> listOptionByVote(Long voteId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from VoteOption vo where vo.voteId = :voteId ";
		params.put("voteId", voteId);
		return this.findByHQL(hql, params);
	}

}
