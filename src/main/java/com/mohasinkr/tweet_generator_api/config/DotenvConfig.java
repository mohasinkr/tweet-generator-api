package com.mohasinkr.tweet_generator_api.config;

import java.io.File;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

/**
 * Configuration for loading environment variables from .env files.
 * This class loads variables from a .env file if it exists, making them
 * available as system environment variables.
 */
@Configuration
@Slf4j
public class DotenvConfig {

    /**
     * Loads environment variables from .env file if it exists.
     * Checks for .env file in the root directory and loads it if found.
     */
    @PostConstruct
    public void loadDotEnv() {
        try {
            // Check if .env file exists in the root directory
            File envFile = new File(".env");
            
            if (envFile.exists()) {
                log.info("Loading environment variables from .env file");
                
                // Load .env file
                Dotenv dotenv = Dotenv.configure()
                        .directory(".")
                        .ignoreIfMalformed()
                        .ignoreIfMissing()
                        .load();
                
                // Set system properties for Spring to use
                dotenv.entries().forEach(entry -> {
                    if (System.getProperty(entry.getKey()) == null && 
                        System.getenv(entry.getKey()) == null) {
                        System.setProperty(entry.getKey(), entry.getValue());
                    }
                });
                
                log.info("Environment variables loaded successfully from .env file");
            } else {
                log.info("No .env file found in the root directory");
            }
        } catch (Exception e) {
            log.warn("Failed to load .env file: {}", e.getMessage());
        }
    }
}
