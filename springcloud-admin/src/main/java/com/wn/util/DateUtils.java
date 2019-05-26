package com.wn.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	/** yyyy-MM-dd HH:mm:ss */
	private static final String yyyymmddHHmmss = "yyyy-MM-dd HH:mm:ss";
	
	
	/**
	 * 转化时间为字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String convertDate(Date date,String format) {
		if(date == null || StringUtils.isEmpty(format))return null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
    public static String convertDate(Date date) {
    	return convertDate(date, yyyymmddHHmmss);
	}
}
