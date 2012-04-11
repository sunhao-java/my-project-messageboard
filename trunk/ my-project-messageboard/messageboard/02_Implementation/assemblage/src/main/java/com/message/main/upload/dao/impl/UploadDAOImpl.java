package com.message.main.upload.dao.impl;

import com.message.base.hibernate.impl.GenericHibernateDAOImpl;
import com.message.main.upload.dao.UploadDAO;
import com.message.main.upload.pojo.UploadFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 12-3-20 下午10:42
 */
public class UploadDAOImpl extends GenericHibernateDAOImpl implements UploadDAO {
    
    public Object saveUpload(UploadFile uploadFile) throws Exception {
        return this.saveObject(uploadFile);
    }

    @SuppressWarnings("unchecked")
	public List<Long> listUploadFile(Long resourceId, Long uploadId, Integer resourceType) throws Exception {
        String hql = "select gf.pkId from UploadFile gf where gf.resourceId = :resourceId and gf.uploadId = :uploadId " +
                " and gf.resourceType = :resourceType order by gf.pkId desc ";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("resourceId", resourceId);
        params.put("resourceType", resourceType);
        params.put("uploadId", uploadId);

        return this.findByHQL(hql, params);
    }

    public UploadFile loadFile(Long pkId) throws Exception {
        return (UploadFile) this.loadObject(UploadFile.class, pkId);
    }

    public void deleteFile(UploadFile uploadFile) throws Exception {
        this.deleteObject(uploadFile);
    }

    public void updateFile(UploadFile uploadFile) throws Exception {
        this.updateObject(uploadFile);
    }
}
