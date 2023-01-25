package com.project.coalba.domain.auth.entity;

import com.project.coalba.global.audit.BaseTimeEntity;
import com.project.coalba.global.utils.EncryptionUtil;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "refresh_token")
@Entity
public class UserRefreshToken extends BaseTimeEntity {

    @Id
    @Column(name = "refresh_token_id")
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "refresh_token_id", nullable = false)
    private User user; //FK이면서 PK

    @Column(nullable = false)
    private String token;

    @Builder
    public UserRefreshToken(User user, String token) {
        this.user = user;
        this.updateToken(token);
    }

    public void updateToken(String token) {
        if (token != null) {
            this.token = EncryptionUtil.encrypt(token);
        }
    }

    public String getToken() {
        return EncryptionUtil.decrypt(token);
    }
}
