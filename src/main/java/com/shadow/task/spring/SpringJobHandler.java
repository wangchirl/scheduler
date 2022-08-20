package com.shadow.task.spring;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SpringJobHandler {

    @Scheduled(cron = "0 1 * * * ?")
    public void spring1() {
        System.out.println("invoke spring1 job ...");
    }

    @Scheduled(cron = "0 1 * * * ?")
    public void spring2() {
        System.out.println("invoke spring2 job ...");
    }

}
