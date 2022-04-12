package com.nwc.demo01.service;

import com.nwc.demo01.dao.EtlcntrSystemDao;
import com.nwc.demo01.pojo.DevcntrStatusCurrent;
import com.nwc.demo01.pojo.RptcntrTrafficMinuteOrHour;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class DevcntrStatusCurrentServiceImpl implements DevcntrStatusCurrentService {
    @Autowired
    private EtlcntrSystemDao devcntrStatusCurrentDao;


    @Override
    public List<DevcntrStatusCurrent> findBy5min() {
        return devcntrStatusCurrentDao.findBy5min();
    }

    @Override
    public void saveRptcntrTrafficMinute(List<RptcntrTrafficMinuteOrHour> minuteList) {
        try {
            devcntrStatusCurrentDao.saveRptcntrTrafficMinute(minuteList);
        } catch (Exception e) {
            System.out.println("插入失败, 可能存在重复数据");
        }
    }

    @Override
    public void saveRptcntrTrafficHour(List<RptcntrTrafficMinuteOrHour> list) {
        try {
            devcntrStatusCurrentDao.saveRptcntrTrafficHour(list);
        } catch (Exception e) {
            System.out.println("插入失败, 可能存在重复数据");
        }
    }

    @Override
    public List<RptcntrTrafficMinuteOrHour> findBy1hour(String language) {
        DateTime time = DateTime.now().minusHours(1);//当前时间减一小时
//        DateTime time = DateTime.now().minusDays(5);
        return devcntrStatusCurrentDao.findBy1hour(time.getYear(), time.getMonthOfYear(), time.getDayOfMonth(), time.getHourOfDay(),language);
    }

    @Override
    public List<RptcntrTrafficMinuteOrHour> xmlToMinutes(String deviceDataXml, DevcntrStatusCurrent devcntrStatusCurrent) {
        if (!deviceDataXml.equals("0")) {
            List<RptcntrTrafficMinuteOrHour> list = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            try {
                Document document = DocumentHelper.parseText(deviceDataXml);
                Element rootElement = document.getRootElement();
                List<Element> elements = rootElement.elements();
                for (Element element : elements) {
                    List<Element> lanElement = element.elements();
                    for (Element element1 : lanElement) {

                        RptcntrTrafficMinuteOrHour RptcntrTrafficMinuteOrHour = new RptcntrTrafficMinuteOrHour();
                        List<Element> typeElements = element1.elements();
                        for (Element typeElement : typeElements) {//遍历 TYPE 123节点


//                        System.out.println(element1.getName() + " : " + element1.getText());
                            if (element1.getName().contains("TYPE") && !element1.getName().equals("TYPE_CLASS")) {
                                int type = Integer.parseInt(element1.getName().substring(4));
                                RptcntrTrafficMinuteOrHour.setVehicleType(type);
                                if (typeElement.getName().equals("VEHICLE_RATE")) {
                                    typeElement.setText("123");
                                    RptcntrTrafficMinuteOrHour.setAverageSpeed(Double.parseDouble(typeElement.getText()));
                                }
                                if (typeElement.getName().equals("POSSESS_RATE")) {
                                        RptcntrTrafficMinuteOrHour.setOccupancyRatio(Double.parseDouble(typeElement.getText()));
                                }
                                if (typeElement.getName().equals("VEHICLE_FLUX")) {
                                    Date sampleTime = sdf.parse(element.elementText("SAMPLE_TIME"));
                                    cal.setTime(sampleTime);
                                    RptcntrTrafficMinuteOrHour.setTimeYear(cal.get(Calendar.YEAR));
                                    RptcntrTrafficMinuteOrHour.setTimeMonth(cal.get(Calendar.MONTH) + 1);
                                    RptcntrTrafficMinuteOrHour.setTimeDay(cal.get(Calendar.DAY_OF_MONTH));
                                    RptcntrTrafficMinuteOrHour.setTimeHour(cal.get(Calendar.HOUR_OF_DAY));
                                    RptcntrTrafficMinuteOrHour.setTimeMinute(cal.get(Calendar.MINUTE));
                                    RptcntrTrafficMinuteOrHour.setUnitId(devcntrStatusCurrent.getUnitId());
                                    RptcntrTrafficMinuteOrHour.setDeviceId(devcntrStatusCurrent.getDeviceId());
                                    RptcntrTrafficMinuteOrHour.setDirection(Integer.parseInt(element.elementText("DIRECTION")));
                                    RptcntrTrafficMinuteOrHour.setLaneId(Integer.parseInt(element.elementText("LANE_ID")));
                                    RptcntrTrafficMinuteOrHour.setVehicleVolume(0);
                                    RptcntrTrafficMinuteOrHour.setVehicleLength(0);
                                    RptcntrTrafficMinuteOrHour.setVehicleDensity(0);
                                    RptcntrTrafficMinuteOrHour.setAverageScale(0);
                                    RptcntrTrafficMinuteOrHour.setDistanceHeadway(0.0D);

                                    list.add(RptcntrTrafficMinuteOrHour);
                                }
                            }
                        }
                    }
                }
                System.out.println(document.asXML());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }
        return null;
    }


}
