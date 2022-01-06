package com.nwc.demo01.pojo;

import lombok.Data;

@Data
public class RptcntrTrafficMinuteOrHour {

    private Integer unitId;
    private Integer deviceId;
    private Integer timeYear;
    private Integer timeMonth;
    private Integer timeDay;
    private Integer timeHour;
    private Integer timeMinute;
    private Integer direction;
    private Integer laneId;
    private Integer vehicleType;
    private Integer vehicleVolume;
    private Integer vehicleLength;
    private Integer vehicleDensity;
    private Integer averageScale;
    private Double occupancyRatio;
    private Double distanceHeadway;
    private Double averageSpeed;

}
