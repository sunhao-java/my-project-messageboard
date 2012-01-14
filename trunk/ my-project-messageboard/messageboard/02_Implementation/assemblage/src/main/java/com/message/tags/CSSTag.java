package com.message.tags;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.message.utils.StringUtils;


/**
 * css的自定义标签，为了在引入css时避免重复加上contextPath
 * @author sunhao(sunhao.java@gmail.com)
 */
public class CSSTag extends TagSupport{
	private static final long serialVersionUID = 2967223963177024321L;
	
	private String charset;
	private String href;

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public void setHref(String href) {
		this.href = href;
	}
	
	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		StringBuffer content = new StringBuffer();
		content.append("<link rel=\"stylesheet\" type=\"text/css\" ");
		if(StringUtils.isNotEmpty(charset)){
			content.append("charset=\"").append(charset).append("\" ");
		}
		String url = request.getContextPath() + "/" + href;
		content.append("href=\"").append(url).append("\"/>");
		
		print(content);
		
		return EVAL_PAGE;
	}

	private void print(StringBuffer content) {
		try {
			pageContext.getOut().print(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void release() {
		charset = null;
		href = null;
	}

}
