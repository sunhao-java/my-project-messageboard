package com.message.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.message.base.utils.StringUtils;
import com.message.resource.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.message.base.spring.ApplicationHelper;
import com.message.main.user.pojo.User;
import com.message.main.user.service.UserService;

/**
 * 解决显示用户本地硬盘的头像问题
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-3-16 下午11:12:51
 */
public class HeadTag extends TagSupport {
	private static final long serialVersionUID = 6744047241720109064L;
	private static final Logger logger = LoggerFactory.getLogger(HeadTag.class);
	
	/**
	 * UserService在spring中bean的id
	 */
	private static final String BEAN_USERSERVICE = "userService";
	
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 显示头像的类型
	 * 0普通头像1大头像2小头像
	 */
	private Integer headType;
	
	public HeadTag(){
		this.setUserId(Long.valueOf(-1));
		this.setHeadType(Integer.valueOf(0));
	}

	public int doEndTag() throws JspException {
		if(Integer.valueOf(-1).equals(userId) || headType == null){
			logger.error("given userId or headType is null!");
			return EVAL_PAGE;
		}
		
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		String contextPath = request.getContextPath();
		
		UserService userService = (UserService) ApplicationHelper.getInstance().getBean(BEAN_USERSERVICE);
		User user = null;
		try {
			user = userService.getUserById(userId);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

        StringBuffer content = new StringBuffer();
        
        if(user == null || StringUtils.isEmpty(user.getHeadImage())){
            logger.debug("this user'{}' has not set his head image, use the default image!", user);
            String size = StringUtils.EMPTY;
            if(Integer.valueOf(1).equals(headType)){
                size = ResourceType.IMAGE_SIZE_BIG;
            } else if(Integer.valueOf(2).equals(headType)){
                size = ResourceType.IMAGE_SIZE_SMALL;
            } else {
                size = ResourceType.IMAGE_SIZE_NORMAL;
            }
            
            content.append("<img src=\"").append(contextPath).append("/image/head/").append(size).append("/blank.jpg")
                    .append("\" title=\"").append(user.getTruename() == null ? "" : user.getTruename()).append("\"/>");
        } else {
            content.append("<img src=\"").append(contextPath).append("/head.jpg?userId=")
				.append(userId).append("&headType=").append(headType).append("\" title=\"")
				.append(user.getTruename() == null ? "" : user.getTruename()).append("\"/>");
        }

		try {
			printImage(content.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("print html img tag error!");
		}
		
		return EVAL_PAGE;
	}
	
	/**
	 * 往页面写出调用servlet的代码
	 *
     * @param content
	 * @throws Exception
	 */
	private void printImage(String content) throws Exception{
		pageContext.getOut().print(content);
	}

	public void release() {
		this.setUserId(Long.valueOf(-1));
		this.setHeadType(Integer.valueOf(1));
		super.release();
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setHeadType(Integer headType) {
		this.headType = headType;
	}
	
}
