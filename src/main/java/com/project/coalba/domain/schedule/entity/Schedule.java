package com.project.coalba.domain.schedule.entity;

import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.schedule.entity.enums.ScheduleStatus;
import com.project.coalba.domain.substituteReq.entity.SubstituteReq;
import com.project.coalba.domain.timecardReq.entity.TimecardReq;
import com.project.coalba.domain.workspace.entity.Workspace;
import com.project.coalba.global.audit.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Schedule extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    @Column(nullable = false)
    private LocalDate scheduleDate;

    @Column(nullable = false)
    private LocalTime scheduleStartTime;

    @Column(nullable = false)
    private LocalTime scheduleEndTime;

    private LocalTime logicalStartTime;

    private LocalTime logicalEndTime;

    private LocalTime physicalStartTime;

    private LocalTime physicalEndTime;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScheduleStatus status = ScheduleStatus.BEFORE_WORK;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", nullable = false)
    private Workspace workspace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    @Builder.Default
    @OneToMany(mappedBy = "schedule")
    private List<SubstituteReq> substituteReqList = new ArrayList<>();

    @OneToOne(mappedBy = "schedule")
    private TimecardReq timecardReq;

    public void mapWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public void mapStaff(Staff staff) {
        this.staff = staff;
    }
}
