package com.message.main.reply.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.message.base.cache.utils.ObjectCache;
import com.message.base.hibernate.GenericHibernateDAO;
import com.message.base.jdbc.GenericJdbcDAO;
import com.message.base.pagination.PaginationSupport;
import com.message.main.ResourceType;
import com.message.main.reply.dao.ReplyDAO;
import com.message.main.reply.pojo.Reply;

/**
 * 回复DAO接口实现.
 *
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0, 12-8-31 下午9:41
 */
public class ReplyDAOImpl extends GenericHibernateDAO implements ReplyDAO {
    private GenericJdbcDAO genericJdbcDAO;
    private ObjectCache cache;

    public void setGenericJdbcDAO(GenericJdbcDAO genericJdbcDAO) {
        this.genericJdbcDAO = genericJdbcDAO;
    }

    public void setCache(ObjectCache cache) {
        this.cache = cache;
    }

    public PaginationSupport list(Long resourceId, Integer resourceType, int start, int num) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        String sql = "select r.pk_id from t_message_reply r where r.resource_id = :resourceId and r.resource_type = :resourceType " +
                " and r.delete_flag = :deleteFlag order by r.pk_id desc ";

        params.put("resourceId", resourceId);
        params.put("resourceType", resourceType);
        params.put("deleteFlag", ResourceType.DELETE_NO);
        return this.genericJdbcDAO.getBeanPaginationSupport(sql, null, start, num, params, Reply.class);
    }

    public Long saveReply(Reply reply) throws Exception {
        this.saveObject(reply);
        this.cache.put(reply, reply.getPkId());
        
        return reply.getPkId();
    }

    public void updateReply(Reply reply) throws Exception {
        this.updateObject(reply);

        this.cache.remove(Reply.class, reply.getPkId());
        this.cache.put(reply, reply.getPkId());
    }

    public boolean delete(List<Long> replyIds) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        String sql = "update t_message_reply r set r.delete_flag = :deleteFlag where r.pk_id in (:pkIds)";

        params.put("deleteFlag", ResourceType.DELETE_YES);
        params.put("pkIds", replyIds);

        for(Long pkId : replyIds){
            this.cache.remove(Reply.class, pkId);
        }

        return this.genericJdbcDAO.update(sql, params) == replyIds.size();
    }

    public Reply getReply(Long pkId) throws Exception {
        Reply reply = (Reply) this.cache.get(Reply.class, pkId);
        if(reply == null) {
            reply = (Reply) this.loadObject(Reply.class, pkId);
            this.cache.put(reply, reply.getPkId());
        }

        return reply;
    }
}
