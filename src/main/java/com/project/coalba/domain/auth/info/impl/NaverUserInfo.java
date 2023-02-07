package com.project.coalba.domain.auth.info.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.coalba.domain.auth.info.impl.dto.NaverUserInfoDto;
import com.project.coalba.domain.auth.entity.enums.*;
import com.project.coalba.domain.auth.info.UserInfo;
import com.project.coalba.domain.auth.service.dto.SocialInfo;
import com.project.coalba.global.exception.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.*;

@Slf4j
public class NaverUserInfo implements UserInfo {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public SocialInfo getSocialInfo(String accessToken, String refreshToken) {
        NaverUserInfoDto naverUserInfoDto = getNaverUserInfoDto(accessToken);
        return SocialInfo.builder()
                .email(naverUserInfoDto.getResponse().getEmail())
                .name(naverUserInfoDto.getResponse().getName())
                .imageUrl(naverUserInfoDto.getResponse().getProfileImage())
                .provider(Provider.NAVER)
                .providerId(naverUserInfoDto.getResponse().getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private NaverUserInfoDto getNaverUserInfoDto(String token) {
        try {
            String reqURL = "https://openapi.naver.com/v1/nid/me";
            URL url = new URL(reqURL);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Bearer " + token);
            return mapper.readValue(urlConnection.getInputStream(), NaverUserInfoDto.class);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        throw new BusinessException(ErrorCode.INVALID_SOCIAL_TOKEN);
    }
}
