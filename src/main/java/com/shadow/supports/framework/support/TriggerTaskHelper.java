package com.shadow.supports.framework.support;


import com.shadow.supports.framework.ICronTriggerTask;
import com.shadow.supports.helper.ScheduleTaskInfoEnum;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class TriggerTaskHelper<T> {

    /**
     * 支持无参/有参，无返回值任务
     */
    public ICronTriggerTask<T> generateTask(ScheduleTaskInfoEnum scheduleTaskInfoEnum, Consumer<Object> consumer) {
        return new CronTriggerSupport<T>() {
            @Override
            public String type() {
                return scheduleTaskInfoEnum.getTaskKey();
            }

            @Override
            public String getCronName() {
                return scheduleTaskInfoEnum.getCronName();
            }

            @Override
            public T call() throws Exception {
                getScheduleService().consumer(consumer);
                return null;
            }
        };
    }

    /**
     * 支持无参，有返回值任务
     */
    public ICronTriggerTask<T> generateTask(ScheduleTaskInfoEnum scheduleTaskInfoEnum, Supplier<T> supplier) {
        return new CronTriggerSupport<T>() {
            @Override
            public String type() {
                return scheduleTaskInfoEnum.getTaskKey();
            }

            @Override
            public String getCronName() {
                return scheduleTaskInfoEnum.getCronName();
            }

            @Override
            public T call() throws Exception {
                return getScheduleService().supplier(supplier);
            }
        };
    }

    /**
     * 支持有参，有返回值任务
     */
    public ICronTriggerTask<T> generateTask(ScheduleTaskInfoEnum scheduleTaskInfoEnum, Function<Object, T> function) {
        return new CronTriggerSupport<T>() {
            @Override
            public String type() {
                return scheduleTaskInfoEnum.getTaskKey();
            }

            @Override
            public String getCronName() {
                return scheduleTaskInfoEnum.getCronName();
            }

            @Override
            public T call() throws Exception {
                return getScheduleService().function(function);
            }
        };
    }


}
