package com.shadow.task.xxl;

import com.alibaba.fastjson.JSON;
import com.shadow.supports.framework.ScheduleService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.shadow.utils.JobNamingConsts.XXL_TEST1;
import static com.shadow.utils.JobNamingConsts.XXL_TEST2;

@Slf4j
@Component
public class XxlJobHandler {

    @XxlJob(value = XXL_TEST1)
    public void xxl1() {
        System.out.println("invoke xxl job " + XXL_TEST1 + " PARAMS = " + JSON.toJSONString(ScheduleService.JOB_PARAMETERS_THREAD_LOCAL.get()));
    }

    @XxlJob(value = XXL_TEST2)
    public void xxl2() {
        System.out.println("invoke xxl job " + XXL_TEST2 + " PARAMS = " + JSON.toJSONString(ScheduleService.JOB_PARAMETERS_THREAD_LOCAL.get()));
    }

}
