package com.project.coalba.domain.auth.info;

public interface SocialInfoProvider {
    SocialInfo getSocialInfo(String accessToken, String refreshToken);
}
