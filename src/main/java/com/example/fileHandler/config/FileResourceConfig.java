package com.example.fileHandler.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Configuration
public class FileResourceConfig {
    @Autowired
    private ResourcePatternResolver resolver;

    @Bean
    public Resource[] csvResources() {
        try {
            return resolver.getResources("classpath:/static/csv/*.csv");
        } catch (IOException e){
            throw new IllegalStateException("csv file path error: " + e.getMessage());
        }
    }
}
