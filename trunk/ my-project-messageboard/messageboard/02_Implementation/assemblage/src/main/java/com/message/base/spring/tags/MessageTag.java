package com.message.base.spring.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.message.base.utils.HtmlUtils;
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
			/**
			 * 第一个'{'的位置
			 */
			int firstLeftBrace = printString.indexOf("{");
			/**
			 * 最后一个'}'的位置
			 */
			int lastRightBrace = printString.lastIndexOf("}");
			/**
			 * 第一个'{'后面的那个数字
			 */
			String first = printString.substring(firstLeftBrace + 1, firstLeftBrace + 2);
			/**
			 * 最后一个'}'后面的那个数字
			 */
			String last = printString.substring(lastRightBrace - 1, lastRightBrace);
			try {
				/**
				 * 第一个序号和最后一个序号
				 */
				int firstSequence = Integer.parseInt(first);
				int lastSequence = Integer.parseInt(last);
				
				/**
				 * {0}{1}...的个数与给定的值个数不一致，或者{0}{1}...不是按照这样递增的，那么返回错误,
				 * 否则进行替换
				 */
				if(!((lastSequence - firstSequence + 1) < 0 || (lastSequence - firstSequence + 1) != argsTemp.length)){
					/**
					 * 替换规则：
					 * 给定值的第一个值替换{0}，以此类推
					 */
					for(int j = 0; j < argsTemp.length; j++){
						printString = printString.replace("{" + j + "}", (String) argsTemp[j]);
					}
				}
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			}
			
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
