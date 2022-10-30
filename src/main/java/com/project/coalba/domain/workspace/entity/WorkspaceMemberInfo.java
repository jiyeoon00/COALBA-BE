package com.project.coalba.domain.workspace.entity;

import com.project.coalba.domain.workspace.entity.enums.Position;
import com.project.coalba.global.audit.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.YearMonth;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class WorkspaceMemberInfo extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workspace_member_info_id")
    private Long id;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Position position = Position.ALBA;

    @ColumnDefault("9160")
    @Column(nullable = false)
    private Integer hourlyWage;

    @Column(nullable = false)
    private YearMonth appliedStartDate;

    private YearMonth appliedEndDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_member_id")
    private WorkspaceMember workspaceMember;
}
