package com.message.base.attachment.dao;

import java.util.List;

import com.message.base.attachment.pojo.Attachment;

/**
 * 附件的DAO接口
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 12-3-20 下午10:42
 */
public interface AttachmentDAO {

    /**
     * 保存上传文件的信息
     *
     * @param attachment		附件实体
     * @throws Exception
     */
    Object saveAttachment(Attachment attachment) throws Exception;

    /**
     * 获取刚刚上传的文件
     *
     * @param resourceId            资源ID
     * @param uploadId              上传者ID
     * @param resourceType          资源类型
     * @return
     * @throws Exception
     */
    List<Long> listAttachment(Long resourceId, Long uploadId, Integer resourceType) throws Exception;

    /**
     * 通过pkId获取文件
     *
     * @param pkId
     * @return
     * @throws Exception
     */
    Attachment loadAttachment(Long pkId) throws Exception;

    /**
     * 删除上传文件
     * 
     * @param uploadFile
     * @throws Exception
     */
    void deleteAttachment(Attachment attachment) throws Exception;

    /**
     * 更新附件实体
     * 
     * @param uploadFile
     * @throws Exception
     */
    void updateAttachment(Attachment attachment) throws Exception;
}
