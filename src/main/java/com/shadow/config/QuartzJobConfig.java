package com.shadow.config;

import com.shadow.task.quartz.QuartzJobHandler;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzJobConfig {

    @Value("${quartz.job.test.cron:0 1 * * * ?}")

    @Bean
    public JobDetail quartzJob() {
        return JobBuilder.newJob(QuartzJobHandler.class)
                .withIdentity("quartzJob")
                .usingJobData("msg", "Hello Shadow")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger quartzTriggerKey() {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 1 * * * ?");
        return TriggerBuilder.newTrigger()
                .forJob(quartzJob())
                .withIdentity("quartzTrigger")
                .withSchedule(cronScheduleBuilder)
                .build();
    }
}
