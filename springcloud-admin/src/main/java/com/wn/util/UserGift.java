package com.wn.util;

import java.util.List;

import com.wn.util.excel.ExcelField;
import com.wn.util.excel.ExcelMergeColum;

/**
 * 用户拥有的礼物
 * 
 * @author wnxaj
 *
 */
public class UserGift {
	private Integer id;
	private Integer userId;
	
	@ExcelField(title="礼物名称")
	private String giftName;
	
	@ExcelField(title="礼物数量")
	private Integer num;
	
	@ExcelField(title="产地")
	private String controry;
	
	@ExcelMergeColum
	@ExcelField(title="省")
	private String province;
	
	@ExcelField(title="市")
	private String city;
	
	@ExcelField(isList = true)
	private List<UserGiftConfig> configList;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getControry() {
		return controry;
	}

	public void setControry(String controry) {
		this.controry = controry;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public List<UserGiftConfig> getConfigList() {
		return configList;
	}

	public void setConfigList(List<UserGiftConfig> configList) {
		this.configList = configList;
	}
}
