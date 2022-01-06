package com.nwc.demo01.dao;

import com.nwc.demo01.pojo.DevcntrStatusCurrent;
import com.nwc.demo01.pojo.RptcntrTrafficMinuteOrHour;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DevcntrStatusCurrentDao {

    List<DevcntrStatusCurrent> findBy5min();

    void saveRptcntrTrafficMinute(@Param("minuteList") List<RptcntrTrafficMinuteOrHour> minuteList);

    List<RptcntrTrafficMinuteOrHour> findBy1hour(@Param("year") Integer year,@Param("month") Integer month,@Param("day")Integer day,@Param("hour")Integer hour);

    void saveRptcntrTrafficHour(@Param("hourList") List<RptcntrTrafficMinuteOrHour> list);
}
