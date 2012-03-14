package com.message.base.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.message.base.utils.HtmlUtils;
import com.message.base.utils.ReplaceStringUtils;
import com.message.base.utils.StringUtils;
import com.message.base.utils.SystemConfig;

/**
 * 取得系统配置文件中的配置<br/><br/>
 * 用法：<code>&lt;msg:message code="mail.confirm.content" ignoreHtml="false" args="2012-01-12,sunhao,3"/&gt;</code><br/>
 * <code>code</code> 必填<br/>
 * <code>defaultValue</code> 选填<br/>
 * <code>ignoreHtml</code> 选填<br/>
 * <code>args</code> 选填
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-3-11 下午10:55:50
 */
public class MessageTag extends TagSupport {
	private static final long serialVersionUID = 1717739528655898485L;
	private static final Logger logger = LoggerFactory.getLogger(MessageTag.class);

	/**
	 * properties文件中的key
	 */
	private String code;
	/**
	 * 如果key找不到值，返回的默认值
	 */
	private String defaultValue;
	/**
	 * 是否去除html标签
	 */
	private boolean ignoreHtml;
	/**
	 * 为类似{0}{1}设值，以,号隔开
	 */
	private String args;
	
	public int doEndTag() throws JspException {
		if(StringUtils.isEmpty(code)){
			logger.error("the code is null,it is required!");
			return EVAL_PAGE;
		}
		
		Object[] argsTemp = null;
		if(StringUtils.isNotEmpty(args)){
			argsTemp = args.split(",");
		}
		
		String printString = StringUtils.EMPTY;

		if(StringUtils.isNotEmpty(defaultValue)){
			printString = SystemConfig.getProperty(code, defaultValue);
		} else {
			printString = SystemConfig.getProperty(code);
		}
		
		if(this.ignoreHtml){
			printString = HtmlUtils.subHtmlCode(printString.length(), printString, "");
		}
		
		if(argsTemp != null && argsTemp.length > 0){
			printString = ReplaceStringUtils.replace(printString, argsTemp, printString);
		}
		
		this.print(printString);
		
		return super.doEndTag();
	}
	
	private void print(String printString){
		try {
			pageContext.getOut().print(printString);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
	}
	
	public void release() {
		this.setCode(StringUtils.EMPTY);
		this.setDefaultValue(StringUtils.EMPTY);
		this.setIgnoreHtml(Boolean.FALSE);
		this.setArgs(StringUtils.EMPTY);
		super.release();
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public void setIgnoreHtml(boolean ignoreHtml) {
		this.ignoreHtml = ignoreHtml;
	}

	public void setArgs(String args) {
		this.args = args;
	}

}
