
package com.wn.model;

import java.util.Date;

import lombok.Data;

/** 
 * 订单实体类
* @author 作者 wunan: 
* @version 创建时间：2019年5月26日 下午3:51:07 
*/
@SuppressWarnings("unused")
@Data
public class Trade {
	 private Integer id;
	 private String tradeNo;
	 private Date time;
}
