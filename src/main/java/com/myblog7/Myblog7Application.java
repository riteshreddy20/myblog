package com.myblog7;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

//@springbootapplication  is the starting point of execution in spring boot project.
//this annotation makes this also configuration class.
@SpringBootApplication
public class Myblog7Application {

	public static void main(String[] args) {
		SpringApplication.run(Myblog7Application.class, args);
	}
	@Bean
public ModelMapper modelMapper() {
return new ModelMapper();
}
}