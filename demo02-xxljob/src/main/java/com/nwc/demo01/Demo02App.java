package com.nwc.demo01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableScheduling // 开启定时任务
public class Demo02App {

    public static void main(String[] args) {
        SpringApplication.run(Demo02App.class, args);
    }
}
