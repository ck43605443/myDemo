package com.nwc.demo01.config;

import com.nwc.demo01.service.MyJob;
import com.nwc.demo01.service.ThreadLocalUtil;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class QuartzManager {
    @Autowired
    private Scheduler scheduler;

//    public static SchedulerFactory schedulerFactory = new StdSchedulerFactory();

    /** 
     * @Description: 添加一个定时任务 
     *  
     * @param jobName 任务名 
     * @param jobGroupName  任务组名 
     * @param triggerName 触发器名 
     * @param triggerGroupName 触发器组名 
     * @param jobClass  任务 
     * @param cron   时间设置，参考quartz说明文档  
     */


    @SuppressWarnings({ "unchecked", "rawtypes" })  
    public  void addJob(String jobName, String jobGroupName,
            String triggerName, String triggerGroupName, Class jobClass, String cron) {  
        try {

//            Scheduler sched = schedulerFactory.getScheduler();
            // 任务名，任务组，任务执行类
            JobDetail jobDetail= JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();

            // 触发器  
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名,触发器组  
            triggerBuilder.withIdentity(triggerName, triggerGroupName);
            triggerBuilder.startNow();
            // 触发器时间设定  
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
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
    }  

    /** 
     * @Description: 修改一个任务的触发时间
     *  
     * @param jobName 
     * @param jobGroupName
     * @param triggerName 触发器名
     * @param triggerGroupName 触发器组名 
     * @param cron   时间设置，参考quartz说明文档   
     */  
    public  void modifyJobTime(String jobName,
            String jobGroupName, String triggerName, String triggerGroupName, String cron) {  
        try {  
//            Scheduler sched = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null) {  
                return;  
            }  

            String oldTime = trigger.getCronExpression();  
            if (!oldTime.equalsIgnoreCase(cron)) { 
                /** 方式一 ：调用 rescheduleJob 开始 */
                // 触发器  
                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                // 触发器名,触发器组  
                triggerBuilder.withIdentity(triggerName, triggerGroupName);
                triggerBuilder.startNow();
                // 触发器时间设定  
                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
                // 创建Trigger对象
                trigger = (CronTrigger) triggerBuilder.build();
                // 方式一 ：修改一个任务的触发时间
                scheduler.rescheduleJob(triggerKey, trigger);
                /** 方式一 ：调用 rescheduleJob 结束 */

                /** 方式二：先删除，然后在创建一个新的Job  */
                //JobDetail jobDetail = sched.getJobDetail(JobKey.jobKey(jobName, jobGroupName));  
                //Class<? extends Job> jobClass = jobDetail.getJobClass();  
                //removeJob(jobName, jobGroupName, triggerName, triggerGroupName);  
                //addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron); 
                /** 方式二 ：先删除，然后在创建一个新的Job */
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  

    /** 
     * @Description: 移除一个任务 
     *  
     * @param jobName 
     * @param jobGroupName 
     * @param triggerName 
     * @param triggerGroupName 
     */  
    public  void removeJob(String jobName, String jobGroupName,
            String triggerName, String triggerGroupName) {  
        try {  
//            Scheduler sched = schedulerFactory.getScheduler();

            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);

            scheduler.pauseTrigger(triggerKey);// 停止触发器
            scheduler.unscheduleJob(triggerKey);// 移除触发器
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  

    /** 
     * @Description:启动所有定时任务 
     */  
    public  void startJobs() {
        try {  
//            Scheduler sched = schedulerFactory.getScheduler();
            scheduler.start();
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  

    /** 
     * @Description:关闭所有定时任务 
     */  
    public  void shutdownJobs() {
        try {  
//            Scheduler sched = schedulerFactory.getScheduler();
            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }
        public  void addManageJob() {
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
//                    triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ? 2022"));
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

