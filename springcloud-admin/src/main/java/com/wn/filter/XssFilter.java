package com.wn.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;


//@Component
public class XssFilter implements Filter{


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("----------------");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		  XssHttpServletRequestWrapper xssRequest =
			        new XssHttpServletRequestWrapper((HttpServletRequest) request);
		  
		  System.out.println("name==");
			    chain.doFilter(xssRequest, response);
	}

	@Override
	public void destroy() {
		
	}

}
