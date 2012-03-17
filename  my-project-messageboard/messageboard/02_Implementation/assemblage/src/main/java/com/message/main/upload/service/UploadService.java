package com.message.main.upload.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 上传头像的service
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-3-17 上午03:05:54
 */
public interface UploadService {
	
	/**
	 * 上传头像
	 * 
	 * @param userId	用户ID
	 * @param file		spring上传文件的对象
	 * @throws Exception
	 */
	void uploadHead(Long userId, MultipartFile file) throws Exception;
	
}
