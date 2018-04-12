package com.wn.api;


import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wn.util.AjaxResult;

@RestController
public class AuthTokenApi {
	
	private final Logger logger = Logger.getLogger(getClass());
	
	@RequestMapping(value="/auth/praseToken")
	public AjaxResult praseToken(@RequestParam(required=false) String token){
		AjaxResult ajaxResult = new AjaxResult();
		logger.info(String.format("token=[%s]", token));
		ajaxResult.setCode(200);
		ajaxResult.setMsg("success");
		return ajaxResult;
	}

}
