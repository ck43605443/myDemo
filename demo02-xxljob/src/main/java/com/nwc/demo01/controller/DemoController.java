package com.nwc.demo01.controller;

import com.nwc.demo01.bojo.XxlJobInfo;
import com.nwc.demo01.util.XxlJobUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class DemoController {

//    @Scheduled(cron = "0/5 * * * * ?")
//    public void taskJob1()  {
    @XxlJob("taskJob")
    public ReturnT<String> taskJob1(String param) throws Exception {
        System.out.println(new Date());
//            return ReturnT.FAIL;
        return ReturnT.SUCCESS;
    }
        @XxlJob("divTask")
    public ReturnT<String> divTask(String param) throws Exception {
        System.out.println("动态定时任务:"+new Date());
//            return ReturnT.FAIL;
        return ReturnT.SUCCESS;
    }

    @GetMapping("/get")
    public void get() {
        XxlJobUtil xxlJobUtil = new XxlJobUtil();
        XxlJobInfo xxlJobInfo = new XxlJobInfo();
        xxlJobInfo.setId(0);
        xxlJobInfo.setJobGroup(3);
        xxlJobInfo.setJobCron("0/1 * * * * ?");
        xxlJobInfo.setJobDesc("测试动态定时");
        xxlJobInfo.setAddTime(null);
        xxlJobInfo.setUpdateTime(null);
        xxlJobInfo.setAuthor("nwc");
        xxlJobInfo.setExecutorRouteStrategy("FIRST");
        xxlJobInfo.setExecutorHandler("divTask");
        xxlJobInfo.setExecutorBlockStrategy("SERIAL_EXECUTION");
        xxlJobInfo.setExecutorTimeout(0);
        xxlJobInfo.setExecutorFailRetryCount(0);
        xxlJobInfo.setGlueType("BEAN");
        xxlJobInfo.setGlueSource("");
        xxlJobInfo.setGlueRemark("GLUE代码初始化");
        xxlJobInfo.setGlueUpdatetime(null);
        xxlJobInfo.setTriggerStatus(0);
        xxlJobInfo.setTriggerLastTime(0L);
        xxlJobInfo.setTriggerNextTime(0L);
        xxlJobUtil.addAndStart(xxlJobInfo);
    }



}
