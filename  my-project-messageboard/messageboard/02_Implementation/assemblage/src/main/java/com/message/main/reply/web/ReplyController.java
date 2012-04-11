package com.message.main.reply.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import com.message.base.spring.ExtMultiActionController;
import com.message.base.web.WebInput;
import com.message.base.web.WebOutput;
import com.message.main.reply.pojo.Reply;
import com.message.main.reply.service.ReplyService;
import com.message.resource.ResourceType;

public class ReplyController extends ExtMultiActionController {
	@SuppressWarnings("unused")
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
        params.put(ResourceType.AJAX_STATUS, this.replyService.deleteReplyById(pkId) ? ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE);
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
            this.replyService.saveReply(reply);
            params.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_SUCCESS);
		}
		out.toJson(params);
		return null;
	}
}
