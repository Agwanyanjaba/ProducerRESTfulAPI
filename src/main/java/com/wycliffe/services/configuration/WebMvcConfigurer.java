package com.wycliffe.services.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

public class WebMvcConfigurer {

    @Bean
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/v1/accounts").allowedOrigins("http://www.wybosoftbank.com:8080");
            //registry.addMapping("/v1/accounts").allowedOrigins("http://localhost:3001");
            // registry.addMapping("/v1/auth").allowedOrigins("http://localhost:3001");
        }

}
