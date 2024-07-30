package com.projeto.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.projeto.api", "com.example.demo"})
public class PetApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(PetApplication.class, args);
	}

}
