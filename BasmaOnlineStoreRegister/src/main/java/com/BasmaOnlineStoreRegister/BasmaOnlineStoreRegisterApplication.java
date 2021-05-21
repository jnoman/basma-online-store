package com.BasmaOnlineStoreRegister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class BasmaOnlineStoreRegisterApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasmaOnlineStoreRegisterApplication.class, args);
	}

}
