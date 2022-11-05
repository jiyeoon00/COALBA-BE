package com.project.coalba.domain.auth.entity;

import com.project.coalba.global.audit.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "refresh_token")
@Entity
public class UserRefreshToken extends BaseTimeEntity {

    @Id
    @Column(name = "refresh_token_id")
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "refresh_token_id")
    private User user; //FK이면서 PK

    private String token;

    public void updateToken(String token) {
        this.token = token;
    }
}
