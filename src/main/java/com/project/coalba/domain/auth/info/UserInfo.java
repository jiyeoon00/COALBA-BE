package com.project.coalba.domain.auth.info;

import com.project.coalba.domain.auth.service.dto.SocialInfo;

public interface UserInfo {
    SocialInfo getSocialInfo(String accessToken, String refreshToken);
}
