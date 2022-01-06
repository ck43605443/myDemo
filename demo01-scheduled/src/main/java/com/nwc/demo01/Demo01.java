package com.nwc.demo01;

import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Demo01 {
//    @Scheduled(cron = "0/5 * * * * ?")
    public void scheduleDemo() {

        System.out.println("定时任务:"+ DateTime.now());

    }

}
