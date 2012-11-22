package com.message.main.letter.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.message.base.utils.SqlUtils;
import com.message.base.utils.StringUtils;
import com.message.base.web.WebInput;
import com.message.base.web.WebOutput;
import com.message.main.ResourceType;
import com.message.main.letter.pojo.Letter;
import com.message.main.letter.pojo.LetterUserRelation;
import com.message.main.letter.service.LetterService;
import com.message.main.login.pojo.LoginUser;

/**
 * 站内信的controller.
 * 
 * @author Danny(sunhao)(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-10-22 下午09:20:19
 */
public class LetterController {
	
	@Autowired
	private LetterService letterService;
	
	/**
	 * 进入收件箱
	 * 
	 * @param in
	 * @param out
	 * @param loginUser
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView inbox(WebInput in, WebOutput out, LoginUser loginUser) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		int num = in.getInt("num", 10);
		int start = SqlUtils.getStartNum(in, num);
		Integer read = in.getInt("r");
		
		params.put("paginationSupport", this.letterService.getInbox(loginUser, read, start, num));
		params.put("current", "inbox");
		return new ModelAndView("letter.inbox", params);
	}
	
	/**
	 * 进入写站内信页面
	 * 
	 * @param in
	 * @param out
	 * @param loginUser
	 * @return
	 */
	public ModelAndView compose(WebInput in, WebOutput out, LoginUser loginUser){
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("current", "compose");
		return new ModelAndView("letter.compose", params);
	}
	
	/**
	 * 发送站内信
	 * 
	 * @param in
	 * @param out
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	public ModelAndView send(WebInput in, WebOutput out, LoginUser loginUser, Letter letter) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		String receiverIds = in.getString("receiverIds_val", StringUtils.EMPTY);
		
		boolean res = this.letterService.send(receiverIds, letter, loginUser);
		
		params.put(ResourceType.AJAX_STATUS, res ? ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE);
		out.toJson(params);
		return null;
	}
	
	/**
	 * 查看站内信详情
	 * 
	 * @param in
	 * @param out
	 * @param loginUser
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView show(WebInput in, WebOutput out, LoginUser loginUser) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		Long lid = in.getLong("lid", Long.valueOf(-1));
		Long luid = in.getLong("luid");
		
		Letter letter = this.letterService.getLetter(lid);
		List<LetterUserRelation> lurs = this.letterService.getReleationByLetter(lid);
		
		if(luid != null){
			//设置成已读
			this.letterService.setReadOrUnRead(luid + "", loginUser, true);
		}
		
		params.put("letter", letter);
		params.put("lurs", lurs);
		return new ModelAndView("letter.show", params);
	}
	
	/**
	 * 删除发件箱站内信的方法
	 * 
	 * @param in
	 * @param out
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	public ModelAndView deleteOutBox(WebInput in, WebOutput out, LoginUser loginUser) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		String lids = in.getString("lids", StringUtils.EMPTY);
		
		boolean res = this.letterService.delete(lids, false);
		
		params.put(ResourceType.AJAX_STATUS, res ? ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE);
		out.toJson(params);
		return null;
	}
	
	/**
	 * 删除收件箱的站内信
	 * 
	 * @param in
	 * @param out
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	public ModelAndView deleteInBox(WebInput in, WebOutput out, LoginUser loginUser) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		String lrids = in.getString("lrids", StringUtils.EMPTY);
		
		boolean res = this.letterService.delete(lrids, true);
		
		params.put(ResourceType.AJAX_STATUS, res ? ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE);
		out.toJson(params);
		return null;
	}
	
	/**
	 * 设置已读/未读
	 * 
	 * @param in
	 * @param out
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	public ModelAndView setRead(WebInput in, WebOutput out, LoginUser loginUser) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		String luids = in.getString("luids", StringUtils.EMPTY);
		boolean type = in.getBoolean("type", true);
		
		boolean res = this.letterService.setReadOrUnRead(luids, loginUser, type);
		
		params.put(ResourceType.AJAX_STATUS, res ? ResourceType.AJAX_SUCCESS : ResourceType.AJAX_FAILURE);
		out.toJson(params);
		return null;
	}
	
	/**
	 * 发件箱
	 * 
	 * @param in
	 * @param out
	 * @param loginUser
	 * @return
	 * @throws Exception
	 */
	public ModelAndView outbox(WebInput in, WebOutput out, LoginUser loginUser) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		int num = in.getInt("num", 10);
		int start = SqlUtils.getStartNum(in, num);
		
		params.put("paginationSupport", this.letterService.getOutBox(loginUser, start, num));
		params.put("current", "outbox");
		return new ModelAndView("letter.outbox", params);
	}
}
