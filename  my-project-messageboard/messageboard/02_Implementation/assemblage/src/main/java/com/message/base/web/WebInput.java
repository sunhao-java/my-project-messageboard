package com.message.base.web;

import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.message.utils.GenericDateFormat;
import com.message.utils.StringUtils;

/**
 * 封装HttpServletRequest
 * @author sunhao(sunhao.java@gmail.com)
 */
public class WebInput {
	private static final Log log = LogFactory.getLog(WebInput.class);
	public static final String TIME_PATTERN = "yyyy-MM-dd HH:mm";
	private HttpServletRequest request;
	
	public WebInput(HttpServletRequest request){
		this.request = request;
	}

	public HttpServletRequest getRequest() {
		return request;
	}
	
	public String getString(String name, String defaultValue) {
		String result = StringUtils.trimToNull(this.request.getParameter(name));
		if(result == null) {
			return defaultValue;
		}
		
		return result;
	}
	
	public String getString(String name){
		return StringUtils.trimToNull(this.request.getParameter(name));
	}
	
	public int getInt(String name, int defaultValue) {
		int result = 0;
		try {
			result = this.request.getParameter(name) == null ? defaultValue
					: Integer.parseInt(this.request.getParameter(name));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Integer getInt(String name, Integer defaultValue){
		Integer result = defaultValue;
		try {
			result = Integer.valueOf(this.request.getParameter(name));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Integer getInt(String name){
		try {
			if(this.request.getParameter(name) == null){
				return 0;
			} else {
				return Integer.parseInt(this.request.getParameter(name));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public long getLong(String name, long defaultValue){
		long result = defaultValue;
		try {
			result = Long.parseLong(this.request.getParameter(name));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long getLong(String name, Long defaultValue){
		Long result = defaultValue;
		try {
			result = Long.valueOf(this.request.getParameter(name));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Long getLong(String name){
		try {
			return Long.valueOf(this.request.getParameter(name));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public double getDouble(String name, double defaultValue){
		double result = defaultValue;
		try {
			result = Double.parseDouble(this.request.getParameter(name));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public boolean getBoolean(String name, boolean defaultValue) {
		boolean result = defaultValue;
		String s = StringUtils.trimToNull(this.request.getParameter(name));
		if(s == null){
			return result;
		}
		s = s.toLowerCase();
		
		if("yes".equals(s) || "true".equals(s) || "1".equals(s) || "t".equals(s)) {
			result = true;
		} else if("no".equals(s) || "false".equals(s) || "0".equals(s) || "f".equals(s)) {
			result = false;
		}
		
		return result;
	}
	
	public Date getDate(String name, String pattern, Date defaultValue) {
		GenericDateFormat format = new GenericDateFormat(pattern);
		Date result = defaultValue;
		try {
			String value = this.request.getParameter(name);
			if(StringUtils.isNotBlank(value)) {
				result = format.parse(value);
			}
		} catch (ParseException e) {
			log.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	public Date getDate(String name) {
		GenericDateFormat format = new GenericDateFormat(TIME_PATTERN);
		String value = this.request.getParameter(name);
		try {
			return value == null ? null : format.parse(value);
		} catch (ParseException e) {
			log.error(e.getMessage(), e);
		}
		
		return null;
	}
	
	public String[] getStrings(String name){
		return this.request.getParameterValues(name);
	}
	
	public int[] getInts(String name, int defaultValue){
		String[] values = this.getStrings(name);
		if(values == null || values.length < 1){
			return null;
		} 
		int[] results = new int[values.length];
		for(int i = 0; i < values.length; i++) {
			results[i] = defaultValue;
			try {
				results[i] = Integer.parseInt(values[i]);
			} catch (NumberFormatException e) {
				log.error(e.getMessage(), e);
			}
		}
		
		return results;
	}
	
	public int[] getInts(String name){
		return this.getInts(name, 0);
	}
	
	public long[] getLongs(String name, long defaultValue){
		String[] values = this.getStrings(name);
		if(values == null || values.length < 1){
			return null;
		}
		long[] results = new long[values.length];
		for(int i = 0; i < values.length; i++) {
			results[i] = defaultValue;
			try {
				results[i] = Long.parseLong(values[i]);
			} catch (NumberFormatException e) {
				log.error(e.getMessage(), e);
			}
		}
		
		return results;
	}
	
	public long[] getLongs(String name){
		return this.getLongs(name, 0L);
	}
	
	public Long[] getLongObjects(String name, Long defaultValue){
		long[] values = this.getLongs(name, defaultValue);
		if(values == null || values.length < 1){
			return null;
		}
		Long[] results = new Long[values.length];
		for(int i = 0; i < values.length; i++){
			results[i] = new Long(values[i]);
		}
		
		return results;
	}
	
	public Long[] getLongObjects(String name){
		return this.getLongObjects(name, 0L);
	}
	
	public String getCookieValue(String name){
		Cookie[] cs = this.request.getCookies();
		
		if(cs == null || cs.length < 1){
			return null;
		}
		
		for(int i = 0; i < cs.length; i++){
			Cookie c = cs[i];
			String key = c.getName();
			String value = c.getValue();
			if(name.equals(key)){
				return value;
			}
		}
		
		return null;
	}
	
	public HttpSession getSession(){
		return this.request.getSession();
	}
	
	public String getClientIP(){
		return this.request.getRemoteAddr();
	}
	
	public void setAttribute(String name, Object value){
		request.setAttribute(name, value);
	}
	
	public Object getAttribute(String name){
		return this.request.getAttribute(name);
	}

}
