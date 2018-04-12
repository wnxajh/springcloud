package com.wn;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement  //开启事务
@EnableDiscoveryClient
@SpringBootApplication
public class UcenterApplication {
	public static void main(String[] args) {
		SpringApplication.run(UcenterApplication.class, args);
	}
}
