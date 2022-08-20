package com.shadow.supports.framework;

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
    public void consumer(Consumer<String> consumer) {
        String params = ScheduleService.JOB_PARAMETERS_THREAD_LOCAL.get();
        consumer.accept(params);
    }

    @Override
    public <T> T supplier(Supplier<T> supplier) {
        return supplier.get();
    }

    @Override
    public <T> T function(Function<String, T> function) {
        String params = ScheduleService.JOB_PARAMETERS_THREAD_LOCAL.get();
        return function.apply(params);
    }

    @Override
    @SimpleJob(value = JobNamingConsts.SIMPLE_TEST1)
    public Integer test(String params) {
        log.info("params : {} ", params);
        return 1024;
    }

    @Override
    @SimpleJob(value = JobNamingConsts.SIMPLE_TEST2)
    @SchedulerLock(name = JobNamingConsts.SIMPLE_TEST2, lockAtLeastFor = "30000")
    public Integer testDis(String params) {
        log.info("params : {} ", params);
        return 1024;
    }
}
