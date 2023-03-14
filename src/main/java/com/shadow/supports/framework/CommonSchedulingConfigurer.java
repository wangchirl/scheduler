package com.shadow.supports.framework;

import com.shadow.supports.helper.ScheduleInfoVO;
import com.shadow.supports.helper.ScheduleTaskInfoEnum;
import com.shadow.supports.helper.ScheduleVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Component
public class CommonSchedulingConfigurer<T> implements SchedulingConfigurer, ApplicationContextAware, InitializingBean, DisposableBean {

    public CommonSchedulingConfigurer(Collection<ICronTriggerTask<T>> cronTriggerTasks) {
        this.cronTriggerTasks = cronTriggerTasks;
    }

    /**
     * collections for futures of running tasks
     */
    private final Map<String, ScheduledFuture<?>> futureMap = new ConcurrentHashMap<>(16);

    /**
     * collections for schedule task, for restart / update task
     */
    private final Map<String, ICronTriggerTask<T>> taskMap = new ConcurrentHashMap<>(16);

    /**
     * CronTriggerTask collection
     */
    private Collection<ICronTriggerTask<T>> cronTriggerTasks;

    /**
     * Spring IOC
     */
    private ApplicationContext applicationContext;

    /**
     * TaskScheduler ->  ThreadPoolTaskScheduler
     */
    private TaskScheduler taskScheduler;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskScheduler);
        if (null != cronTriggerTasks && !cronTriggerTasks.isEmpty()) {
            for (ICronTriggerTask<T> triggerTask : cronTriggerTasks) {
                ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(triggerTask.getTask(), triggerTask.getTrigger());
                futureMap.put(triggerTask.type(), scheduledFuture);
                taskMap.put(triggerTask.type(), triggerTask);
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.taskScheduler = this.applicationContext.getBean(TaskScheduler.class);
    }

    /**
     * get task information
     *
     * @return information
     */
    public ScheduleVO<T> get(String taskKey) {
        ScheduleVO<T> scheduleVO = new ScheduleVO<>();
        if (StringUtils.isEmpty(taskKey)) {
            final Set<String> types = futureMap.keySet();
            scheduleVO.setActiveTaskKeys(types);
            List<ScheduleInfoVO<T>> taskInfos = new ArrayList<>();
            for (Map.Entry<String, ICronTriggerTask<T>> taskEntry : taskMap.entrySet()) {
                ScheduleInfoVO<T> scheduleInfoVO = new ScheduleInfoVO<>();
                scheduleInfoVO.setTaskName(ScheduleTaskInfoEnum.getScheduleTaskNameByTaskKey(taskEntry.getKey()))
                        .setTaskKey(taskEntry.getKey())
                        .setResult(taskEntry.getValue().getResult())
                        .setTaskCron(taskEntry.getValue().getTrigger().getExpression());
                taskInfos.add(scheduleInfoVO);
            }
            scheduleVO.setTasks(taskInfos);
        } else {
            final ICronTriggerTask<T> triggerTask = taskMap.get(taskKey);
            if (triggerTask != null) {
                ScheduleInfoVO<T> scheduleInfoVO = new ScheduleInfoVO<>();
                scheduleInfoVO.setTaskName(ScheduleTaskInfoEnum.getScheduleTaskNameByTaskKey(taskKey))
                        .setTaskKey(taskKey)
                        .setResult(triggerTask.getResult())
                        .setTaskCron(triggerTask.getTrigger().getExpression());
                scheduleVO.setTasks(Collections.singletonList(scheduleInfoVO));
                scheduleVO.setActiveTaskKeys(futureMap.containsKey(taskKey) ? Collections.singleton(taskKey) : null);
            }
        }
        return scheduleVO;
    }

    /**
     * add a new task
     *
     * @param triggerTask task
     * @return boolean
     */
    public Boolean add(ICronTriggerTask<T> triggerTask) {
        String type = triggerTask.type(), cron = triggerTask.getTrigger().getExpression();
        if (futureMap.containsKey(type)) {
            log.warn("task type named {} already exists ", type);
            return false;
        }
        ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(triggerTask.getTask(), triggerTask.getTrigger());
        futureMap.put(triggerTask.type(), scheduledFuture);
        taskMap.put(triggerTask.type(), triggerTask);
        log.info("add task successful for type {}  cron {} ", type, cron);
        return true;
    }

    /**
     * update schedule task
     *
     * @param type task type
     * @param cron cron expression
     * @return boolean
     */
    public Boolean update(final String type, final String cron) {
        if (!futureMap.containsKey(type)) {
            log.warn("task type named {} doesn't exist", type);
            return false;
        }
        cancel(type);
        ICronTriggerTask<T> triggerTask = taskMap.get(type);
        CronTrigger oldCronTrigger = triggerTask.getTrigger(), newCronTrigger = triggerTask.setTrigger(cron);
        ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(triggerTask.getTask(), newCronTrigger);
        futureMap.put(type, scheduledFuture);
        log.info("update task successful for type {} old cron {} updated to new cron {}", type, oldCronTrigger, newCronTrigger);
        return true;
    }

    /**
     * cancel task
     *
     * @param type task type
     * @return boolean
     */
    public Boolean cancel(String type) {
        if (!futureMap.containsKey(type)) {
            log.warn("task type named {} doesn't exists", type);
            return false;
        }
        ScheduledFuture<?> scheduledFuture = futureMap.get(type);
        if (null != scheduledFuture) {
            scheduledFuture.cancel(true);
        }
        futureMap.remove(type);
        log.info("cancel task successful for type {} ", type);
        return true;
    }

    /**
     * restart task
     *
     * @param type task type
     * @return boolean
     */
    public Boolean restart(String type) {
        ICronTriggerTask<T> triggerTask = taskMap.get(type);
        if (null == triggerTask) {
            log.warn("task type named {} doesn't exists ", type);
            return false;
        }
        cancel(type);
        ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(triggerTask.getTask(), triggerTask.getTrigger());
        futureMap.put(type, scheduledFuture);
        log.info("restart task successful for type {} ", type);
        return true;
    }

    @Override
    public void destroy() throws Exception {
        for (ScheduledFuture<?> scheduledFuture : futureMap.values()) {
            if (null != scheduledFuture) {
                scheduledFuture.cancel(true);
            }
        }
        futureMap.clear();
        taskMap.clear();
    }
}
