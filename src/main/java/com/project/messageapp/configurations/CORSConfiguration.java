package com.project.messageapp.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // Apply CORS configuration to specific paths
                .allowedOrigins("http://localhost:4200")
                .allowedOrigins("http://172.16.4.29:8080")  // Allow requests from this origin without the trailing slash
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Allow these HTTP methods
                .allowedHeaders("Content-Type", "Authorization");  // Allow only necessary headers
    }
}