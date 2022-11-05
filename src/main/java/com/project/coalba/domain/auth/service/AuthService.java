package com.project.coalba.domain.auth.service;

import com.project.coalba.domain.auth.dto.response.AuthResponse;
import com.project.coalba.domain.auth.entity.User;
import com.project.coalba.domain.auth.entity.UserRefreshToken;
import com.project.coalba.domain.auth.entity.enums.Provider;
import com.project.coalba.domain.auth.entity.enums.Role;
import com.project.coalba.domain.auth.info.UserInfo;
import com.project.coalba.domain.auth.info.UserInfoFactory;
import com.project.coalba.domain.auth.repository.UserRefreshTokenRepository;
import com.project.coalba.domain.auth.repository.UserRepository;
import com.project.coalba.domain.auth.token.AuthTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthTokenManager tokenManager;
    private final UserRepository userRepository;
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    @Transactional
    public AuthResponse login(Provider provider, String token, Role role) {
        User socialUser = getSocialUser(provider, token, role), loginUser;
        String providerId = socialUser.getProviderId();
        boolean isNewUser;
        if (isSubscribedUser(providerId)) {
            loginUser = updateUser(socialUser);
            isNewUser = false;
        }
        else {
             loginUser = joinUser(socialUser);
             isNewUser = true;
        }

        Long userId = loginUser.getId();
        String accessToken = tokenManager.createAccessToken(providerId, userId);
        String refreshToken = tokenManager.createRefreshToken();
        if (hasUserRefreshToken(userId)) updateUserRefreshToken(userId, refreshToken);
        else saveUserRefreshToken(loginUser, refreshToken);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isNewUser(isNewUser)
                .build();
    }

    @Transactional
    public Object reissue() {
        //TODO: refresh token 유효성 검사 후 token 재발행
        return new Object();
    }

    private User getSocialUser(Provider provider, String token, Role role) {
        UserInfo userInfo = UserInfoFactory.getUserInfo(provider);
        return userInfo.getUser(token, role);
    }

    private boolean isSubscribedUser(String providerId) {
        return userRepository.findByProviderId(providerId).isPresent(); //영속성 컨텍스트에서 관리X
    }

    private User updateUser(User socialUser) {
        User loginUser = userRepository.findByProviderId(socialUser.getProviderId()).get();
        loginUser.updateSocialInfo(socialUser);
        return loginUser;
    }

    private User joinUser(User socialUser) {
        return userRepository.save(socialUser);
    }

    private boolean hasUserRefreshToken(Long userId) {
        return userRefreshTokenRepository.findById(userId).isPresent();
    }

    private void updateUserRefreshToken(Long userId, String token) {
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findById(userId).get();
        userRefreshToken.updateToken(token);
    }

    private void saveUserRefreshToken(User loginUser, String token) {
        userRefreshTokenRepository.save(
                UserRefreshToken.builder()
                        .user(loginUser)
                        .token(token)
                        .build());
    }
}
