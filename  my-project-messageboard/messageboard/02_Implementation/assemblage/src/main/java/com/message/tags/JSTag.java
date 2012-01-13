package com.message.tags;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.message.utils.string.StringUtils;

/**
 * JS的自定义标签，为了在引入JS时避免重复加上contextPath
 * @author sunhao(sunhao.java@gmail.com)
 */
public class JSTag extends TagSupport {
	private static final long serialVersionUID = -4399738019338181044L;

	private String charset;
	private String src;
	private String language;

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public void setSrc(String src) {
		this.src = src;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}

	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		StringBuffer content = new StringBuffer();
		content.append("<script type=\"text/javascript\" ");
		if(StringUtils.isNotEmpty(charset)){
			content.append("charset=\"").append(charset).append("\" ");
		}
		if(StringUtils.isNotEmpty(language)){
			content.append("language=\"").append(language).append("\" ");
		}
		String url = request.getContextPath() + "/" + src;
        content.append(" src=\"").append(url).append("\"></script>");
        
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
		src = null;
		language = null;
	}

}
