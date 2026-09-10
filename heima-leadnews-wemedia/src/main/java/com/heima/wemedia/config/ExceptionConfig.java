package com.heima.wemedia.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 让全局异常生效
 */
@Configuration
@ComponentScan("com.heima.common.exception")
public class ExceptionConfig {
}
