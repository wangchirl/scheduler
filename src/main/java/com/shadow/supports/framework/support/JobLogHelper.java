package com.shadow.supports.framework.support;

import com.xxl.job.core.context.XxlJobHelper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JobLogHelper {
    private JobLogHelper(){}

    public static void beforeLog(String name, boolean isXxl) {
        if(isXxl) {
            XxlJobHelper.log(name + " start ...");
        }
        log.info("{} start ... " , name);
    }

    public static void exceptionLog(String name, boolean isXxl, Throwable t) {
        String error = t.getCause() == null ? t.getMessage() : t.getCause().getMessage();
        if(isXxl) {
            XxlJobHelper.log(name + " error ..." + error);
        }
        log.error("{} error {} ...", name, error);
    }

    public static void afterLog(String name, boolean isXxl) {
        if(isXxl) {
            XxlJobHelper.log(name + " end ...");
        }
        log.info("{} end ... ", name);
    }

}
