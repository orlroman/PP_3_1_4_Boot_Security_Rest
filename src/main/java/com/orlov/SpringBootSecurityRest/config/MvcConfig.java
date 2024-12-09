package com.orlov.SpringBootSecurityRest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("user/index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/user").setViewName("user/index");
        registry.addViewController("/admin").setViewName("admin/index");
        registry.addViewController("/admin/new").setViewName("admin/new");
    }
    
}
