package com.wn.service;

import java.util.List;

import com.wn.model.InterfaceLimit;



public interface InterfaceLimitService {

    InterfaceLimit getEntityByPri(Integer id);
	
	List<InterfaceLimit> getList();
	
	List<InterfaceLimit> getAllList();
}
