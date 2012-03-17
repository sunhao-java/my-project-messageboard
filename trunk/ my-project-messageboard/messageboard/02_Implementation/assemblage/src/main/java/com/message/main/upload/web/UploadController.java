package com.message.main.upload.web;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.message.base.spring.ExtMultiActionController;
import com.message.base.web.WebInput;
import com.message.base.web.WebOutput;
import com.message.main.upload.service.UploadService;
import com.message.resource.ResourceType;

/**
 * 文件上传的controller
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-3-15 下午11:10:35
 */
public class UploadController extends ExtMultiActionController {
	
	private UploadService uploadService;
	
	public void setUploadService(UploadService uploadService) {
		this.uploadService = uploadService;
	}

	private static WebOutput out = null;
	private static WebInput in = null;
	
	/**
	 * 上传头像
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView upload(HttpServletRequest request, HttpServletResponse response) throws Exception{
		out = new WebOutput(request, response);
		in = new WebInput(request);
		Long userId = in.getLong("userId", Long.valueOf(-1));
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//		MultipartFile file = multipartRequest.getFile("file");
		Iterator iterator = multipartRequest.getFileNames();
        while(iterator.hasNext()){
            String fileName = (String) iterator.next();
            MultipartFile file = multipartRequest.getFile(fileName);
            this.uploadService.uploadHead(userId, file);
        }

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_SUCCESS);
		out.toJson(params);
		return null;
	}
}
