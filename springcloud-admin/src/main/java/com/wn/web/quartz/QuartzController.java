
package com.wn.web.quartz;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

//import com.wn.model.QuartzConfig;
//import com.wn.service.QuartzConfigService;

/** 
* @author 作者 wunan: 
* @version 创建时间：2019年1月19日 下午2:44:29 
*/
@Controller
@RequestMapping("/sys/quartz")
public class QuartzController {
	
//	@Autowired
//	private QuartzConfigService quartzConfigService;
	
	 @RequestMapping(value = "/list")
	 public String list(Model model) {
//		List<QuartzConfig> list = quartzConfigService.findQuartzConfigList(null);
//		model.addAttribute("quartzConfigList", list);
		return "/admin/quartz/main";
	 }
}
