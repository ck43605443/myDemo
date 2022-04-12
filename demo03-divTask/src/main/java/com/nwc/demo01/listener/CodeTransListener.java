package com.nwc.demo01.listener;

import com.nwc.demo01.config.QuartzManager;
import com.nwc.demo01.dao.CronTableDao;
import com.nwc.demo01.pojo.CronTable;
import com.nwc.demo01.service.InitJob;
import com.nwc.demo01.service.MyJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

//@Component
public class CodeTransListener {
    @Autowired
    private CronTableDao dao;
    @Autowired
    private QuartzManager quartzManager;

    public void contextInitialized() {
        List<CronTable> cronTables = dao.selectList(null);
        for (CronTable cronTable : cronTables) {
            quartzManager.addJob("动态任务调度", "XLXXCC_JOB_GROUP", "初始化任务", "XLXXCC_JOB_GROUP", InitJob.class, cronTable.getCron());
        }
    }
}