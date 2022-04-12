package com.nwc.demo01.service;

import com.nwc.demo01.pojo.DevcntrStatusCurrent;
import com.nwc.demo01.pojo.RptcntrTrafficMinuteOrHour;

import java.util.List;

public interface DevcntrStatusCurrentService {

    List<DevcntrStatusCurrent> findBy5min();

    List<RptcntrTrafficMinuteOrHour> xmlToMinutes(String deviceDataXml,DevcntrStatusCurrent devcntrStatusCurrent);

    void saveRptcntrTrafficMinute(List<RptcntrTrafficMinuteOrHour> minuteList);

    List<RptcntrTrafficMinuteOrHour> findBy1hour(String language);

    void saveRptcntrTrafficHour(List<RptcntrTrafficMinuteOrHour> list);
}
