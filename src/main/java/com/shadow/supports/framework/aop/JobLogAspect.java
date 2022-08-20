package com.shadow.supports.framework.aop;

import com.shadow.supports.framework.support.JobLogHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
@Order(value = Integer.MIN_VALUE)
@Slf4j
public class JobLogAspect {

    @Pointcut("@annotation(com.shadow.supports.framework.aop.SimpleJob)")
    public void jobLogPointcut() {
        // job log
    }

    @Around(value = "jobLogPointcut()")
    public Object jobLogAround(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        // job type
        String jobType = "";
        boolean isXxl = false;
        Object result = null;
        XxlJob xxlJob = method.getAnnotation(XxlJob.class);
        SimpleJob simpleJob = method.getAnnotation(SimpleJob.class);
        if (xxlJob != null) {
            isXxl = true;
            jobType = xxlJob.value();
        }
        if (simpleJob != null) {
            isXxl = false;
            try {
                Method typeMethod = pjp.getTarget().getClass().getMethod("type");
                typeMethod.setAccessible(true);
                jobType = (String) typeMethod.invoke(pjp.getTarget(), null);
            } catch (Exception e) {
                // 没有 type 方法
            }
            if (!"".equals(simpleJob.value())) {
                jobType = simpleJob.value();
            }
            if ("".equals(jobType)) {
                JobLogHelper.exceptionLog(jobType, false, new RuntimeException("请指定 @SimpleJob#value"));
                return null;
            }
        }
        try {
            // before log
            JobLogHelper.beforeLog(jobType, isXxl);
            // invoke method
            result = pjp.proceed();
        } catch (Throwable t) {
            // exception log
            JobLogHelper.exceptionLog(jobType, isXxl, t);
        } finally {
            // after log
            JobLogHelper.afterLog(jobType, isXxl);
        }
        return result;
    }
}
