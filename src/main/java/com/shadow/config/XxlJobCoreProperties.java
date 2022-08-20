package com.shadow.config;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@Accessors(chain = true)
@ConfigurationProperties(prefix = "xxl.job")
public class XxlJobCoreProperties {

    private XxlJobExecutorProperties executor = new XxlJobExecutorProperties();

    private String accessToken;

    private String adminAddresses;

}
