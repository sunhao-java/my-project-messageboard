package com.message.main.vote.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.message.base.hibernate.impl.GenericHibernateDAOImpl;
import com.message.base.pagination.PaginationSupport;
import com.message.main.user.pojo.User;
import com.message.main.vote.dao.VoteDAO;
import com.message.main.vote.pojo.Vote;
import com.message.main.vote.pojo.VoteAnswer;
import com.message.main.vote.pojo.VoteComment;
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

	public void saveVoteAnswers(List<VoteAnswer> answers) throws Exception {
		if(CollectionUtils.isNotEmpty(answers)) {
			for(VoteAnswer answer : answers) {
				this.saveObject(answer);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<VoteAnswer> listAnswerByVote(Long voteId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from VoteAnswer va where va.voteId = :voteId order by va.pkId desc ";
		params.put("voteId", voteId);
		return this.findByHQL(hql, params);
	}

	@SuppressWarnings("unchecked")
	public int getParticipantNum(Long voteId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "select count(*) from (select distinct answer_userid from t_message_vote_answer where vote_id = :voteId)";
		params.put("voteId", voteId);
		List list = this.queryByNativeSQL(sql, params);
		return Integer.parseInt(list.get(0).toString());
	}

	public VoteOption getOptionById(Long pkId) throws Exception {
		return (VoteOption) this.loadObject(VoteOption.class, pkId);
	}

	public Vote getVote(Long pkId) throws Exception {
		return (Vote) this.loadObject(Vote.class, pkId);
	}

	@SuppressWarnings("unchecked")
	public PaginationSupport listVoteByCreateUser(User user, int start, int num) throws Exception {
		String hql = "from Vote v where v.createUserId = :userId order by v.pkId desc ";
        String countHql = "select count(*) " + hql;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", user.getPkId());
		return this.getPaginationSupport(hql, countHql, start, num, params);
	}

	@SuppressWarnings("unchecked")
	public PaginationSupport listMyAnswerVoteId(User user, int start, int num) throws Exception {
		String sql = "select distinct va.vote_id from t_message_vote_answer va where va.answer_userid = :userId order by va.vote_id desc  ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", user.getPkId());
        
		return this.getPaginationByNativeSQL(sql, null, start, num, params);
	}
	
	public Long saveComment(VoteComment voteComment) throws Exception {
		return ((VoteComment)this.saveObject(voteComment)).getPkId();
	}

	public VoteComment getComment(Long voteId, User user) throws Exception {
		String sql = "select vc.pk_id from t_message_vote_comment vc where vc.vote_id = :voteId and vc.comment_userid = :userId";
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("userId", user.getPkId());
		params.put("voteId", voteId);
		List list = this.queryByNativeSQL(sql, params);
		if(list != null && list.size() > 0){
			return (VoteComment) this.loadObject(VoteComment.class, Long.valueOf(list.get(0).toString()));
		}
		return null;
	}

	public List<VoteComment> getAllComments(Long voteId) throws Exception {
		String hql = "from VoteComment vc where vc.voteId = :voteId ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("voteId", voteId);
		return this.findByHQL(hql, params);
	}

}
