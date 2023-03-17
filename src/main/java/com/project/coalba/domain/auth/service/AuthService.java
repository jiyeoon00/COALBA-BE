package com.project.coalba.domain.auth.service;

import com.project.coalba.domain.auth.dto.response.*;
import com.project.coalba.domain.auth.entity.*;
import com.project.coalba.domain.auth.entity.enums.*;
import com.project.coalba.domain.auth.info.*;
import com.project.coalba.domain.auth.repository.*;
import com.project.coalba.domain.auth.token.AuthTokenManager;
import com.project.coalba.global.exception.*;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final AuthTokenManager tokenManager;
    private final UserRepository userRepository;
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private static final String USER_ID_KEY = "userId";
    private static final long REFRESH_TOKEN_CREATE_CRITERIA_SECONDS = 2 * 24 * 60 * 60;

    @Transactional
    public AuthResponse login(SocialInfo socialInfo, Role role) {
        Optional<User> userOptional = getSubscribedUser(socialInfo.getProviderId(), role);
        User loginUser = userOptional.map(user -> updateUser(user, socialInfo))
                .orElseGet(() -> joinUser(socialInfo, role));

        String accessToken = tokenManager.createAccessToken(loginUser.getProviderId(), loginUser.getId());
        String refreshToken = issueRefreshToken(getUserRefreshToken(loginUser.getId()).orElse(null));
        manageRefreshToken(loginUser, refreshToken);
        return new AuthResponse(accessToken, refreshToken, isNewUser(loginUser.getId()));
    }

    @Transactional
    public TokenResponse reissue(String accessToken, String refreshToken) {
        Claims claims = tokenManager.getExpiredTokenClaims(accessToken);
        if (claims == null) throw new BusinessException(ErrorCode.INVALID_ACCESS_TOKEN);
        String providerId = claims.getSubject();
        Long userId = claims.get(USER_ID_KEY, Long.class);

        UserRefreshToken userRefreshToken = getUserRefreshToken(userId).orElseThrow(() -> new BusinessException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));
        if (isValidRefreshToken(refreshToken, userRefreshToken.getToken())) {
            String newAccessToken = tokenManager.createAccessToken(providerId, userId);
            String newRefreshToken = issueRefreshToken(userRefreshToken);
            userRefreshToken.updateToken(newRefreshToken);
            return new TokenResponse(newAccessToken, newRefreshToken);
        }
        throw new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN);
    }

    private Optional<User> getSubscribedUser(String providerId, Role role) {
        return userRepository.findByProviderIdAndRole(providerId, role);
    }

    private User joinUser(SocialInfo socialInfo, Role role) {
        User user = socialInfo.toEntity(role);
        return userRepository.save(user);
    }

    private User updateUser(User user, SocialInfo socialInfo) {
        user.updateSocialInfo(socialInfo.getEmail(), socialInfo.getName(), socialInfo.getImageUrl(), socialInfo.getProviderId(),
                socialInfo.getSocialAccessToken(), socialInfo.getSocialRefreshToken());
        return user;
    }

    private Boolean isNewUser(Long userId) {
        return userRepository.existsByProfile(userId);
    }

    private void manageRefreshToken(User loginUser, String refreshToken) {
        Optional<UserRefreshToken> userRefreshTokenOptional = getUserRefreshToken(loginUser.getId());
        userRefreshTokenOptional.ifPresentOrElse(userRefreshToken -> userRefreshToken.updateToken(refreshToken),
                () -> userRefreshTokenRepository.save(UserRefreshToken.builder()
                            .user(loginUser).token(refreshToken).build()));
    }

    private Optional<UserRefreshToken> getUserRefreshToken(Long userId) {
        return userRefreshTokenRepository.findById(userId);
    }

    private boolean isValidRefreshToken(String refreshToken, String dbRefreshToken) {
        return tokenManager.validate(refreshToken) && refreshToken.equals(dbRefreshToken);
    }

    private String issueRefreshToken(UserRefreshToken userRefreshToken) {
        if (userRefreshToken == null || !tokenManager.validate(userRefreshToken.getToken()) ||
                tokenManager.getRemainedExpirySeconds(userRefreshToken.getToken()) <= REFRESH_TOKEN_CREATE_CRITERIA_SECONDS) {
            return tokenManager.createRefreshToken();
        }
        return userRefreshToken.getToken();
    }
}
