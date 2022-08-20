package com.shadow.config;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class XxlJobExecutorProperties {

    private String appname;
    private String address;
    private String ip;
    private int port;
    private String logpath;
    private int logretentiondays;

}
