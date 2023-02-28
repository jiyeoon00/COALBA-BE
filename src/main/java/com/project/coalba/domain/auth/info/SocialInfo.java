package com.project.coalba.domain.auth.info;

import com.project.coalba.domain.auth.entity.User;
import com.project.coalba.domain.auth.entity.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
@AllArgsConstructor
public class SocialInfo {
    private String email;
    private String name;
    private String imageUrl;
    private Provider provider;
    private String providerId;
    private String socialAccessToken;
    private String socialRefreshToken;

    public User toEntity(Role role) {
        return User.builder()
                .email(email)
                .name(name)
                .imageUrl(imageUrl)
                .role(role)
                .provider(provider)
                .providerId(providerId)
                .socialAccessToken(socialAccessToken)
                .socialRefreshToken(socialRefreshToken)
                .build();
    }
}
