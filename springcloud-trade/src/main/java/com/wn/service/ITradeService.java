package com.wn.service;
import com.wn.model.Trade;

/** 
* @author 作者 wunan: 
* @version 创建时间：2019年5月26日 下午4:02:30 
*/
public interface ITradeService {

	 public Trade get(Integer id);
	 
	 public Trade get(String tradeNo);
}
