package com.project.coalba.domain.auth.entity;

import com.project.coalba.global.audit.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RefreshToken extends BaseTimeEntity {

    @Id
    @Column(name = "token_key")
    private Long key;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "token_key")
    private User user; //FK이면서 PK

    @Column(name = "token_value")
    private String value;

    public void updateValue(String value) {
        this.value = value;
    }
}
