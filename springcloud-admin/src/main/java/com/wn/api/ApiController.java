package com.wn.api;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.wn.util.AjaxResult;
import com.wn.util.User;
import com.wn.util.UserBooks;
import com.wn.util.UserGift;
import com.wn.util.UserGiftConfig;
import com.wn.util.excel.ExcelUtils;

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
    
    
    @RequestMapping("/export")
    public void export(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, InstantiationException {
    	List<User> userList = new ArrayList<>();
    	User user = new User();
		user.setId(1);
		user.setAge(22);
		user.setUserName("wn0");
		user.setMobile("15267175695");
		user.setUserType(1);
		user.setBoolType("");
		List<UserGift> giftList = new ArrayList<>();
		UserGift gift = new UserGift();
		gift.setId(1);
		gift.setUserId(1);
		gift.setGiftName("笔记本011111111111111111111111111111111");
		gift.setNum(1);
		gift.setProvince("河南");
		gift.setControry("中国");
		gift.setCity("郑州");
		giftList.add(gift);
		
		UserGift gift2 = new UserGift();
		gift2.setId(1);
		gift2.setUserId(1);
		gift2.setGiftName("笔记本0");
		gift2.setNum(1);
		gift2.setProvince("河南");
		gift2.setControry("中国");
		gift2.setCity("郑州");
		giftList.add(gift2);
		user.setGiftList(giftList);
		
    	
//    	List<User> userList = new ArrayList<>();
//    	User user = new User();
//		user.setId(1);
//		user.setAge(22);
//		user.setUserName("wn0");
//		user.setMobile("15267175695");
//		user.setUserType(1);
//		user.setBoolType("O");
//		List<UserGift> giftList = new ArrayList<>();
//		UserGift gift = new UserGift();
//		gift.setId(1);
//		gift.setUserId(1);
//		gift.setGiftName("笔记本011111111111111111111111111111111");
//		gift.setNum(1);
//		gift.setProvince("河南");
//		gift.setControry("中国");
//		gift.setCity("郑州");
//		
//		List<UserGiftConfig> confgList = new ArrayList<>();
//		UserGiftConfig con = new UserGiftConfig();
//		con.setId(1);
//		con.setName("配置一");
//		con.setTime("2018-2019");
//		confgList.add(con);
//		gift.setConfigList(confgList);
//		giftList.add(gift);
//		
//		UserGift gift2 = new UserGift();
//		gift2.setId(1);
//		gift2.setUserId(1);
//		gift2.setGiftName("笔记本0");
//		gift2.setNum(1);
//		gift2.setProvince("河南");
//		gift2.setControry("中国");
//		gift2.setCity("郑州");
//		
//		List<UserGiftConfig> confgList2 = new ArrayList<>();
//		UserGiftConfig con2 = new UserGiftConfig();
//		con2.setId(1);
//		con2.setName("配置二");
//		con2.setTime("2018-2019");
//		confgList2.add(con2);
//		gift2.setConfigList(confgList2);
//		
//		giftList.add(gift2);
//		user.setGiftList(giftList);
		
		userList.add(user);
		
		
//		List<User> userList2 = new ArrayList<>();
//		User user1 = new User();
//		user1.setId(1);
//		user1.setUserName("wn1");
//		user1.setMobile("15267175697");
//		user1.setUserType(1);
//		user1.setAge(22);
//		user1.setBoolType(null);
//		List<UserGift> giftList1 = new ArrayList<>();
//		UserGift gift1 = new UserGift();
//		gift1.setId(2);
//		gift1.setUserId(1);
//		gift1.setProvince("浙江");
//		gift1.setControry("中国");
//		gift1.setCity("杭州");
//		gift1.setGiftName("笔记本1");
//		gift1.setNum(1);
//		giftList1.add(gift1);
//		user1.setGiftList(giftList1);
//		userList2.add(user1);
		System.out.println(JSON.toJSONString(userList));
		ExcelUtils.exportExcel(request, response, "test吴南", new String[] {"test1"}, userList);
    }
}
