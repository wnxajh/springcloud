package com.wn.util.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * excel域注解
 * @author wnxaj
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelField {

	boolean open() default true; //默认起作用
	
	String title() default ""; //列标题
	
	boolean isList() default false; //是否对象（用来合并行） 
	
	int colWidth() default 6000; //列宽
	
}
