package com.longge.es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2WebFlux
public class ESWebFluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(ESWebFluxApplication.class, args);
	}
}
