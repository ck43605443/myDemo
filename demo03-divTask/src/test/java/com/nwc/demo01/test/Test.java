package com.nwc.demo01.test;

import com.nwc.demo01.config.QuartzManager;
import com.nwc.demo01.service.MyJob;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test {
    @Autowired
    private QuartzManager quartzManager;

    public static String JOB_NAME = "动态任务调度";  
    public static String TRIGGER_NAME = "动态任务触发器";  
    public static String JOB_GROUP_NAME = "XLXXCC_JOB_GROUP";  
    public static String TRIGGER_GROUP_NAME = "XLXXCC_JOB_GROUP"; 


    public  void main() {
        try {  
            System.out.println("【系统启动】开始(每1秒输出一次)...");
            quartzManager.addJob(JOB_NAME, JOB_GROUP_NAME, TRIGGER_NAME, TRIGGER_GROUP_NAME, MyJob.class, "0/1 * * * * ?");

            Thread.sleep(5000);    
            System.out.println("【修改时间】开始(每5秒输出一次)...");
            quartzManager.modifyJobTime(JOB_NAME, JOB_GROUP_NAME, TRIGGER_NAME, TRIGGER_GROUP_NAME, "0/5 * * * * ?");

            Thread.sleep(6000);    
            System.out.println("【移除定时】开始...");
            quartzManager.removeJob(JOB_NAME, JOB_GROUP_NAME, TRIGGER_NAME, TRIGGER_GROUP_NAME);
            System.out.println("【移除定时】成功");    
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }

    public static void main(String[] args) {
        Date date = new Date();
        String s = new SimpleDateFormat("s m H d M ? yyyy").format(date).toString();
        System.out.println(s);
        System.out.println("----------------");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String t = calendar.get(Calendar.SECOND) + " " + calendar.get(Calendar.MINUTE) + " "+calendar.get(Calendar.HOUR_OF_DAY)+" "+  calendar.get(Calendar.DAY_OF_MONTH) + " " + (calendar.get(Calendar.MONTH)+1) + " ? " + calendar.get(Calendar.YEAR);
        System.out.println(t);
    }
}