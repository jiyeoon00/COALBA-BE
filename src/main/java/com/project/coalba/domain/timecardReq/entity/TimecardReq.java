package com.project.coalba.domain.timecardReq.entity;

import com.project.coalba.domain.profile.entity.Boss;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.timecardReq.entity.enums.TimecardReqStatus;
import com.project.coalba.global.audit.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class TimecardReq extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timecard_req_id")
    private Long id;

    @Column(nullable = false)
    private LocalTime reqStartTime;

    @Column(nullable = false)
    private LocalTime reqEndTime;

    @Column(nullable = false, length = 150)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TimecardReqStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boss_id")
    private Boss boss;
}
