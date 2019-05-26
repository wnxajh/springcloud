
package com.wn.util;

import com.wn.util.excel.ExcelField;

/** 
* @author 作者 wunan: 
* @version 创建时间：2019年2月26日 下午5:46:19 
*/

public class UserGiftConfig {

	private Integer id;
	
	@ExcelField(title="配置名称")
	private String name;
	
	@ExcelField(title="有效时间")
	private String time;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
