package com.wn.util;

import java.util.List;

import com.wn.util.enumeration.UserLevelEnum;
import com.wn.util.excel.ExcelEnum;
import com.wn.util.excel.ExcelField;
import com.wn.util.excel.ExcelMergeColum;

/**
 * 
 * @author wnxaj
 *
 */
public class User {

	private Integer id;
	
	@ExcelMergeColum
	@ExcelField(title="用户名")
	private String userName;
	
	@ExcelMergeColum
	@ExcelField(title="手机号")
	private String mobile;
	
	@ExcelField(isList = true)
	private List<UserGift> giftList;
	
	@ExcelMergeColum
	@ExcelField(title="用户类型")
	@ExcelEnum(enumClass=UserLevelEnum.class,methodName="getNameByType")
	private Integer userType;
	
	@ExcelMergeColum
	@ExcelField(title="年龄")
	private Integer age;
	
	@ExcelMergeColum
	@ExcelField(title="血型")
	private String boolType;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public List<UserGift> getGiftList() {
		return giftList;
	}

	public void setGiftList(List<UserGift> giftList) {
		this.giftList = giftList;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getBoolType() {
		return boolType;
	}

	public void setBoolType(String boolType) {
		this.boolType = boolType;
	}
}
