package com.wn.filter;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.wn.common.AjaxResult;


public class UserLoginFilter extends ZuulFilter{
	private final String serverId = "UCENTER";
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
	    HttpServletRequest request = ctx.getRequest();
	    System.out.println("----------------");
	    String token = request.getHeader("token");
		if(StringUtils.isBlank(token)){
			ctx.setResponseStatusCode(405);
		}else{
			AjaxResult  ajaxResult = restTemplate.getForObject("http://"+serverId+"/auth/praseToken",
					AjaxResult.class);
//			AjaxResult  ajaxResult = apiAuthService.praseToken(token);
			if(ajaxResult.getCode() == 200){
				logger.info("login success!");
			}else{
				return ajaxResult;
			}
		}
		return null;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public String filterType() {
		return "pre";
	}

}
