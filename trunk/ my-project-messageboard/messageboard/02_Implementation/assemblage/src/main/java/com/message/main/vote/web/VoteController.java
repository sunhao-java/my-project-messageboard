package com.message.main.vote.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import com.message.base.spring.ExtMultiActionController;
import com.message.base.web.WebInput;
import com.message.base.web.WebOutput;
import com.message.main.user.pojo.User;
import com.message.main.user.web.UserController;
import com.message.main.vote.pojo.Vote;
import com.message.main.vote.service.VoteService;
import com.message.resource.ResourceType;

/**
 * 投票的web控制器
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-2-12 下午12:41:44
 */
public class VoteController extends ExtMultiActionController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private VoteService voteService;
	
	public void setVoteService(VoteService voteService) {
		this.voteService = voteService;
	}

	private static WebInput in = null;
	private static WebOutput out = null;
	
	/**
	 * 进入创建投票的页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView createVote(HttpServletRequest request, HttpServletResponse response) {
		in = new WebInput(request);
		return new ModelAndView("vote.create");
	}
	
	/**
	 * 保存投票
	 * 
	 * @param request
	 * @param response
	 * @param vote
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView saveVote(HttpServletRequest request, HttpServletResponse response, Vote vote) throws Exception{
		JSONObject params = new JSONObject();
		in = new WebInput(request);
		out = new WebOutput(request, response);
		//获得投票的选项内容
		String[] voteOptions = in.getStrings("choice[]");
		User user = (User) in.getSession().getAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION);
		
		try {
			params.put(ResourceType.AJAX_STATUS, this.voteService.saveVote(vote, user, voteOptions) ? 
															ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE);
		} catch (Exception e) {
			e.printStackTrace();
			params.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_FAILURE);
			logger.error(e.getMessage(), e);
		}
		out.toJson(params);
		return null;
	}

}