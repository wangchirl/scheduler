package com.shadow.supports.framework.support;

import com.shadow.supports.framework.ICronTriggerTask;
import com.shadow.supports.framework.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.support.CronTrigger;

import java.lang.ref.SoftReference;
import java.time.LocalDateTime;
import java.util.concurrent.Callable;


@Slf4j
public abstract class CronTriggerSupport<T> implements ICronTriggerTask<T>, ApplicationContextAware, InitializingBean, Callable<T> {

    private SoftReference<ScheduleResult<T>> result;

    private CronTrigger trigger;

    private ApplicationContext applicationContext;

    private ScheduleService scheduleService;

    public ScheduleService getScheduleService() {
        return scheduleService;
    }

    @Override
    public ScheduleResult<T> getResult() {
        return result == null ? null : result.get();
    }

    @Override
    public void run() {
        try {
            ScheduleResult<T> scheduleResult = new ScheduleResult<>();
            scheduleResult.setLastStartTime(LocalDateTime.now());
            // get current obj
            CronTriggerSupport<T> target = getCurrentObj();
            // invoke
            scheduleResult.setResult(target.call());
            scheduleResult.setLastEndTime(LocalDateTime.now());
            result = new SoftReference<>(scheduleResult);
        } catch (Exception e) {
            log.error("invoke [ " + this.type() + " ] job error {}", e.getMessage(), e);
        }
    }

    private CronTriggerSupport<T> getCurrentObj() {
        CronTriggerSupport<T> target = this;
        try {
            // for aop
            target = (CronTriggerSupport<T>) AopContext.currentProxy();
        } catch (IllegalStateException e) {
            log.debug("proxy is null, use current obj to invoke call method ...");
        }
        return target;
    }

    protected ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String cronExpression = applicationContext.getEnvironment().getProperty(getCronName());
        if (StringUtils.isEmpty(cronExpression)) {
            throw new RuntimeException("cron name [ + " + getCronName() + " ] not found cron expression ...");
        }
        trigger = new CronTrigger(cronExpression);
        scheduleService = applicationContext.getBean(ScheduleService.class);
    }

    @Override
    public Runnable getTask() {
        return this;
    }

    @Override
    public CronTrigger getTrigger() {
        return trigger;
    }

    @Override
    public CronTrigger setTrigger(String newCron) {
        String oldCron = trigger.getExpression();
        trigger = new CronTrigger(newCron);
        log.info("update cron old {} to new {} ", oldCron, newCron);
        return trigger;
    }

    @Override
    public String toString() {
        return "TriggerTask {" +
                "trigger = " + getTrigger() +
                "task name = " + this.type() +
                "cronTrigger = " + getTrigger().getExpression() +
                "}";
    }
}
