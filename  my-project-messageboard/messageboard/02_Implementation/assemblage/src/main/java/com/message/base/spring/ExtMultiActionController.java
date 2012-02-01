package com.message.base.spring;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.message.utils.resource.ResourceType;

public class ExtMultiActionController extends MultiActionController {
	/**
	 * 提交form时，表单中的beginTime和endTime是string类型的，而message这个bean中的两个字段是date类型的<br/>
	 * spring进行字段绑定的时候会报错，无法绑定这两个字段<br/>
	 * 解决办法暂时是重写父类此方法，注册一个customerEditor，用来进行日期类型转换
	 * 
	 * 已解决：所以controller继承此类，已达到只需重写一次即可
	 * 
	 * @param request
	 * @param binder
	 */
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(ResourceType.SIMPLE_DATE_FORMAT);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
