package com.wn.dao;
import com.wn.model.Trade;

/** 
* @author 作者 wunan: 
* @version 创建时间：2019年5月26日 下午3:59:39 
*/
public interface ITradeDao {

	public Trade getById(Integer id);
	
	public Trade getByTradeNo(String tradeNo);
	
}
