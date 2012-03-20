package com.message.base.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import com.message.base.utils.HtmlUtils;
import com.message.base.utils.StringUtils;

/**
 * 切割字符串的自定义标签
 *
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-1-17 上午12:10
 */
public class CutTextTag extends TagSupport{
	private static final long serialVersionUID = -2921502360042121850L;
	
	//需要切割的字符串
	private String text;
	//切割的长度
	private int length;
	//多余字符用endText代替
	private String endText;
	//是否去除html标签，默认不去除
	private boolean escapeHtml = Boolean.FALSE;
	//如果为空默认显示
	private String defaultStr;
	
	public int doEndTag() throws JspException {
		String out = StringUtils.EMPTY;
		
		if(StringUtils.isEmpty(text)){
			out = StringUtils.isEmpty(defaultStr) ? StringUtils.EMPTY : defaultStr;
		} else {
			if(escapeHtml){
				out = HtmlUtils.subHtmlCode(0, text, "...");
			}
		}
		
		print(out);
		return EVAL_PAGE;
	}
	
	private void print(String out) throws JspTagException{
		String printOut = StringUtils.EMPTY;
		if(out.length() <= length) {
			printOut = out;
		} else {
			printOut = out.substring(0, length) + endText;
		}
		
		try {
			this.pageContext.getOut().write(printOut);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getEndText() {
		return endText;
	}

	public void setEndText(String endText) {
		this.endText = endText;
	}

	public boolean isEscapeHtml() {
		return escapeHtml;
	}

	public void setEscapeHtml(boolean escapeHtml) {
		this.escapeHtml = escapeHtml;
	}

	public String getDefault() {
		return defaultStr;
	}

	public void setDefault(String defaultStr) {
		this.defaultStr = defaultStr;
	}

}