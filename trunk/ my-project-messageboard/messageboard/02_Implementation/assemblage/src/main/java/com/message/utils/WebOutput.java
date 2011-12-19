package com.message.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

@SuppressWarnings({"unchecked","unused"})
public class WebOutput implements HttpServletResponse {
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

	@Override
	public void addCookie(Cookie arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addDateHeader(String arg0, long arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addHeader(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addIntHeader(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean containsHeader(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String encodeRedirectURL(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String encodeRedirectUrl(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String encodeURL(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String encodeUrl(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendError(int arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendError(int arg0, String arg1) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendRedirect(String arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDateHeader(String arg0, long arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHeader(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setIntHeader(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStatus(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStatus(int arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void flushBuffer() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getBufferSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCharacterEncoding() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCommitted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetBuffer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBufferSize(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCharacterEncoding(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setContentLength(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLocale(Locale arg0) {
		// TODO Auto-generated method stub
		
	}
}
