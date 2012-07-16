package com.message.main.home.web;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;

import com.message.base.properties.MessageUtils;
import com.message.base.spring.SimpleController;
import com.message.base.utils.DateUtils;
import com.message.base.utils.StringUtils;
import com.message.base.web.WebInput;
import com.message.base.web.WebOutput;

/**
 * 资源文件的controller.
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-7-16 下午08:37:25
 */
public class ResourceController extends SimpleController {
	private static final String LOCALE_ZH_CN = "zh_cn";
	private static final String LOCALE_ZH = "zh";
	private static final String LOCALE_CN = "cn";
	private static final String LOCALE_EN_US = "en_us";
	private static final String LOCALE_EN = "en";
	
	/**
	 * 获取资源文件中的值
	 * 
	 * @param in
	 * @param out
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(WebInput in, WebOutput out) throws Exception{
		Locale locale = null;
		String localeStr = in.getString("locale", StringUtils.EMPTY);
		
		if(StringUtils.isNotEmpty(localeStr)){
			if(StringUtils.equalsIgnoreCase(LOCALE_ZH_CN, localeStr) || StringUtils.equalsIgnoreCase(LOCALE_ZH, localeStr)
					|| StringUtils.equalsIgnoreCase(LOCALE_CN, localeStr)){
				locale = Locale.CHINA;
			} else if(StringUtils.equalsIgnoreCase(LOCALE_EN_US, localeStr) || StringUtils.equalsIgnoreCase(LOCALE_EN, localeStr)){
				locale = Locale.US;
			} else {
				locale = new Locale(localeStr);
			}
		} else {
			locale = Locale.CHINA;
		}
		
		Map<String, String> i18nMap = MessageUtils.getMessages();
		JSONObject json = new JSONObject();
		json.putAll(i18nMap);
		json.put("message", "Message Resource.");
		json.put("locale", locale.toString());
		
		StringBuffer sb = new StringBuffer(8192);
		sb.append("YAHOO.namespace(\"messages\");").append("YAHOO.messages = ")
				.append(json.toString());
		
		HttpServletResponse response = out.getResponse();
		response.setContentType("text/javascript;charset=UTF-8");
		response.setHeader("Cache-Control", "private,max-age=" + 60 * 60 * 24 * 30);
        response.setDateHeader("Expires", System.currentTimeMillis() + 60 * 60 * 24 * 30 * DateUtils.MILLIS_PER_SECOND);
        response.setDateHeader("Date", System.currentTimeMillis());
        response.getWriter().write(sb.toString());
		return null;
	}
}
