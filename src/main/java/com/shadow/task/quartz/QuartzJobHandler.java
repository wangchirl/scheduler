package com.shadow.task.quartz;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Slf4j
public class QuartzJobHandler extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("invoke quartz job .... PARAMS = " + JSON.toJSONString(jobExecutionContext.getMergedJobDataMap()));
    }
}
