package com.example.fileHandler.config;

import com.example.fileHandler.utils.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
public class FileResourceConfig {
    @Autowired
    private ResourcePatternResolver resolver;

    @Bean
    public Resource[] csvResources() {
        try {
            return resolver.getResources(CommonConstant.csvResourceFileDirectory);
        } catch (FileNotFoundException e) {
            try {
                Path tmpFilePath = Files.createTempFile(CommonConstant.csvInitFilePrefix, ".csv");
                tmpFilePath.toFile().deleteOnExit();
                return resolver.getResources(tmpFilePath.toString());
            } catch (Exception ex) {
                throw new IllegalStateException("csv tmp file path error: " + ex.getMessage());
            }
        } catch (IOException e) {
            throw new IllegalStateException("csv file path error: " + e.getMessage());
        }
    }

    @Bean
    public Resource[] jpegResources() {
        try {
            return resolver.getResources(CommonConstant.jpegResourceFileDirectory);
        } catch (FileNotFoundException e) {
            try {
                Path tmpFilePath = Files.createTempFile(CommonConstant.jpegInitFilePrefix, ".jpg");
                tmpFilePath.toFile().deleteOnExit();
                return resolver.getResources(tmpFilePath.toString());
            } catch (Exception ex) {
                throw new IllegalStateException("jpeg tmp file path error: " + ex.getMessage());
            }
        } catch (IOException e) {
            throw new IllegalStateException("jpeg file path error: " + e.getMessage());
        }
    }
}
