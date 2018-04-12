package com.wn.common;


public class AjaxResult{

	private String msg;
	
	private Integer code;
	
	private Object data;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
    public static AjaxResult wrap(Object data){
    	return wrap(data, 200, "");
	}
    
    public static AjaxResult wrapSuccess(String msg){
    	return wrap(null, 200, msg);
	}
    
    public static AjaxResult wrap(Object data,int code){
    	return wrap(data, code, "");
	}
	
	public static AjaxResult wrap(Object data,int code,String msg){
		AjaxResult ajaxResult = new AjaxResult();
		ajaxResult.setCode(code);
		ajaxResult.setMsg(msg);
		ajaxResult.setData(data);
		return ajaxResult;
	}
	
	public static AjaxResult wrapFaild(String msg,int code){
    	return wrap(null, code, msg);
	}
	
}
