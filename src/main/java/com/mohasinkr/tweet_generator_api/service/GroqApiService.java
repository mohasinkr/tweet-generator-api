package com.mohasinkr.tweet_generator_api.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mohasinkr.tweet_generator_api.config.GroqApiConfig;
import com.mohasinkr.tweet_generator_api.dto.TweetResponse;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service for interacting with the Groq API for tweet generation.
 */
@Service
@Slf4j
public class GroqApiService {

    private final GroqApiConfig groqApiConfig;
    private final RestTemplate restTemplate;

    @Value("${groq.api.url}")
    private String groqUrl;

    @Autowired
    public GroqApiService(GroqApiConfig groqApiConfig) {
        this.groqApiConfig = groqApiConfig;
        this.restTemplate = new RestTemplate();
    }

    /**
     * Creates HTTP headers with the Groq API key for authentication.
     *
     * @return HttpHeaders with the Authorization header set
     */
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + groqApiConfig.getKey());
        return headers;
    }

    /**
     * Validates that the API key is configured.
     *
     * @throws IllegalStateException if the API key is not configured
     */
    public void validateApiKeyConfigured() {
        if (groqApiConfig.getKey() == null || groqApiConfig.getKey().isEmpty()) {
            log.error("Groq API key is not configured");
            throw new IllegalStateException("Groq API key is not configured. Please set the GROQ_API_KEY environment variable.");
        }
        log.info("Groq API key is configured");

        // Check if the API key is from a secure source
        checkApiKeySource();
    }

    /**
     * Checks if the API key is being loaded from a secure source.
     * Warns if the API key appears to be hardcoded in a properties file.
     */
    private void checkApiKeySource() {
        String apiKey = groqApiConfig.getKey();

        // Check if the API key is the placeholder from the template
        if ("your_groq_api_key_here".equals(apiKey)) {
            log.warn("You are using the placeholder API key. Please replace it with your actual Groq API key.");
            return;
        }

        // Check if the API key is from an environment variable
        String envApiKey = System.getenv("GROQ_API_KEY");
        if (envApiKey != null && envApiKey.equals(apiKey)) {
            log.info("API key is properly loaded from environment variable");
            return;
        }

        // If we get here, the API key might be hardcoded in a properties file
        log.warn("API key appears to be hardcoded in a properties file. For security, consider using environment variables or .env file instead.");
    }

    // Additional methods for tweet generation will be added here

    /**
     * Generates a tweet from a given category and returns a simple JSON response with the tweet content.
     *
     * @param category The category to generate a tweet about
     * @return A JSON response with the tweet content
     */
    public ResponseEntity<TweetResponse> generateTweetFromPrompt(String category) {
        String apiKey = groqApiConfig.getKey();
        String prompt = "Generate a tweet about " + category + ". Don't give much options, share a ready to use tweet.";
        log.info("Prompt: {}", prompt);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> message = new HashMap<>();
        message.put("role", "system");
        message.put("content", prompt);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gemma2-9b-it");
        body.put("messages", List.of(message));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            // Define a private class to parse the Groq API response
            ResponseEntity<GroqResponse> response = restTemplate.exchange(
                groqUrl,
                HttpMethod.POST,
                request,
                GroqResponse.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                GroqResponse groqResponse = response.getBody();
                if (groqResponse.getChoices() != null && !groqResponse.getChoices().isEmpty()) {
                    String tweetContent = groqResponse.getChoices().getFirst().getMessage().getContent();
                    log.info("Generated tweet: {}", tweetContent);
                    return ResponseEntity.ok(new TweetResponse(tweetContent));
                }
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new TweetResponse("No response from GROQ"));
        } catch (Exception e) {
            log.error("Error calling GROQ API: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new TweetResponse("Error generating tweet"));
        }
    }

    // Inner classes for type-safe response handling
    @Setter
    @Getter
    private static class GroqResponse {
        private List<Choice> choices;
    }

    @Setter
    @Getter
    private static class Choice {
        private Message message;
    }

    @Setter
    @Getter
    private static class Message {
        private String content;
    }
}
