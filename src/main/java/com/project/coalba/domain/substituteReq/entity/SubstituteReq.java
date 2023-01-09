package com.project.coalba.domain.substituteReq.entity;

import com.project.coalba.domain.profile.entity.Boss;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.substituteReq.entity.enums.SubstituteReqStatus;
import com.project.coalba.global.audit.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SubstituteReq extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "substitute_req_id")
    private Long id;

    @Column(nullable = false, length = 150)
    private String reqMessage;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubstituteReqStatus status = SubstituteReqStatus.WAITING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private Staff receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private Staff sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boss_id", nullable = false)
    private Boss boss;

    public Boolean isWaiting() {
        return status == SubstituteReqStatus.WAITING;
    }

    public void cancel() {
        status = SubstituteReqStatus.CANCELLATION;
    }

    public void accept() {
        status = SubstituteReqStatus.ACCEPTANCE;
    }

    public void refuse() {
        status = SubstituteReqStatus.REFUSAL;
    }

    public void approve() {
        status = SubstituteReqStatus.APPROVAL;
    }

    public void disapprove() {
        status = SubstituteReqStatus.DISAPPROVAL;
    }
}
