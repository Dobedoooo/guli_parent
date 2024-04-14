package com.atguigu.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu"})
@EnableDiscoveryClient  // nacos 注册
@EnableFeignClients
public class EduApplication {
    public static void main(String[] args) {
        try {

            SpringApplication.run(EduApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
