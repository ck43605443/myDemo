package com.nwc.demo01.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@TableName("cron_table")
@Data
@AllArgsConstructor
public class CronTable {

    @TableId("id")
    private Integer id;
    @TableField("cron")
    private String cron;


}
