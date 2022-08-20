package com.shadow.supports.framework.support;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class ScheduleResult<T> implements Serializable {

    /**
     * 最近一次执行结果
     */
    private transient T result;

    /**
     * 最近一次执行开始时间
     */
    private LocalDateTime lastStartTime;

    /**
     * 最近一次执行结束时间
     */
    private LocalDateTime lastEndTime;

}
