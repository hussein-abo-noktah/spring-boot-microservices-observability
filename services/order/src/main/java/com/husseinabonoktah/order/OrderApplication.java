package com.husseinabonoktah.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OrderApplication {

	 static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}

}
