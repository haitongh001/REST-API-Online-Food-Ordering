package com.webService.onlineOrdering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class OnlineOrderingApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineOrderingApplication.class, args);
	}
}
