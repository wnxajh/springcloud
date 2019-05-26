package com.wn.filter;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.springframework.web.util.HtmlUtils;

import com.wn.constant.CommonConstant;

/**
 * 继承servletRequest的具体实现类
 * 装饰者模式
 * @author wun
 *
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
	
	HttpServletRequest xssRequest = null;    
	
	public XssHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		xssRequest = request;  
	}
	
	@Override
	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);
		if (values == null) {
			return null;
		}
		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			encodedValues[i] = HtmlUtils.htmlEscape(values[i],CommonConstant.ENCODING_UTF8);
		}
		return encodedValues;
	}
	
	@Override
	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);
		if (value == null) {
			return null;
		}
		return HtmlUtils.htmlEscape(value,CommonConstant.ENCODING_UTF8);
	}

	@Override
	public String getHeader(String name) {
		String value = super.getHeader(name);
		if (value == null)
			return null;
		return HtmlUtils.htmlEscape(value,CommonConstant.ENCODING_UTF8);
	}
	
}
