package com.project.coalba.domain.auth.service;

import com.project.coalba.domain.auth.dto.response.OauthAccessTokenResponse;
import com.project.coalba.domain.auth.entity.User;
import com.project.coalba.domain.auth.repository.UserRepository;
import com.project.coalba.global.config.GoogleProperties;
import com.project.coalba.global.exception.BusinessException;
import com.project.coalba.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthSocialTokenService {
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final GoogleProperties googleProperties;

    @Transactional
    public String updateAccessToken(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if(findUser.isPresent()) {
            String updatedAccessToken = getUpdatedAccessToken(findUser.get().getSocialRefreshToken());
            findUser.get().updateSocialAccessToken(updatedAccessToken);
            return updatedAccessToken;
        } else {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }
    }

    private String getUpdatedAccessToken(String refreshToken) {
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> params = generateAccessTokenParams(refreshToken);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        return restTemplate.postForEntity(googleProperties.getTokenUri(), request, OauthAccessTokenResponse.class).getBody().getAccess_token();
    }

    private MultiValueMap<String, String> generateAccessTokenParams(final String refreshToken) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", googleProperties.getClientId());
        params.add("client_secret", googleProperties.getClientSecret());
        params.add("refresh_token", refreshToken);
        params.add("grant_type", "refresh_token");
        return params;
    }
}
