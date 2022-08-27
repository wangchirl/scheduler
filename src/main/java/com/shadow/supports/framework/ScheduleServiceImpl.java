package com.shadow.supports.framework;

import com.alibaba.fastjson.JSON;
import com.shadow.supports.framework.aop.SimpleJob;
import com.shadow.utils.JobNamingConsts;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Override
    public void consumer(Consumer<Object> consumer) {
        Object params = ScheduleService.JOB_PARAMETERS_THREAD_LOCAL.get();
        consumer.accept(params);
    }

    @Override
    public <T> T supplier(Supplier<T> supplier) {
        return supplier.get();
    }

    @Override
    public <T> T function(Function<Object, T> function) {
        Object params = ScheduleService.JOB_PARAMETERS_THREAD_LOCAL.get();
        return function.apply(params);
    }

    @Override
    @SimpleJob(value = JobNamingConsts.SIMPLE_TEST1)
    public Integer test(String params) {
        log.info("simple job test1 invoke : PARAMS = {} ", JSON.toJSON(params));
        return 1024;
    }

    @Override
    @SimpleJob(value = JobNamingConsts.SIMPLE_TEST2)
    @SchedulerLock(name = JobNamingConsts.SIMPLE_TEST2, lockAtLeastFor = "30000")
    public Integer testDis(String params) {
        log.info("simple job test2 invoke : PARAMS = {} ", JSON.toJSON(params));
        return 1024;
    }
}
