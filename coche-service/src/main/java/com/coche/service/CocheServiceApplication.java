package com.coche.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CocheServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CocheServiceApplication.class, args);
	}

}
