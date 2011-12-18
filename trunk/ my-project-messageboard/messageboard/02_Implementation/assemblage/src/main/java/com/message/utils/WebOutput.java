package com.message.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

@SuppressWarnings({"unchecked","unused"})
public class WebOutput {
	private HttpServletResponse response;
	private HttpServletRequest request;
	private static Map _defaultMimeTypes;
	
	static{
		_defaultMimeTypes.put(".avi", "video/x-msvideo");
		_defaultMimeTypes.put(".bmp", "image/bmp");
		_defaultMimeTypes.put(".class", "application/octet-stream");
		_defaultMimeTypes.put(".css", "text/css");
		_defaultMimeTypes.put(".doc", "application/msword");
		_defaultMimeTypes.put(".exe", "application/octet-stream");
		_defaultMimeTypes.put(".gif", "image/gif");
		_defaultMimeTypes.put(".html", "text/html");
		_defaultMimeTypes.put(".htm", "text/html");
		_defaultMimeTypes.put(".js", "application/x-javascript");
		_defaultMimeTypes.put(".jpeg", "image/jpeg");
		_defaultMimeTypes.put(".jpg", "image/jpeg");
		_defaultMimeTypes.put(".movie", "video/x-sgi-movie");
		_defaultMimeTypes.put(".mp3", "audio/mpeg");
		_defaultMimeTypes.put(".pdf", "application/pdf");
		_defaultMimeTypes.put(".ps", "application/postscript");
		_defaultMimeTypes.put(".ppt", "application/vnd.ms-powerpoint");
		_defaultMimeTypes.put(".png", "image/png");
		_defaultMimeTypes.put(".swf", "application/x-shockwave-flash");
		_defaultMimeTypes.put(".txt", "text/plain");
		_defaultMimeTypes.put(".wav", "audio/x-wav");
		_defaultMimeTypes.put(".xml", "text/xml");
		_defaultMimeTypes.put(".zip", "application/zip");
	}
	
	public WebOutput(HttpServletRequest request, HttpServletResponse response){
		this.request = request;
		this.response = response;
	}
	
	public HttpServletResponse getResponse(){
		return this.response;
	}
	
	public void setCookie(String name, String value, int maxAge){
		Cookie c = new Cookie(name, value);
		if(maxAge > 0){
			c.setMaxAge(maxAge);
		}
		c.setPath("/");
		this.response.addCookie(c);
	}
	
	public void setContentType(String contentType){
		this.response.setContentType(contentType);
	}
	
	public void setContentType(String contextType, String charset){
		if(charset == null){
			this.response.setContentType(contextType);
		} else {
			this.response.setContentType(contextType + "; charset=" + charset);
		}
	}
	
	public void print(String content) throws IOException{
		Writer out = this.response.getWriter();
		out.write(content);
	}
	
	public void copy(InputStream in) throws IOException{
		IOUtils.copy(in, this.response.getOutputStream());
	}
	
	public void copy(byte[] data) throws IOException{
		IOUtils.write(data, this.response.getOutputStream());
	}
	
	public void redirect(String url) throws IOException{
		this.response.sendRedirect(url);
	}
	
	public void toJson(Object obj) throws IOException{
		this.response.setHeader("Pragma", "No-cache");
		this.response.setHeader("Cache-Control", "no-cache");
		this.response.setDateHeader("Expires", 0L);
		
		this.print(JsonUtils.toString(obj));
	}
	
	public void toJson(String rootName, Object obj) throws IOException {
		setContentType("application/x-javascript", "UTF-8");
		print(JsonUtils.toString(rootName, obj));
	}
	
	public void flush() throws IOException{
		this.response.flushBuffer();
	}
}
