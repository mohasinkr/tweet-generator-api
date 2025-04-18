package com.mohasinkr.tweet_generator_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration for RestTemplate.
 */
@Configuration
public class RestTemplateConfig {

    /**
     * Creates a RestTemplate bean for making HTTP requests.
     * 
     * @return a new RestTemplate instance
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
