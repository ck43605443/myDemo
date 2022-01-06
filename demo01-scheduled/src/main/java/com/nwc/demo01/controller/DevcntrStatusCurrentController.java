package com.nwc.demo01.controller;

import com.nwc.demo01.pojo.DevcntrStatusCurrent;
import com.nwc.demo01.pojo.RptcntrTrafficMinuteOrHour;
import com.nwc.demo01.service.DevcntrStatusCurrentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DevcntrStatusCurrentController {

    @Autowired
    private DevcntrStatusCurrentService devcntrStatusCurrentService;

    @Scheduled(cron = "0 */5 * * * ?")//每5分钟执行
//    @Scheduled(cron = "0 0 */1 * * ?")
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
                devcntrStatusCurrentService.saveRptcntrTrafficMinute(rptcntrTrafficMinuteOrHourList);
            }
        }
    }

    //    @Scheduled(cron = "0/5 * * * * ?")
    @Scheduled(cron = "0 0 */1 * * ?")
    public void getMinutesToHour() {
        List<RptcntrTrafficMinuteOrHour> list = devcntrStatusCurrentService.findBy1hour();
        if (list.size() > 0) {
//            for (RptcntrTrafficMinuteOrHour rptcntrTrafficMinuteOrHour : list) {
//                System.out.println(rptcntrTrafficMinuteOrHour);
//            }
            devcntrStatusCurrentService.saveRptcntrTrafficHour(list);
        }
    }
}
