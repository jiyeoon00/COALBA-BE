package com.project.coalba.global.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class GoogleProperties {
    private String clientId;
    private String clientSecret;
    private String tokenUri;

    private GoogleProperties(@Value("${clientId}") String clientId, @Value("${clientSecret}") String clientSecret, @Value("${tokenUri}") String tokenUri) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.tokenUri = tokenUri;
    }
}
