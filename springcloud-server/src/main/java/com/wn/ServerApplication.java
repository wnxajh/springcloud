package com.wn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer     // Eureka Server 标识
@SpringBootApplication  // Spring Boot 应用标识
public class ServerApplication {

	 public static void main(String[] args) {
		// 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
	    SpringApplication.run(ServerApplication.class,args);
	 }
}
