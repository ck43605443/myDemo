package com.nwc.demo01.listener;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.nwc.demo01.config.QuartzManager;
import com.nwc.demo01.service.InitJob;
import org.quartz.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 容器监听器
 * @author QQ 1535512395
 */
@Component
public class QuartzJobListener implements ServletContextListener {
    @Autowired
    private QuartzManager quartzManager;
 
    public void contextInitialized(ServletContextEvent arg0) {
        System.out.println("监听器启动");
        Date date = new Date();
        date.setMinutes(10);
        date.setSeconds(30);
        String s = new SimpleDateFormat("ss mm HH dd MM ? yyyy").format(date);
        quartzManager.addJob("动态任务调度", "XLXXCC_JOB_GROUP", "初始化任务", "XLXXCC_JOB_GROUP", InitJob.class, s);
        System.out.println(s);

        /***处理获取数据库的job表，然后遍历循环每个加到job中 ***/
//        QuartzManager quartzManager = WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext()).getBean(QuartzManager.class);

//        //此处就不写获取数据库了，模拟一个集合遍历的数据
//        List<Map<String,Object>> listMap=new ArrayList<>();
//        Map<String, Object> map1=new HashMap<String, Object>();
//        map1.put("jobClass","com.yj.quartzjob.QuartzJob");
//        map1.put("jobName","job1");
//        map1.put("jobGroupName","job1");
//        map1.put("jobTime","0/5 * * * * ? ");
//        listMap.add(map1);
//
//        for (Map<String, Object> map : listMap) {
//            try {
//                quartzManager.addJob((Class<? extends Job>)(Class.forName((String)map1.get("jobClass")).newInstance().getClass()),(String)map.get("jobName"), (String)map.get("jobGroupName"),(String)map.get("jobTime"));
//        quartzManager.addManageJob();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        System.out.println("QuartzJobListener 启动了");
    }
    public void contextDestroyed(ServletContextEvent arg0) {
    }
 
}