package com.message.main.message.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.message.base.hibernate.impl.GenericHibernateDAOImpl;
import com.message.base.pagination.PaginationSupport;
import com.message.base.utils.SqlUtils;
import com.message.base.utils.StringUtils;
import com.message.main.ResourceType;
import com.message.main.message.dao.MessageDAO;
import com.message.main.message.pojo.Message;

/**
 * 留言操作的DAO
 * @author sunhao(sunhao.java@gmail.com)
 */
public class MessageDAOImpl extends GenericHibernateDAOImpl implements MessageDAO {
	
	public PaginationSupport getAllMessages(int start, int num, Message message)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from Message where deleteFlag = :deleteFlag and isAudit = :isAudit ";
		if(message != null){
			if(StringUtils.isNotEmpty(message.getTitle())) {
				hql += " and title like :title";
				params.put("title", SqlUtils.makeLikeString(StringUtils.trim(message.getTitle())));
			}
			if(StringUtils.isNotEmpty(message.getCreateUsername())){
				hql += " and createUsername like :createUsername ";
				params.put("createUsername", SqlUtils.makeLikeString(StringUtils.trim(message.getCreateUsername())));
			}
			if(message.getBeginTime() != null){
				hql += " and createDate >= :beginTime ";
				params.put("beginTime", message.getBeginTime());
			}
			if(message.getEndTime() != null){
				hql += " and createDate <= :endTime ";
				params.put("endTime", message.getEndTime());
			}
		}
		hql += " order by pkId desc";
		String countHql = "select count(*) " + hql;
		params.put("deleteFlag", ResourceType.DELETE_NO);
		params.put("isAudit", ResourceType.AUDIT_YES);
		PaginationSupport paginationSupport = this.getPaginationSupport(hql, countHql, start, num, params);
		return paginationSupport;
	}

	public Long saveMessage(Message message) throws Exception {
		return ((Message)this.saveObject(message)).getPkId();
	}

	public int getLoginUserMessageCount(Long pkId) throws Exception {
		String hql = "select count(*) from Message m where m.createUserId = ? and m.deleteFlag = ?";
		List params = new ArrayList();
		params.add(pkId);
		params.add(ResourceType.DELETE_NO);
		List list = this.findByHQL(hql, params);
		if(CollectionUtils.isNotEmpty(list)){
			return Integer.parseInt(list.get(0).toString());
		} else {
			return 0;
		}
	}

	public Message getMessageByPkId(Long pkId) throws Exception {
		Message message = (Message) this.loadObject(Message.class, pkId);
		return message;
	}

	public void updateMessage(Message message) throws Exception {
		this.updateObject(message);
	}

	public PaginationSupport getMyMessages(int start, int num, Long userId, Message message) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from Message m where m.createUserId = :createUserId and m.deleteFlag = :deleteFlag and isAudit = :isAudit ";
		if(message != null){
			if(StringUtils.isNotEmpty(message.getTitle())){
				hql += " and m.title like :title ";
				params.put("title", SqlUtils.makeLikeString(StringUtils.trim(message.getTitle())));
			}
			if(StringUtils.isNotEmpty(message.getIp())){
				hql += " and m.ip like :ip ";
				params.put("ip", SqlUtils.makeLikeString(StringUtils.trim(message.getIp())));
			}
			if(message.getBeginTime() != null){
				hql += " and createDate >= :beginTime ";
				params.put("beginTime", message.getBeginTime());
			}
			if(message.getEndTime() != null){
				hql += " and createDate <= :endTime ";
				params.put("endTime", message.getEndTime());
			}
		}
		
		hql += " order by m.pkId desc";
		String countHql = "select count(*) " + hql;
		
		params.put("deleteFlag", ResourceType.DELETE_NO);
		params.put("createUserId", userId);
		params.put("isAudit", ResourceType.AUDIT_YES);
		PaginationSupport paginationSupport = this.getPaginationSupport(hql, countHql, start, num, params);
		return paginationSupport;
	}

	public PaginationSupport listToAuditMessage(int start, int num, Message message, boolean flag) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from Message m where m.deleteFlag = :deleteFlag and m.isAudit = :isAudit order by m.pkId desc ";
		String countHql = "select count(*) " + hql;
		params.put("deleteFlag", ResourceType.DELETE_NO);
		params.put("isAudit", flag ? ResourceType.AUDIT_NOAUDIT : ResourceType.AUDIT_NO);
		return this.getPaginationSupport(hql, countHql, start, num, params);
	}

}
