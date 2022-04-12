package com.nwc.demo01.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DevcntrPlanStepDao {

    void updateById(@Param("id") Integer id, @Param("type") Integer type);


}
