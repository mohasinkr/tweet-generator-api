package com.mohasinkr.tweet_generator_api.dto;

public class TweetResponse {
    private String tweet;

    public TweetResponse(String tweet){
        this.tweet = tweet;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }
}
