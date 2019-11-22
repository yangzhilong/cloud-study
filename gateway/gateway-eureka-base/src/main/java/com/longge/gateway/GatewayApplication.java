package com.longge.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.longge.common.service.annotation.EnableOkHttpUtilsAutoConfiguration;

@SpringBootApplication
@EnableDiscoveryClient
@EnableOkHttpUtilsAutoConfiguration
@EnableFeignClients(basePackages = "com.longge.gateway.fegin")
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

}
