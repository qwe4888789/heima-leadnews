package com.heima.wemedia.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 自媒体网关
 */
@SpringBootApplication
@EnableDiscoveryClient //服务注册
public class WemediaGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(WemediaGatewayApplication.class,args);
    }
}
