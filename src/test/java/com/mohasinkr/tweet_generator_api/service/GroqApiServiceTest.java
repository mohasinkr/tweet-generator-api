package com.mohasinkr.tweet_generator_api.service;

import com.mohasinkr.tweet_generator_api.config.GroqApiConfig;
import com.mohasinkr.tweet_generator_api.dto.TweetResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class GroqApiServiceTest {

    @Mock
    private GroqApiConfig groqApiConfig;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GroqApiService groqApiService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(groqApiService, "groqUrl", "https://api.groq.com/openai/v1/chat/completions");
    }

    @Test
    public void testGenerateTweetFromPrompt() {
        // Mock the API key
        when(groqApiConfig.getKey()).thenReturn("test-api-key");

        // Create a mock Groq API response using the internal GroqResponse class
        GroqApiService.GroqResponse mockGroqResponse = new GroqApiService.GroqResponse();
        List<GroqApiService.Choice> choices = new ArrayList<>();
        GroqApiService.Choice choice = new GroqApiService.Choice();
        GroqApiService.Message message = new GroqApiService.Message();
        message.setContent("This is a test tweet about technology #tech #innovation");
        choice.setMessage(message);
        choices.add(choice);
        mockGroqResponse.setChoices(choices);

        // Mock the RestTemplate response
        ResponseEntity<GroqApiService.GroqResponse> responseEntity = new ResponseEntity<>(mockGroqResponse, HttpStatus.OK);
        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(GroqApiService.GroqResponse.class)
        )).thenReturn(responseEntity);

        // Call the service method
        ResponseEntity<TweetResponse> result = groqApiService.generateTweetFromPrompt("technology");

        // Verify the result
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("This is a test tweet about technology #tech #innovation", result.getBody().getTweet());
    }
}
