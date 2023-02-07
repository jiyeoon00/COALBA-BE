package com.project.coalba.domain.auth.entity;

import com.project.coalba.domain.auth.entity.enums.*;
import com.project.coalba.global.audit.BaseTimeEntity;
import com.project.coalba.global.utils.EncryptionUtil;
import lombok.*;

import javax.persistence.*;

@Getter
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Provider provider;

    @Column(nullable = false)
    private String providerId;

    @Column(nullable = false, length = 350)
    private String accessToken;

    @Column(nullable = false)
    private String refreshToken;

    @Builder
    public User(String email, String name, String imageUrl, Role role, Provider provider, String providerId, String accessToken, String refreshToken) {
        this.email = email;
        this.name = name;
        this.imageUrl = imageUrl;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.updateSocialToken(accessToken, refreshToken);
    }

    public void updateSocialInfo(String email, String name, String imageUrl, String providerId, String accessToken, String refreshToken) {
        this.email = email;
        this.name = name;
        this.imageUrl = imageUrl;
        this.providerId = providerId;
        this.updateSocialToken(accessToken, refreshToken);
    }

    public void updateSocialToken(String accessToken, String refreshToken) {
        this.accessToken = EncryptionUtil.encrypt(accessToken);
        if (refreshToken != null) {
            this.refreshToken = EncryptionUtil.encrypt(refreshToken);
        }
    }

    public void updateSocialAccessToken(String accessToken) {
        this.accessToken = EncryptionUtil.encrypt(accessToken);
    }

    public String getAccessToken() {
        return EncryptionUtil.decrypt(accessToken);
    }

    public String getRefreshToken() {
        return EncryptionUtil.decrypt(refreshToken);
    }
}
