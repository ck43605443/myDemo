package com.nwc.demo01;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableScheduling
//@MapperScan("com.nwc.demo01.dao")
@EnableFeignClients
public class Demo03App {

    public static void main(String[] args) {
        SpringApplication.run(Demo03App.class, args);
    }
}
