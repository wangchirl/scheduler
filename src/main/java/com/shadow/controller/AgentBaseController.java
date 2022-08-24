package com.shadow.controller;

import com.shadow.supports.framework.ScheduleService;
import org.springframework.web.bind.annotation.*;

/**
 * agent base controller
 */
@RestController
@RequestMapping("/api/system")
public class AgentBaseController {

    @RequestMapping("/test")
    public String test(@RequestParam(required = false) String params) {
        try {
            ScheduleService.JOB_PARAMETERS_THREAD_LOCAL.set(params);
            System.out.println("test ..." + ScheduleService.JOB_PARAMETERS_THREAD_LOCAL.get());
        } finally {
            ScheduleService.JOB_PARAMETERS_THREAD_LOCAL.remove();
        }
        return "test";
    }
}
