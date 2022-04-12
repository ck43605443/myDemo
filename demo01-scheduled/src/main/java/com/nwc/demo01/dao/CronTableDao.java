package com.nwc.demo01.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nwc.demo01.pojo.CronTable;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CronTableDao extends BaseMapper<CronTable> {
}
