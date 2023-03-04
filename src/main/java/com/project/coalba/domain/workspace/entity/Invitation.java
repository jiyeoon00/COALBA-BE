package com.project.coalba.domain.workspace.entity;

import com.project.coalba.global.audit.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Invitation extends BaseTimeEntity {
    private static final long VALID_PERIOD_MINUTES = 15L; //초대 유효 기간 (분)

    @Column(name = "invitation_id", length = 36)
    @Id @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Column(nullable = false)
    private LocalDateTime expirationDate;

    @Column(nullable = false)
    private Boolean expired;

    @Column(nullable = false)
    private String receiverEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", nullable = false)
    private Workspace workspace;

    public static Invitation invite(String receiverEmail, Workspace workspace) {
        return Invitation.builder()
                .expirationDate(LocalDateTime.now().plusMinutes(VALID_PERIOD_MINUTES))
                .expired(false)
                .receiverEmail(receiverEmail)
                .workspace(workspace)
                .build();
    }

    //초대 수락 또는 만료일 초과로 인한 초대장 만료
    public void expired() {
        expired = true;
    }
}
