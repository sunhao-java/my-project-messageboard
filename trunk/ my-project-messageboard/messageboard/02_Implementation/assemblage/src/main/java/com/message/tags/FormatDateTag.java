package com.message.tags;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import com.message.utils.MessageUtils;
import com.message.utils.resource.ResourceType;

/**
 * 自定义的格式化时间标签
 * @author sunhao(sunhao.java@gmail.com)
 */
public class FormatDateTag extends org.apache.taglibs.standard.tag.rt.fmt.FormatDateTag {
	private static final long serialVersionUID = 5556661395114907904L;
	
	/**
	 * 日期显示的格式<br/>
	 * 默认显示yyyy年MM月dd日 HH时mm分
	 * <ul>
	 * 	<li>1 显示为yyyy-MM-dd HH:mm</li>
	 * 	<li>0 显示为yyyy年MM月dd日 HH时mm分</li>
	 * </ul>
	 */
	private Integer dateType = 0;

	public void setDateType(Integer dateType) {
		this.dateType = dateType;
	}

	@SuppressWarnings("deprecation")
	public int doEndTag() throws JspException {
		String out = "";
		Date nowDate = new Date();
		if(value == null){
			return EVAL_PAGE;
		}
		SimpleDateFormat format = new SimpleDateFormat();
		
		long mm = Math.abs(nowDate.getTime() - value.getTime());
		
		//显示完整时间
		if(mm > 2*24*60*60*1000){
			if(dateType == 1){
				format.applyPattern(ResourceType.SIMPLE_DATE_FORMAT);
			} else {
				format.applyPattern(MessageUtils.getMessage("dateformat.chinese.format"));
			}
			
			out = format.format(value);
			print(out);
			return EVAL_PAGE;
		}
		
		//显示昨天XX时XX分
		if(mm >= 1*24*60*60*1000){
			int hours = value.getHours();
			int minutes = value.getMinutes();
			String time = "";
			if(hours < 6){
				time = MessageUtils.getMessage("dateformat.morning");
			} else if(hours < 12) {
				time = MessageUtils.getMessage("dateformat.am");
			} else if(hours < 18){
				time = MessageUtils.getMessage("dateformat.pm");
				hours -= 12;
			} else if(hours < 24){
				time = MessageUtils.getMessage("dateformat.night");
				hours -= 12;
			}
			out = MessageUtils.getMessage("dateformat.date.format",new Object[]{time, hours, minutes});
			print(out);
			return EVAL_PAGE;
		}
		
		//显示多少小时之前
		if(mm > 1*60*60*1000){
			String hours = String.valueOf((int) mm/(1000*60*60));
			out = hours + MessageUtils.getMessage("dateformat.beforeH");
			print(out);
			return EVAL_PAGE;
		}
		//显示多少分钟之前
		if(mm > 1*60*1000){
			String minutes = String.valueOf((int) mm/(1000*60));
			out = minutes + MessageUtils.getMessage("dateformat.beforeM");
			print(out);
			return EVAL_PAGE;
		}
		//显示多少秒之前
		if(mm > 1000){
			String seconds = String.valueOf((int) mm/(1000));
			out = seconds + MessageUtils.getMessage("dateformat.beforeS");
			print(out);
			return EVAL_PAGE;
		}
		//显示1秒前
		if(mm <= 1000){
			out = "1" + MessageUtils.getMessage("dateformat.beforeS");
			print(out);
			return EVAL_PAGE;
		}
		
		return EVAL_PAGE;
	}
	
	private void print(String out) throws JspTagException{
		try {
			pageContext.getOut().print(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
