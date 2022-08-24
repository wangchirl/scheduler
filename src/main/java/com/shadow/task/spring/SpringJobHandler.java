package com.shadow.task.spring;

import com.alibaba.fastjson.JSON;
import com.shadow.supports.framework.ScheduleService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SpringJobHandler {

    @Scheduled(cron = "0 1 * * * ?")
    public void spring1() {
        System.out.println("invoke spring1 job ..." + " PARAMS = " + JSON.toJSONString(ScheduleService.JOB_PARAMETERS_THREAD_LOCAL.get()));
    }

    @Scheduled(cron = "0 1 * * * ?")
    public void spring2() {
        System.out.println("invoke spring2 job ..." + " PARAMS = " + JSON.toJSONString(ScheduleService.JOB_PARAMETERS_THREAD_LOCAL.get()));
    }

}
