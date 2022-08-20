package com.shadow.supports.framework.support;

import com.shadow.supports.framework.ScheduleService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 任务基础类
 */
public class JobHandlerSupport implements ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    private ScheduleService scheduleService;

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public ScheduleService getScheduleService() {
        return scheduleService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        scheduleService = applicationContext.getBean(ScheduleService.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
