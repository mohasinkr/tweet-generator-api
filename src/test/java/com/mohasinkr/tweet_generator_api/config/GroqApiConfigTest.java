package com.mohasinkr.tweet_generator_api.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "groq.api.key=test-api-key",
    "groq.api.url=https://test-api-url.com"
})
public class GroqApiConfigTest {

    @Autowired
    private GroqApiConfig groqApiConfig;

    @Test
    public void testGroqApiConfigProperties() {
        assertNotNull(groqApiConfig);
        assertEquals("test-api-key", groqApiConfig.getKey());
        assertEquals("https://test-api-url.com", groqApiConfig.getUrl());
    }
}
