package com.message.base.exception;

import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.message.base.utils.RequestUtils;

/**
 * 异常统一处理类
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-2-15 下午10:12:52
 */
public class ExceptionResolver extends SimpleMappingExceptionResolver {

	private static final String AJAX_TYPE_JSON = "json";
	private ViewResolver viewResolver;
	
	//TODO 要看
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

			}
		}

		return super.resolveException(request, response, handler, ex);
	}

	protected ModelAndView getModelAndView(String viewName, Exception ex,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView(viewName);
		mv.addObject(DEFAULT_EXCEPTION_ATTRIBUTE, ex.getClass().getName());

		ViewResolver vr = this.viewResolver;

		View v = null;
		try {
			v = vr.resolveViewName(mv.getViewName(), Locale.getDefault());
		} catch (Exception e) {

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
