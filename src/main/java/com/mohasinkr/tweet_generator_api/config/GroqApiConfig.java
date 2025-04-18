package com.mohasinkr.tweet_generator_api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * Configuration properties for the Groq API.
 */
@Configuration
@ConfigurationProperties(prefix = "groq.api")
@Data
public class GroqApiConfig {
    
    /**
     * The API key for authenticating with the Groq API.
     */
    private String key;
    
    /**
     * The URL for the Groq API endpoint.
     */
    private String url;
}
