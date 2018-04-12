package com.wn.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wn.model.InterfaceLimit;
import com.wn.service.InterfaceLimitService;

@RestController
public class TestApi {

	@Autowired
	private InterfaceLimitService interfaceLimitService;
	
	@RequestMapping(value="/test/getData")
	public List<InterfaceLimit> getData(){
		List<InterfaceLimit> interfaceLimits = interfaceLimitService.getList();
		return interfaceLimits;
	}
	
	@RequestMapping(value="/test/getAllData")
	public List<InterfaceLimit> getAllData(){
		List<InterfaceLimit> interfaceLimits = interfaceLimitService.getAllList();
		return interfaceLimits;
	}
}
