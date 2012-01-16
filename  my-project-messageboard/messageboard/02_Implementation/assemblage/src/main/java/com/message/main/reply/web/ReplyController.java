package com.message.main.reply.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.message.main.reply.pojo.Reply;
import com.message.main.reply.service.ReplyService;
import com.message.utils.WebInput;
import com.message.utils.WebOutput;
import com.message.utils.resource.ResourceType;

public class ReplyController extends MultiActionController {
	private static final Logger logger = LoggerFactory.getLogger(ReplyController.class);
	
	private WebInput in = null;
	private WebOutput out = null;
	
	private ReplyService replyService;
	
	public void setReplyService(ReplyService replyService) {
		this.replyService = replyService;
	}

	/**
	 * 删除回复
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView deleteReply(HttpServletRequest request, HttpServletResponse response) throws Exception{
		in = new WebInput(request);
		out = new WebOutput(request, response);
		JSONObject params = new JSONObject();
		Long pkId = in.getLong("replyPkId", 0L);
		try {
			params.put(ResourceType.AJAX_STATUS, this.replyService.deleteReplyById(pkId) ? ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			params.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_FAILURE);
			e.printStackTrace();
		}
		out.toJson(params);
		
		return null;
	}
	
	/**
	 * 发表回复
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView replyMessage(HttpServletRequest request, HttpServletResponse response, Reply reply) throws Exception{
		in = new WebInput(request);
		out = new WebOutput(request, response);
		JSONObject params = new JSONObject();
		
		if(reply != null){
			try {
				this.replyService.saveReply(reply);
				params.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_SUCCESS);
			} catch (Exception e) {
				params.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_FAILURE);
				logger.error(e.getMessage(), e);
				e.printStackTrace();
			}
		}
		out.toJson(params);
		return null;
	}
}
