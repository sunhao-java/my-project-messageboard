package com.message.main.upload.dao;

import com.message.main.upload.pojo.UploadFile;

import java.util.List;

/**
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 12-3-20 下午10:42
 */
public interface UploadDAO {

    /**
     * 保存上传文件的信息
     *
     * @param uploadFile
     * @throws Exception
     */
    Object saveUpload(UploadFile uploadFile) throws Exception;

    /**
     * 获取刚刚上传的文件
     *
     * @param resourceId            资源ID
     * @param uploadId              上传者ID
     * @param resourceType          资源类型
     * @return
     * @throws Exception
     */
    List<Long> listUploadFile(Long resourceId, Long uploadId, Integer resourceType) throws Exception;

    /**
     * 通过pkId获取文件
     *
     * @param pkId
     * @return
     * @throws Exception
     */
    UploadFile loadFile(Long pkId) throws Exception;
}
