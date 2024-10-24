package com.example.Room_Exchange_System;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RoomExchangeSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomExchangeSystemApplication.class, args);
	}

}
