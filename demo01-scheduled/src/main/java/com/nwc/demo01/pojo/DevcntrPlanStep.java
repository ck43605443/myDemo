package com.nwc.demo01.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DevcntrPlanStep {
    private Integer planId;
    private Integer planStep;
    private Integer unitId;
    private Integer deviceId;
    private Integer deviceType;
    private String stepDesc;
    private Integer deviceCommondType;

}
