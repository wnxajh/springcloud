package com.wn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient // Eureka Discovery Client 标识
@SpringBootApplication
public class AdminApplication {

	@Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        // 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
        SpringApplication.run(AdminApplication.class,args);
        
    }
    
    
    
    /**
     * 修改DispatcherServlet默认配置(DispatcherServlet 默认拦截“/”)
     * wun
     * 2018年1月26日
     * @param dispatcherServlet
     * @return
     */
//    @Bean
//    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
//        ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
//        registration.getUrlMappings().clear();
//        registration.addUrlMappings("*.do");
//        registration.addUrlMappings("*.json");
//        return registration;
//    }
}
