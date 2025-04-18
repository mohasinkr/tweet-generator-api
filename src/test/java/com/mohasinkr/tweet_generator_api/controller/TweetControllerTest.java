package com.mohasinkr.tweet_generator_api.controller;

import com.mohasinkr.tweet_generator_api.dto.TweetResponse;
import com.mohasinkr.tweet_generator_api.service.GroqApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TweetController.class)
public class TweetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroqApiService groqApiService;

    @Test
    public void testGenerateTweetController() throws Exception {
        // Create a mock tweet response
        TweetResponse mockResponse = new TweetResponse("This is a test tweet about technology #tech #innovation");

        // Mock the service response
        when(groqApiService.generateTweetFromPrompt(anyString()))
                .thenReturn(ResponseEntity.ok(mockResponse));

        // Perform the test
        mockMvc.perform(post("/api/v1/tweet")
                .contentType(MediaType.APPLICATION_JSON)
                .content("technology"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tweet").value("This is a test tweet about technology #tech #innovation"));
    }
}
