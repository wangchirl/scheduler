package com.shadow.task.simple;

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
            return getScheduleService().test(p);
        });
    }

    @Bean(value = JobNamingConsts.SIMPLE_TEST2)
    public ICronTriggerTask<Integer> testDis() {
        return new TriggerTaskHelper<Integer>().generateTask(ScheduleTaskInfoEnum.TEST_DISTRIBUTION_TASK,(p) -> {
            return getScheduleService().testDis(p);
        });
    }

}
