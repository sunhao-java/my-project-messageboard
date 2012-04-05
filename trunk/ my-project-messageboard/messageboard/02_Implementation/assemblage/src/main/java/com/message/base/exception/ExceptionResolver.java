package com.message.base.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.message.base.utils.RequestUtils;
import com.message.base.web.WebOutput;

/**
 * 异常统一处理类
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-2-15 下午10:12:52
 */
public class ExceptionResolver extends SimpleMappingExceptionResolver {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);

	private static final String AJAX_TYPE_JSON = "json";
	private static final String AJAX_TYPE_HTML = "html";
	private static final String AJAX_TYPE_JS = "js";
	private static final String AJAX_ERROR_HTML="An error occured when dealing with the ajax request,nested exception is : :errorMessage";
    private static final String AJAX_ERROR_JS="javascript:alert(\":errorMessage \")";
    
	private ViewResolver viewResolver;
	
	@SuppressWarnings("rawtypes")
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		if (RequestUtils.isAjaxRequest(request)) {
			Enumeration ajaxParams = request.getParameterNames();
			String ajaxParam = null;

			while (ajaxParams != null && ajaxParams.hasMoreElements()) {
				Object obj = ajaxParams.nextElement();
				if (obj != null
						&& obj instanceof java.lang.String
						&& StringUtils.equalsIgnoreCase("ajaxtype",
								(String) obj)) {
					ajaxParam = (String) obj;
					break;
				}
			}
			String ajaxType = ajaxParam == null ? AJAX_TYPE_JSON : request
					.getParameter(ajaxParam);
			ajaxType = ajaxType == null ? AJAX_TYPE_JSON : ajaxType;
			if (ajaxType != null) {
				return resolveAjaxException(ajaxType, request, response, ex);
			}
		}

		return super.resolveException(request, response, handler, ex);
	}
	
	private ModelAndView resolveAjaxException(String ajaxType,HttpServletRequest request,HttpServletResponse response,Exception ex){
		String exceptionMessage = StringUtils.trimToEmpty(ex.getMessage());
		if(StringUtils.equalsIgnoreCase(AJAX_TYPE_JSON, ajaxType)){
			JSONObject params = new JSONObject();
			params.put("status", 0);
			params.put("msg", exceptionMessage);
			WebOutput out = new WebOutput(request, response);
			try {
				out.toJson(params);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(StringUtils.equalsIgnoreCase(AJAX_TYPE_HTML, ajaxType)){
			printHTML(response, AJAX_ERROR_HTML);
		} else if(StringUtils.equals(AJAX_TYPE_JS, ajaxType)){
			printHTML(response, AJAX_ERROR_JS);
		}
		return null;
	}
	
	private void printHTML(HttpServletResponse response, String html){
		PrintWriter printWriter = null;
		try {
			printWriter = response.getWriter();
			printWriter.print(html);
			printWriter.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * 发生异常之后跳转到页面
	 * 
	 * @param viewName 	视图名
	 * @param ex		异常类
	 */
	protected ModelAndView getModelAndView(String viewName, Exception ex,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView(viewName);
		String errorMsg = ex.getMessage();
		//如果异常中有错误信息的话，则往页面打出错误信息
		//否则打出异常的类名
		if(StringUtils.isNotEmpty(errorMsg)){
			mv.addObject(DEFAULT_EXCEPTION_ATTRIBUTE, errorMsg);
		} else {
			mv.addObject(DEFAULT_EXCEPTION_ATTRIBUTE, ex.getClass().getName());
		}
        mv.addObject("ex", ex);
		
		ViewResolver vr = this.viewResolver;

		View v = null;
		try {
			v = vr.resolveViewName(mv.getViewName(), Locale.getDefault());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}

		if (v != null) {
			mv.setView(v);
		}

		return mv;
	}

	public ViewResolver getViewResolver() {
		return viewResolver;
	}

	public void setViewResolver(ViewResolver viewResolver) {
		this.viewResolver = viewResolver;
	}

}
