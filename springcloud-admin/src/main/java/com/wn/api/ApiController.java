package com.wn.api;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.wn.util.AjaxResult;

@RestController
@RequestMapping("/api/")
public class ApiController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);
    private static final String serverId = "PRODUCER";
    
    @Autowired
    private RestTemplate restTemplate; // HTTP 访问操作类
    
    @Autowired
    private DiscoveryClient client;
    
    @RequestMapping("/getList")
    public AjaxResult getList() {
    	try {
    		String providerMsg = restTemplate.getForObject("http://"+serverId+"/list",
                    String.class);
    		 System.out.println("host:"+client.getLocalServiceInstance().getHost()+"   -----port:"+client.getLocalServiceInstance().getPort());
            LOGGER.info(String.format("返回信息:[%s]", providerMsg));
            return AjaxResult.wrap(providerMsg);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return AjaxResult.wrapFaild("系统异常", 0);
		}
    }
}
