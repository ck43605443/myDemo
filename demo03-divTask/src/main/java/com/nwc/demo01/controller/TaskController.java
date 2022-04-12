package com.nwc.demo01.controller;

import com.nwc.demo01.config.QuartzManager;
import com.nwc.demo01.feign.DevcntrStatusCurrentControllerFeign;
import com.nwc.demo01.service.MyJob;
import com.nwc.demo01.service.ThreadLocalUtil;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
public class TaskController {
    public static String JOB_NAME = "动态任务调度";
    public static String TRIGGER_NAME = "动态任务触发器";
    public static String JOB_GROUP_NAME = "XLXXCC_JOB_GROUP";
    public static String TRIGGER_GROUP_NAME = "XLXXCC_JOB_GROUP";

    @Autowired
     private  DevcntrStatusCurrentControllerFeign feign;
    @Autowired
   private QuartzManager quartzManager;
    @Autowired
    private Scheduler scheduler;
    @GetMapping("/post")
    public void post() {
        quartzManager.addJob("动态任务调度1", "XLXXCC_JOB_GROUP2", "动态任务触发器1", TRIGGER_GROUP_NAME, MyJob.class, "0/1 * * * * ?");
//        QuartzManager.addJob("动态任务调度2", JOB_GROUP_NAME, "动态任务触发器2", TRIGGER_GROUP_NAME, YourJob.class, "0/2 * * * * ?");
    }
        @GetMapping("/get2")
    public void get2() {
            String s = feign.feignTest("测试");
            System.out.println(s);

        }


        @GetMapping("/get1")
    public void get1() {
            Date date;
            CronExpression exp;
            // Run every 10 minutes and 30 seconds in the year 2002
            String a = "0 0 11 31 1 ? 2022";
            // Run every 10 minutes and 30 seconds of any year
            String b = "0 3 11 31 1 ? 2022";
            try {
                exp = new CronExpression(a);
                date = exp.getNextValidTimeAfter(new Date());
                System.out.println(date);  // null
                exp = new CronExpression(b);
                date = exp.getNextValidTimeAfter(new Date());
                System.out.println(date);  // Tue Nov 04 19:20:30 PST 2014
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        @GetMapping("/get")
    public void getAll() {
//        QuartzManager.addJob("动态任务调度1", JOB_GROUP_NAME, "动态任务触发器1", TRIGGER_GROUP_NAME, MyJob.class, "0/5 * * * * ?");
//        QuartzManager.addJob("动态任务调度2", JOB_GROUP_NAME, "动态任务触发器2", TRIGGER_GROUP_NAME, YourJob.class, "0/2 * * * * ?");


        try {
//            Scheduler sched = QuartzManager.schedulerFactory.getScheduler();
            List<String> jobGroupNames = scheduler.getJobGroupNames();
            //判断是否已存在  存在就删除之前的任务 执行新的任务
            for (String jobGroupName : jobGroupNames) {
                if(jobGroupName.equals("XLXXCC_JOB_GROUP")){//如果job已经存在
                    GroupMatcher<JobKey> matcher = GroupMatcher.groupEquals("XLXXCC_JOB_GROUP");
                    Set<JobKey> jobkeySet = scheduler.getJobKeys(matcher);
                    List<JobKey> jobkeyList = new ArrayList<>();
                    jobkeyList.addAll(jobkeySet);
                    scheduler.deleteJobs(jobkeyList);
                    System.out.println("任务已存在,删除成功");
                }
            }
                    try {

//                        TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
//                        sched.pauseTrigger(triggerKey);// 停止触发器
//                        sched.unscheduleJob(triggerKey);// 移除触发器
//                        sched.deleteJob(JobKey.jobKey("my_job", jobGroupName));// 删除任务

                            // 任务名，任务组，任务执行类
                            JobDataMap jobDataMap = new JobDataMap();
                            jobDataMap.put("2", "哈哈哈哈");
                            JobDetail jobDetail= JobBuilder.newJob(MyJob.class).withIdentity("my_job", "XLXXCC_JOB_GROUP").usingJobData(jobDataMap).build();

                            // 触发器
                            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                            // 触发器名,触发器组
                            triggerBuilder.withIdentity("my_jobTag", "XLXXCC_JOB_GROUP");
                            triggerBuilder.startNow();
                            // 触发器时间设定
                            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ? 2022"));
                            // 创建Trigger对象
                            CronTrigger trigger = (CronTrigger) triggerBuilder.build();

                            // 调度容器设置JobDetail和Trigger
                            scheduler.scheduleJob(jobDetail, trigger);

                            // 启动
                            if (!scheduler.isShutdown()) {
                                scheduler.start();
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
            String s = ThreadLocalUtil.get();
            System.out.println(s);
            ThreadLocalUtil.remove();
        }

}
