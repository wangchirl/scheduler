package com.shadow.supports.helper;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Set;

@Data
@Accessors(chain = true)
public class ScheduleVO<T> {

    private Set<String> activeTaskKeys;

    private List<ScheduleInfoVO<T>> tasks;

}
