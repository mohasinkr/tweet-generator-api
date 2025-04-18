package com.mohasinkr.tweet_generator_api.controller;

import com.mohasinkr.tweet_generator_api.dto.TweetResponse;
import com.mohasinkr.tweet_generator_api.service.GroqApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class TweetController {

    private final GroqApiService groqApiService;

    @Autowired
    public TweetController(GroqApiService groqApiService) {
        this.groqApiService = groqApiService;
    }

    /**
     * Endpoint to generate a tweet and return a simple JSON response with the tweet content.
     *
     * @param category The category to generate a tweet about
     * @return A JSON response with the tweet content
     */
    @PostMapping("/tweet")
    public ResponseEntity<TweetResponse> generateTweetController(@RequestBody String category) {
        return groqApiService.generateTweetFromPrompt(category);
    }
}
