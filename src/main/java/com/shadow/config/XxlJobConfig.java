package com.shadow.config;

import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(XxlJobCoreProperties.class)
public class XxlJobConfig {

    @Bean
    public XxlJobExecutor xxlJobSimpleExecutor(XxlJobCoreProperties xxlJobCoreProperties) {
        XxlJobSpringExecutor executor = new XxlJobSpringExecutor();
        executor.setAdminAddresses(xxlJobCoreProperties.getAdminAddresses());
        executor.setAppname(xxlJobCoreProperties.getExecutor().getAppname());
        executor.setIp(xxlJobCoreProperties.getExecutor().getIp());
        executor.setPort(xxlJobCoreProperties.getExecutor().getPort());
        executor.setAccessToken(xxlJobCoreProperties.getAccessToken());
        executor.setLogPath(xxlJobCoreProperties.getExecutor().getLogpath());
        executor.setLogRetentionDays(xxlJobCoreProperties.getExecutor().getLogretentiondays());
        return executor;
    }

}
