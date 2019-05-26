
package com.wn.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wn.util.AjaxResult;

/** 
* @author 作者 wunan: 
* @version 创建时间：2019年5月26日 下午5:24:29 
*/
@RequestMapping("/api")
@RestController
public class Test {
	
	@RequestMapping("/test")
	public AjaxResult test() {
		return AjaxResult.wrapSuccess("success welcome");
	}
}
