package com.shadow.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * agent base controller
 */
@RestController
@RequestMapping("/api/system")
public class AgentBaseController {

    @RequestMapping("/test")
    public void test() {
        System.out.println("hello");
    }

}
