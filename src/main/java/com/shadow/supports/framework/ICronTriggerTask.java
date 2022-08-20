package com.shadow.supports.framework;

import com.shadow.supports.framework.support.ScheduleResult;
import org.springframework.scheduling.support.CronTrigger;

public interface ICronTriggerTask<T> extends Runnable {

    /**
     * the result for last execute
     */
    ScheduleResult<T> getResult();

    /**
     * schedule task type
     * unique key
     */
    String type();

    /**
     * schedule task cron name
     * properties / yml configure name
     */
    String getCronName();

    /**
     * real task
     */
    Runnable getTask();

    /**
     * get cronTrigger
     */
    CronTrigger getTrigger();

    /**
     * for update trigger
     */
    CronTrigger setTrigger(String cron);
}
