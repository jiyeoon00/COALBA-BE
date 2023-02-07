package com.project.coalba.domain.auth.info.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.coalba.domain.auth.entity.enums.*;
import com.project.coalba.domain.auth.info.*;
import com.project.coalba.global.exception.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.*;

@Slf4j
public class NaverInfoProvider implements SocialInfoProvider {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public SocialInfo getSocialInfo(String accessToken, String refreshToken) {
        NaverInfo naverInfo = getNaverInfo(accessToken);
        return SocialInfo.builder()
                .email(naverInfo.getResponse().getEmail())
                .name(naverInfo.getResponse().getName())
                .imageUrl(naverInfo.getResponse().getProfileImage())
                .provider(Provider.NAVER)
                .providerId(naverInfo.getResponse().getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private NaverInfo getNaverInfo(String token) {
        try {
            String reqURL = "https://openapi.naver.com/v1/nid/me";
            URL url = new URL(reqURL);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Bearer " + token);
            return mapper.readValue(urlConnection.getInputStream(), NaverInfo.class);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        throw new BusinessException(ErrorCode.INVALID_SOCIAL_TOKEN);
    }
}
