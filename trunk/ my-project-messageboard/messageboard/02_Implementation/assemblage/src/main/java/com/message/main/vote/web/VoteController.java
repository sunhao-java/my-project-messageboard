package com.message.main.vote.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import com.message.base.spring.SimpleController;
import com.message.base.utils.SqlUtils;
import com.message.base.utils.StringUtils;
import com.message.base.web.WebInput;
import com.message.base.web.WebOutput;
import com.message.main.ResourceType;
import com.message.main.user.web.UserController;
import com.message.main.vote.pojo.Vote;
import com.message.main.vote.service.VoteService;

/**
 * 投票的web控制器
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-2-12 下午12:41:44
 */
public class VoteController extends SimpleController {
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
		
		try {
			params.put(ResourceType.AJAX_STATUS, this.voteService.saveVote(vote, voteOptions) ?
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
	public ModelAndView listVote(HttpServletRequest request, HttpServletResponse response, Vote vote) throws Exception {
		in = new WebInput(request);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("current", "list");
		int num = in.getInt("num", ResourceType.PAGE_NUM);
		int start = SqlUtils.getStartNum(in, num);

        params.put("pagination", this.voteService.listVotes(start, num, vote));
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
		Long voteId = in.getLong("voteId", 0);
		Long[] answers = in.getLongObjects(voteId + "result[]");
		String comment = in.getString("comment", StringUtils.EMPTY);
        
        params.put(ResourceType.AJAX_STATUS, this.voteService.saveAnswer(voteId, answers, comment) ?
                                            ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE);
		out.toJson(params);
		return null;
	}
	
	/**
	 * 取得单个投票
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView viewVote(HttpServletRequest request, HttpServletResponse response) throws Exception {
		in = new WebInput(request);
		out = new WebOutput(request, response);
		Map<String, Object> params = new HashMap<String, Object>();
		Long voteId = in.getLong("voteId", 0L);
		params.put("show", "detail");

        params.put("vote", this.voteService.getVote(voteId));
		return new ModelAndView("vote.detail", params);
	}
	
	/**
	 * 查看投票结果
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView viewVoteResult(HttpServletRequest request, HttpServletResponse response) throws Exception {
		in = new WebInput(request);
		out = new WebOutput(request, response);
		Map<String, Object> params = new HashMap<String, Object>();
		Long voteId = in.getLong("voteId", 0L);
		params.put("show", "result");

        params.put("vote", this.voteService.getVoteResult(voteId));
		return new ModelAndView("vote.detail", params);
	}
	
	/**
	 * 取出我创建的投票和我参与的投票
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView listMyVote(HttpServletRequest request, HttpServletResponse response) throws Exception {
		in = new WebInput(request);
		out = new WebOutput(request, response);
		Map<String, Object> params = new HashMap<String, Object>();
        int num = in.getInt("num", 3);
        int num_ = in.getInt("num_", 3);
        int start = SqlUtils.getStartNum(in, num);
        int start_ = SqlUtils.getStartNum_(in, num_);

        params.put("myCreateVote", this.voteService.listMyCreateVote(start, num));
        params.put("myAttendVote", this.voteService.listMyAttendVote(start_, num_));
		params.put("current", "myVote");
		return new ModelAndView("vote.listmine", params);
	}
	
}
