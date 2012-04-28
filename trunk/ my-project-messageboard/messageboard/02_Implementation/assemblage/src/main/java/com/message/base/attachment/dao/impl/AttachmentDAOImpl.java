package com.message.base.attachment.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.message.base.attachment.dao.AttachmentDAO;
import com.message.base.attachment.pojo.Attachment;
import com.message.base.hibernate.impl.GenericHibernateDAOImpl;

/**
 * 附件的DAO实现
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 12-3-20 下午10:42
 */
public class AttachmentDAOImpl extends GenericHibernateDAOImpl implements AttachmentDAO {
    
    public Object saveAttachment(Attachment attachment) throws Exception {
        return this.saveObject(attachment);
    }

	public List<Long> listAttachment(Long resourceId, Long uploadId, Integer resourceType) throws Exception {
        String hql = "select gf.pkId from Attachment gf where gf.resourceId = :resourceId and gf.uploadId = :uploadId " +
                " and gf.resourceType = :resourceType order by gf.pkId desc ";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("resourceId", resourceId);
        params.put("resourceType", resourceType);
        params.put("uploadId", uploadId);

        return this.findByHQL(hql, params);
    }

    public Attachment loadAttachment(Long pkId) throws Exception {
        return (Attachment) this.loadObject(Attachment.class, pkId);
    }

    public void deleteAttachment(Attachment attachment) throws Exception {
        this.deleteObject(attachment);
    }

    public void updateAttachment(Attachment attachment) throws Exception {
        this.updateObject(attachment);
    }
}
