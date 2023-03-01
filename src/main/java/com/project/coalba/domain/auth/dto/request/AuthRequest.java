package com.project.coalba.domain.auth.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class AuthRequest {

    @NotBlank
    private String socialAccessToken;
    private String socialRefreshToken;
}
