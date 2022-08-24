package com.shadow.task.quartz;

import com.alibaba.fastjson.JSON;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class QuartzJobHandler extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("invoke quartz job .... PARAMS = " + JSON.toJSONString(jobExecutionContext.getMergedJobDataMap()));
    }
}
