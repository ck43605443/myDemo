package com.nwc.demo01.controller;

import com.nwc.demo01.dao.CronTableDao;
import com.nwc.demo01.dao.DevcntrPlanStepDao;
import com.nwc.demo01.pojo.CronTable;
import com.nwc.demo01.pojo.DevcntrStatusCurrent;
import com.nwc.demo01.pojo.RptcntrTrafficMinuteOrHour;
import com.nwc.demo01.service.DevcntrStatusCurrentService;
import com.nwc.demo01.util.TaskUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class DevcntrStatusCurrentController {

    @Autowired
    private DevcntrStatusCurrentService devcntrStatusCurrentService;

//    @Scheduled(cron = "0 */5 * * * ?")//每5分钟执行
//    @Scheduled(cron = "0 0 */1 * * ?")
//    @GetMapping("/get")
    public void getXmlToMinutes() {
        List<DevcntrStatusCurrent> list = devcntrStatusCurrentService.findBy5min();
        if (list.size() > 0) {
            List<RptcntrTrafficMinuteOrHour> rptcntrTrafficMinuteOrHourList = null;
            for (DevcntrStatusCurrent devcntrStatusCurrent : list) {
                String deviceDataXml = devcntrStatusCurrent.getDeviceData();
                if (deviceDataXml != null) {
                    rptcntrTrafficMinuteOrHourList = devcntrStatusCurrentService.xmlToMinutes(deviceDataXml, devcntrStatusCurrent);
                }
            }
            if (rptcntrTrafficMinuteOrHourList != null && rptcntrTrafficMinuteOrHourList.size() > 0) {
//                devcntrStatusCurrentService.saveRptcntrTrafficMinute(rptcntrTrafficMinuteOrHourList);
            }
        }
    }

//        @Scheduled(cron = "0/5 * * * * ?")
//    @Scheduled(cron = "0 0 */1 * * ?")
    public void getMinutesToHour() {
        List<RptcntrTrafficMinuteOrHour> list = devcntrStatusCurrentService.findBy1hour("1");
        if (list.size() > 0) {
//            for (RptcntrTrafficMinuteOrHour rptcntrTrafficMinuteOrHour : list) {
//                System.out.println(rptcntrTrafficMinuteOrHour);
//            }
            devcntrStatusCurrentService.saveRptcntrTrafficHour(list);
        }
    }


    /*    @GetMapping("/get")
        public void  getArr() {
    //        String language = "zh-CN";
            String language = null;
            List<RptcntrTrafficMinuteOrHour> list2 = devcntrStatusCurrentService.findBy1hour(language);
            System.out.println(list2);

        }    */
    @Autowired
    private CronTableDao dao;
    @GetMapping("/get")
    public void  getArr() {
//        String language = "zh-CN";
        CronTable cronTable = dao.selectById(1);
        System.out.println(cronTable);

    }
        @PostMapping("/feignTest")
    public String  feignTest(@RequestBody String s) {
//        String language = "zh-CN";
        return  s+"+feign";

    }

}
