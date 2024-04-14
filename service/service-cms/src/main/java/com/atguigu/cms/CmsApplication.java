package com.atguigu.cms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu"})
public class CmsApplication {
    public static void main(String[] args) {
        try {

            SpringApplication.run(CmsApplication.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
