package com.message.main.vote.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import com.message.base.spring.ExtMultiActionController;
import com.message.base.utils.SqlUtils;
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
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("current", "create");
		return new ModelAndView("vote.create", params);
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
	
	/**
	 * 列出所有的投票
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView listVote(HttpServletRequest request, HttpServletResponse response, Vote vote){
		in = new WebInput(request);
		User user = (User) in.getSession().getAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("current", "list");
		int num = in.getInt("num", ResourceType.PAGE_NUM);
		int start = SqlUtils.getStartNum(in, num);
		try {
			params.put("pagination", this.voteService.listVotes(start, num, vote, user));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return new ModelAndView("vote.list", params);
	}
	
	/**
	 * 保存投票的回答
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView saveVoteResult(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject params = new JSONObject();
		in = new WebInput(request);
		out = new WebOutput(request, response);
		User user = (User) in.getSession().getAttribute(ResourceType.LOGIN_USER_KEY_IN_SESSION);
		Long voteId = in.getLong("voteId", 0);
		Long[] answers = in.getLongObjects(voteId + "result[]");
		try {
			params.put(ResourceType.AJAX_STATUS, this.voteService.saveAnswer(voteId, answers, user) ? 
												ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE);
		} catch (Exception e) {
			params.put(ResourceType.AJAX_STATUS, ResourceType.AJAX_FAILURE);
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		out.toJson(params);
		return null;
	}

}
