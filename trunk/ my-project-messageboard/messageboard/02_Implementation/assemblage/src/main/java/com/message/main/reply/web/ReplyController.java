package com.message.main.reply.web;

import com.message.base.pagination.PaginationSupport;
import com.message.base.spring.AjaxControllerInternal;
import com.message.base.spring.ControllerHelper;
import com.message.base.spring.SimpleController;
import com.message.base.utils.SqlUtils;
import com.message.base.utils.StringUtils;
import com.message.base.web.WebInput;
import com.message.base.web.WebOutput;
import com.message.main.ResourceType;
import com.message.main.login.pojo.LoginUser;
import com.message.main.reply.service.ReplyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * reply模块web控制层.
 *
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0, 12-8-31 下午9:44
 */
public class ReplyController extends SimpleController {

	@Autowired
    private ReplyService replyService;

    /**
     * 列出回复
     * 
     * @param in
     * @param out
     * @param loginUser
     * @return
     * @throws Exception
     */
    public ModelAndView index(WebInput in, WebOutput out, LoginUser loginUser) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        Long resourceId = in.getLong("resourceId", Long.valueOf(-1));
        Integer resourceType = in.getInt("resourceType", Integer.valueOf(-1));
        int num = in.getInt("num", 5);
		int start = SqlUtils.getStartNum(in, num);

        PaginationSupport ps = this.replyService.list(resourceId, resourceType, start, num);
        params.put("reply", ps);
        out.toJson(params);
        return null;
    }

    /**
     * 保存回复
     * 
     * @param in
     * @param out
     * @param loginUser
     * @return
     * @throws Exception
     */
    public ModelAndView reply(WebInput in, WebOutput out, LoginUser loginUser) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        Long resourceId = in.getLong("resourceId", Long.valueOf(-1));
        Integer resourceType = in.getInt("resourceType", Integer.valueOf(-1));
        String title = in.getString("title", StringUtils.EMPTY);
        String content = in.getString("content", StringUtils.EMPTY);
        Long pkId = this.replyService.saveOrUpdateReply(resourceId, resourceType, title, content, loginUser, null);

        params.put(ResourceType.AJAX_STATUS, (pkId == null || Long.valueOf(-1).equals(pkId)) ?
                ResourceType.AJAX_FAILURE : ResourceType.AJAX_SUCCESS);
        params.put("replyId", pkId);
        out.toJson(params);
        return null;
    }

    /**
     * 删除回复的方法
     * 
     * @param in
     * @param out
     * @param loginUser
     * @return
     * @throws Exception
     */
    public ModelAndView delete(WebInput in, WebOutput out, LoginUser loginUser) throws Exception {
        return ControllerHelper.ajaxController(in, out, loginUser, new AjaxControllerInternal<LoginUser>() {
            public Integer doInternal(WebInput in, WebOutput out, Map<String, Object> stringObjectMap, LoginUser loginUser) throws Exception {
                Long replyId = in.getLong("replyId", Long.valueOf(-1));
                boolean status = replyService.delete(replyId + "");

                return status ? 1 : 0;
            }
        });
    }

    /**
     * 列出一个资源的前三条回复
     * 
     * @param in
     * @param out
     * @param loginUser
     * @return
     * @throws Exception
     */
    public ModelAndView list(WebInput in, WebOutput out, LoginUser loginUser) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        Long resourceId = in.getLong("resourceId", Long.valueOf(-1));
        Integer resourceType = in.getInt("resourceType", Integer.valueOf(-1));

        PaginationSupport ps = this.replyService.list(resourceId, resourceType, 0, 5);
        params.put("ps", ps);
        params.put("resourceId", resourceId);
        return new ModelAndView("reply.list", params);
    }
}
