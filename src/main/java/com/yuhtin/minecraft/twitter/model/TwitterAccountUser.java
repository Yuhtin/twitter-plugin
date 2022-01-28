package com.yuhtin.minecraft.twitter.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class TwitterAccountUser {

    private final UUID uniqueId;

    private String token;
    private String tokenSecret;
    private long userID;

}
