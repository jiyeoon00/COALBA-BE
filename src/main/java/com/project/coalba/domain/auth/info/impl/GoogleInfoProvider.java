package com.project.coalba.domain.auth.info.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.coalba.domain.auth.entity.enums.*;
import com.project.coalba.domain.auth.info.*;
import com.project.coalba.global.exception.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;

@Slf4j
public class GoogleInfoProvider implements SocialInfoProvider {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public SocialInfo getSocialInfo(String socialAccessToken, String socialRefreshToken) {
        GoogleInfo googleInfo = getGoogleInfo(socialAccessToken);
        return SocialInfo.builder()
                .email(googleInfo.getEmail())
                .name(googleInfo.getName())
                .imageUrl(googleInfo.getPicture())
                .provider(Provider.GOOGLE)
                .providerId(googleInfo.getSub())
                .socialAccessToken(socialAccessToken)
                .socialRefreshToken(socialRefreshToken)
                .build();
    }

    private GoogleInfo getGoogleInfo(String token) {
        try {
            String reqURL = "https://www.googleapis.com/oauth2/v3/userinfo?access_token=" + token;
            URL url = new URL(reqURL);
            return mapper.readValue(url, GoogleInfo.class);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        throw new BusinessException(ErrorCode.INVALID_SOCIAL_TOKEN);
    }
}
