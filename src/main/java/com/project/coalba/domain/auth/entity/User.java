package com.project.coalba.domain.auth.entity;

import com.project.coalba.domain.auth.entity.enums.*;
import com.project.coalba.global.audit.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 50)
    private String name;

    private String imageUrl;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.STAFF;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Provider provider;

    @Column(nullable = false)
    private String providerId;

    @Column(nullable = false)
    private String accessToken;

    private String refreshToken;

    public User updateSocialInfo(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.imageUrl = user.getImageUrl();
        this.providerId = user.getProviderId();
        return this;
    }

    public void updateSocialToken(String accessToken, String refreshToken) {
        //TODO: 각 토큰 모두 암호화하여 저장
        this.accessToken = accessToken;
        if (refreshToken != null) {
            this.refreshToken = refreshToken;
        }
    }
}
