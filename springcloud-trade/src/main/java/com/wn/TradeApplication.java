package com.wn;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/** 
 * 订单服务
* @author 作者 wunan: 
* @version 创建时间：2019年5月26日 下午4:09:25 
*/
@SpringBootApplication
@EnableEurekaClient
@EnableAutoConfiguration
public class TradeApplication {
	
	public static void main(String[] args) {
        // 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
        SpringApplication.run(TradeApplication.class,args);
    }
}
