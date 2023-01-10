package com.project.coalba.domain.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
}
