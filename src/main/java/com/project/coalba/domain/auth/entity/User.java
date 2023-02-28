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
    private String socialAccessToken;

    @Column(nullable = false)
    private String socialRefreshToken;

    @Builder
    public User(String email, String name, String imageUrl, Role role, Provider provider, String providerId, String socialAccessToken, String socialRefreshToken) {
        this.email = email;
        this.name = name;
        this.imageUrl = imageUrl;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.updateSocialToken(socialAccessToken, socialRefreshToken);
    }

    public void updateSocialInfo(String email, String name, String imageUrl, String providerId, String socialAccessToken, String socialRefreshToken) {
        this.email = email;
        this.name = name;
        this.imageUrl = imageUrl;
        this.providerId = providerId;
        this.updateSocialToken(socialAccessToken, socialRefreshToken);
    }

    public void updateSocialToken(String socialAccessToken, String socialRefreshToken) {
        this.socialAccessToken = EncryptionUtil.encrypt(socialAccessToken);
        if (socialRefreshToken != null) {
            this.socialRefreshToken = EncryptionUtil.encrypt(socialRefreshToken);
        }
    }

    public void updateSocialAccessToken(String socialAccessToken) {
        this.socialAccessToken = EncryptionUtil.encrypt(socialAccessToken);
    }

    public String getSocialAccessToken() {
        return EncryptionUtil.decrypt(socialAccessToken);
    }

    public String getSocialRefreshToken() {
        return EncryptionUtil.decrypt(socialRefreshToken);
    }
}
