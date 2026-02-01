package com.juniorg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GlobalCorsConfig {

/*  Commenting this out so we have have more security updated on 26 Oct 25

    @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // allow all endpoints
//                        .allowedOrigins(
//                                "http://localhost:5173",
//                                "http://juniorgpreschool.s3-website.ap-south-1.amazonaws.com",
//                                "https://juniorgpreschool.s3-website.ap-south-1.amazonaws.com"
//                        )
                        .allowedOriginPatterns("*") // use this instead of allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }*/

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                                "http://localhost:5173",
                                "https://juniorg.site",
                                "http://juniorgpreschool.s3-website.ap-south-1.amazonaws.com",
                                "https://juniorgpreschool.s3-website.ap-south-1.amazonaws.com"
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("Content-Type", "Authorization")
                        .allowCredentials(true);
            }
        };
    }
}
