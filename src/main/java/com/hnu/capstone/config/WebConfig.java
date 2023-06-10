package com.hnu.capstone.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/post_upload/**")
                .addResourceLocations("file:C:\\Users\\ygy01\\IdeaProjects\\capstone\\src\\main/resources/static/post_upload/");
    }
}