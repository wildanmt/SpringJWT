package com.ownproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication 
@EnableSwagger2
public class OwnprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(OwnprojectApplication.class, args);
	}
	

}
