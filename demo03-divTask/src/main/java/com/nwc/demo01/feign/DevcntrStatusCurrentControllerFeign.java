package com.nwc.demo01.feign;

import com.nwc.demo01.dao.CronTableDao;
import com.nwc.demo01.pojo.CronTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@FeignClient( "demo01-scheduled")
public interface DevcntrStatusCurrentControllerFeign {


//    @Scheduled(cron = "0 */5 * * * ?")//每5分钟执行
//    @Scheduled(cron = "0 0 */1 * * ?")
//    @GetMapping("/get")
        @PostMapping("/feignTest")
    public String  feignTest(@RequestBody String s) ;

}
