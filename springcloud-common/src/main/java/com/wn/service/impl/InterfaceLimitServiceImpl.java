package com.wn.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wn.common.ReadOnlyConnection;
import com.wn.dao.InterfaceLimitMapper;
import com.wn.model.InterfaceLimit;
import com.wn.model.InterfaceLimitExample;
import com.wn.service.InterfaceLimitService;

@Service
public class InterfaceLimitServiceImpl implements InterfaceLimitService {
	
	@Autowired
	private InterfaceLimitMapper interfaceLimitMapper;

	@Override
	public InterfaceLimit getEntityByPri(Integer id) {
		return interfaceLimitMapper.selectByPrimaryKey(id);
	}

	@ReadOnlyConnection
	@Override
	public List<InterfaceLimit> getList() {
		InterfaceLimitExample example = new InterfaceLimitExample();
		return interfaceLimitMapper.selectByExample(example);
	}
	
	@Override
	public List<InterfaceLimit> getAllList() {
		InterfaceLimitExample example = new InterfaceLimitExample();
		return interfaceLimitMapper.selectByExample(example);
	}

}
