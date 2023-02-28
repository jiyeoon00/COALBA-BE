package com.project.coalba.domain.notification.entity;

import com.project.coalba.domain.auth.entity.User;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @Column(nullable = false)
    private String deviceToken;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    public void updateDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
