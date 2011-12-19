package com.message.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WebInput implements HttpServletRequest {
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
		int result = defaultValue;
		try {
			result = Integer.parseInt(this.request.getParameter(name));
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
			return Integer.valueOf(this.request.getParameter(name));
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
	
	public String getClientIP(){
		return this.request.getRemoteAddr();
	}
	
	public void setAttribute(String name, Object value){
		request.setAttribute(name, value);
	}
	
	public Object getAttribute(String name){
		return this.request.getAttribute(name);
	}

	@Override
	public String getAuthType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getContextPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cookie[] getCookies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getDateHeader(String arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getHeader(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration getHeaderNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration getHeaders(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getIntHeader(String arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getMethod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPathInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPathTranslated() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQueryString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRemoteUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRequestURI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringBuffer getRequestURL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRequestedSessionId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServletPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpSession getSession() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpSession getSession(boolean arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Principal getUserPrincipal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isRequestedSessionIdFromCookie() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromURL() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromUrl() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRequestedSessionIdValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isUserInRole(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Enumeration getAttributeNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCharacterEncoding() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getContentLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLocalAddr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLocalName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLocalPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration getLocales() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getParameter(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map getParameterMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration getParameterNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getParameterValues(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProtocol() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRealPath(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRemoteAddr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRemoteHost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRemotePort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getScheme() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServerName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getServerPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isSecure() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeAttribute(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCharacterEncoding(String arg0)
			throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		
	}
}
