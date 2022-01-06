package com.nwc.demo01.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class DevcntrStatusCurrent {

    private Integer deviceId;
    private Integer unitId;
    private Integer deviceType;
    private Integer deviceStatus;
    private String deviceData;
    private Integer dataTag;
    private Date lastTime;
    private Integer refreshTag;

}
