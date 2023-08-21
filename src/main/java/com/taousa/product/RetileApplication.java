package com.taousa.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = " com.taousa.product.*")
public class RetileApplication {

	public static void main(String[] args) {

		SpringApplication.run(RetileApplication.class, args);

	}

}
