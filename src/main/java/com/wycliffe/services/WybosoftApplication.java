package com.wycliffe.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class WybosoftApplication {

	public static void main(String[] args) {
		SpringApplication.run(WybosoftApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/v1/accounts").allowedOrigins("http://localhost:3001");
               //                                                                                                                                                                                                                                                               registry.addMapping("/v1/auth").allowedOrigins("http://localhost:3001");
			}
		};


	}
}
