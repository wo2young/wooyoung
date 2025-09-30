package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Boot1HelloApplication {

	public static void main(String[] args) {
		System.out.println("Hello World");
		SpringApplication.run(Boot1HelloApplication.class, args);
	}

}
