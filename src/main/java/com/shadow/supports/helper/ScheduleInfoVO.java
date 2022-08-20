package com.shadow.supports.helper;

import com.shadow.supports.framework.support.ScheduleResult;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ScheduleInfoVO<T> {

    private String taskName;

    private String taskKey;

    private String taskCron;

    private transient ScheduleResult<T> result;

}
