package com.shadow.supports.framework;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface ScheduleService {

    /**
     * 请求参数
     */
    ThreadLocal<String> JOB_PARAMETERS_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * lambda 表达式支持
     * 消费者 = 支持参数，无返回值
     */
    void consumer(Consumer<Object> consumer);

    /**
     * lambda 表达式支持
     * 提供者 = 无参数，支持返回值
     */
    <T> T supplier(Supplier<T> supplier);

    /**
     * lambda 表达式支持
     * 函数 = 支持参数，支持返回值
     */
    <T> T function(Function<Object, T> function);



    Integer test(String params);

    Integer testDis(String params);
}
