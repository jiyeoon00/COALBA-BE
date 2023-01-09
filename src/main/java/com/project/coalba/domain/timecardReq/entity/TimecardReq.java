package com.project.coalba.domain.timecardReq.entity;

import com.project.coalba.domain.profile.entity.Boss;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.timecardReq.entity.enums.TimecardReqStatus;
import com.project.coalba.global.audit.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class TimecardReq extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timecard_req_id")
    private Long id;

    @Column(nullable = false)
    private LocalDateTime reqStartDateTime;

    @Column(nullable = false)
    private LocalDateTime reqEndDateTime;

    @Column(nullable = false, length = 150)
    private String reason;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TimecardReqStatus status = TimecardReqStatus.WAITING;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", unique = true, nullable = false)
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boss_id", nullable = false)
    private Boss boss;
}
