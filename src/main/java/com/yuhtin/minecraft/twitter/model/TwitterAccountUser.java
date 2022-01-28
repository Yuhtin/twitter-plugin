package com.yuhtin.minecraft.twitter.model;

import lombok.Builder;
import lombok.Data;
import lombok.val;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import javax.annotation.Nullable;
import java.util.UUID;

@Builder
@Data
public class TwitterAccountUser {

    private final UUID uniqueId;

    private String token;
    private String tokenSecret;
    private long userID;

    @Nullable
    public AccessToken getAccessToken() {
        if (token == null || tokenSecret == null || userID == 0) return null;
        return new AccessToken(token, tokenSecret, userID);
    }

    @Nullable
    public Twitter getUserTwitter() {
        val accessToken = getAccessToken();
        if (accessToken == null) return null;

        val twitter = new TwitterFactory().getInstance();
        twitter.setOAuthAccessToken(accessToken);

        return twitter;
    }
}
