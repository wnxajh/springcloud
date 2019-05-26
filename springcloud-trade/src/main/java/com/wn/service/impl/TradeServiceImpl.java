package com.wn.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wn.dao.ITradeDao;
import com.wn.model.Trade;
import com.wn.service.ITradeService;

/** 
* @author 作者 wunan: 
* @version 创建时间：2019年5月26日 下午4:03:51 
*/
@Service
public class TradeServiceImpl implements ITradeService{

	@Autowired
	private ITradeDao tradeDao;
	
	@Override
	public Trade get(Integer id) {
		return tradeDao.getById(id);
		
	}
	@Override
	public Trade get(String tradeNo) {
		return tradeDao.getByTradeNo(tradeNo);
	}

}
