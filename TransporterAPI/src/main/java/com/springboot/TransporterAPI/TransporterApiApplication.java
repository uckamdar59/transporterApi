package com.springboot.TransporterAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class TransporterApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransporterApiApplication.class, args);
		Long a = 9999999999L;
		System.out.println(a);
	}

}
