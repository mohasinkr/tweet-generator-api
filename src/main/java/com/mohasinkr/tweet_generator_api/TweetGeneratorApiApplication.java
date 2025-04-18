package com.mohasinkr.tweet_generator_api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.mohasinkr.tweet_generator_api.service.GroqApiService;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class TweetGeneratorApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TweetGeneratorApiApplication.class, args);
	}

	/**
	 * Validates the Groq API configuration on startup.
	 *
	 * @param groqApiService the service to validate
	 * @return a CommandLineRunner that validates the API key
	 */
	@Bean
	public CommandLineRunner validateGroqApiConfig(GroqApiService groqApiService) {
		return args -> {
			try {
				groqApiService.validateApiKeyConfigured();
				log.info("Groq API configuration validated successfully");
			} catch (Exception e) {
				log.warn("Groq API configuration validation failed: {}", e.getMessage());
				log.warn("The application will start, but tweet generation functionality may not work properly");
			}
		};
	}
}
