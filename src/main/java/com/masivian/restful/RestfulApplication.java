package com.masivian.restful;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication (scanBasePackages = {"com.masivian.restful"})
public class RestfulApplication {
	public static void main(String[] args) {
		SpringApplication.run(RestfulApplication.class, args);
	}
}
