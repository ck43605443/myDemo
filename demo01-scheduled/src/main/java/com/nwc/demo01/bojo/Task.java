package com.nwc.demo01.bojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
    @AllArgsConstructor
   public  class Task {
        /**
         * 主键ID
         */
        private int id;
        /**
         * 任务名称
         */
        private String name;
        /**
         * cron表达式
         */
        private String cron;
    }