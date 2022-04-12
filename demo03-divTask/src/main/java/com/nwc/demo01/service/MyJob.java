package com.nwc.demo01.service;

import java.util.Date;

import com.nwc.demo01.feign.DevcntrStatusCurrentControllerFeign;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class MyJob implements Job{
    @Autowired
    private DevcntrStatusCurrentControllerFeign feign;

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        JobKey key = jobExecutionContext.getJobDetail().getKey(); //获取组名
//        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
//        Object o = dataMap.get("2");
        String s = feign.feignTest("定时");
        System.out.println(new Date() + ": 每隔1秒执行一次的任务:"+s);
    }
}
