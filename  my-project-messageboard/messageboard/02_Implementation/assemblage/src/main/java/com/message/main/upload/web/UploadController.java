package com.message.main.upload.web;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.message.main.upload.service.GenericUploadService;
import net.sf.json.JSONObject;
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

    /**
     * 上传头像的service
     */
	private UploadService uploadService;

    private GenericUploadService genericUploadService;
	
	public void setUploadService(UploadService uploadService) {
		this.uploadService = uploadService;
	}

    public void setGenericUploadService(GenericUploadService genericUploadService) {
        this.genericUploadService = genericUploadService;
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
        Map<String, Object> params = new HashMap<String, Object>();
        boolean isHeadImage = in.getBoolean("headImage", Boolean.TRUE);
		Long userId = in.getLong("userId", Long.valueOf(-1));
        Long resourceId = in.getLong(ResourceType.MAP_KEY_RESOURCE_ID, Long.valueOf(-1));
        Integer resourceType = in.getInt(ResourceType.MAP_KEY_RESOURCE_TYPE, Integer.valueOf(-1));
        Long uploadId = in.getLong(ResourceType.MAP_KEY_UPLOAD_ID, Long.valueOf(-1));

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        //是上传头像
        if(isHeadImage){
            Iterator iterator = multipartRequest.getFileNames();
            while(iterator.hasNext()){
                String fileName = (String) iterator.next();
                MultipartFile file = multipartRequest.getFile(fileName);
                this.uploadService.uploadHead(userId, file);
            }
        } else {
            Map uploadParams = new HashMap();
            uploadParams.put(ResourceType.MAP_KEY_RESOURCE_ID, resourceId);
            uploadParams.put(ResourceType.MAP_KEY_RESOURCE_TYPE, resourceType);
            uploadParams.put(ResourceType.MAP_KEY_UPLOAD_ID, uploadId);
            //上传文件
            List<String> results = this.genericUploadService.uploads(multipartRequest, uploadParams);
            params.put("results", results);
        }


		params.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_SUCCESS);
		out.toJson(params);
		return null;
	}

    public ModelAndView showUploads(HttpServletRequest request, HttpServletResponse response) throws Exception {
        out = new WebOutput(request, response);
		in = new WebInput(request);
        JSONObject params = new JSONObject();

        Long resourceId = in.getLong(ResourceType.MAP_KEY_RESOURCE_ID, Long.valueOf(-1));
        Integer resourceType = in.getInt(ResourceType.MAP_KEY_RESOURCE_TYPE, Integer.valueOf(-1));
        Long uploadId = in.getLong(ResourceType.MAP_KEY_UPLOAD_ID, Integer.valueOf(-1));

        List json = this.genericUploadService.listUploadFile(resourceId, uploadId, resourceType);

        if(json != null){
            System.out.println(json);
            params.put("files", json);
        } else {
            logger.warn("can not find any files!");
        }

        params.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_SUCCESS);
		out.toJson(params);
        return null;
    }

}
