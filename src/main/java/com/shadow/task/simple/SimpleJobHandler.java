package com.shadow.task.simple;

import com.alibaba.fastjson.JSON;
import com.shadow.supports.framework.ICronTriggerTask;
import com.shadow.supports.framework.support.JobHandlerSupport;
import com.shadow.supports.framework.support.TriggerTaskHelper;
import com.shadow.supports.helper.ScheduleTaskInfoEnum;
import com.shadow.utils.JobNamingConsts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleJobHandler extends JobHandlerSupport {

    @Bean(value = JobNamingConsts.SIMPLE_TEST1)
    public ICronTriggerTask<Integer> test() {
        return new TriggerTaskHelper<Integer>().generateTask(ScheduleTaskInfoEnum.TEST_NORMAL_TASK,(p) -> {
            return getScheduleService().test(JSON.toJSONString(p));
        });
    }

    @Bean(value = JobNamingConsts.SIMPLE_TEST2)
    public ICronTriggerTask<String> testDis() {
        return new TriggerTaskHelper<String>().generateTask(ScheduleTaskInfoEnum.TEST_DISTRIBUTION_TASK,(p) -> {
            return getScheduleService().testDis(JSON.toJSONString(p));
        });
    }

}
