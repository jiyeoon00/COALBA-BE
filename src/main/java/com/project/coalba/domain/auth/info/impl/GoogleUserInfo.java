package com.project.coalba.domain.auth.info.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.coalba.domain.auth.dto.GoogleUserInfoDto;
import com.project.coalba.domain.auth.entity.User;
import com.project.coalba.domain.auth.entity.enums.Provider;
import com.project.coalba.domain.auth.entity.enums.Role;
import com.project.coalba.domain.auth.info.UserInfo;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;

@Slf4j
public class GoogleUserInfo implements UserInfo {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public User getUser(String token, Role role) {
        GoogleUserInfoDto googleUserInfoDto = getGoogleUserInfoDto(token);
        return User.builder()
                .email(googleUserInfoDto.getEmail())
                .name(googleUserInfoDto.getName())
                .imageUrl(googleUserInfoDto.getPicture())
                .role(role)
                .provider(Provider.GOOGLE)
                .providerId(googleUserInfoDto.getSub())
                .build();
    }

    private GoogleUserInfoDto getGoogleUserInfoDto(String token) {
        try {
            String reqURL = "https://oauth2.googleapis.com/tokeninfo?id_token=" + token;
            URL url = new URL(reqURL);
            return mapper.readValue(url, GoogleUserInfoDto.class);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        throw new IllegalArgumentException("Invalid Token.");
    }
}
