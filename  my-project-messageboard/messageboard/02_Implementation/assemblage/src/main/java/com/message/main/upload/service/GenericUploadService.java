package com.message.main.upload.service;

import net.sf.json.JSONArray;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.util.List;
import java.util.Map;

/**
 * 上传文件的通用类的接口
 *
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 12-3-18 上午11:39
 */
public interface GenericUploadService {
    /**
     * 一次上传多个文件
     *
     * @param request           上传文件的request
     * @throws Exception
     */
    List<String> uploads(MultipartRequest request, Map params) throws Exception;

    /**
     * 上传单个文件
     *
     * @param file              上传的文件
     * @throws Exception
     */
    String upload(MultipartFile file, Map params) throws Exception;

    /**
     * 获取刚刚上传的文件
     *
     * @param resourceId            资源ID
     * @param uploadId              上传者ID
     * @param resourceType          资源类型
     * @return
     * @throws Exception
     */
    List listUploadFile(Long resourceId, Long uploadId, Integer resourceType) throws Exception;
}
