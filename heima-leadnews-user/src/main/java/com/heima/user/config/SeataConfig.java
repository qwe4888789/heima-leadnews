package com.heima.user.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 扫描Seata的数据源配置类
 */
@Configuration
@ComponentScan("com.heima.seata.config")
public class SeataConfig {
}
