package com.project.coalba.domain.profile.entity;

import com.project.coalba.domain.auth.entity.User;
import com.project.coalba.global.audit.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Staff extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String realName;

    @Column(nullable = false, length = 11)
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDate birthDate;

    private String imageUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    public void update(String realName, String phoneNumber, LocalDate birthDate, String imageUrl) {
        this.realName = realName;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.imageUrl = imageUrl;
    }
}
