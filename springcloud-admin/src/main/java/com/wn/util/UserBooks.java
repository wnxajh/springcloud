
package com.wn.util;

import com.wn.util.excel.ExcelField;

/** 
* @author 作者 wunan: 
* @version 创建时间：2019年1月28日 下午3:33:44 
*/

public class UserBooks {

	
	private Integer id;
	
	@ExcelField(title="书名")
	private String bookName;
	
	@ExcelField(title="数量")
	private Integer num;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
}
