package com.project.coalba.domain.workspace.entity;

import com.project.coalba.domain.profile.entity.Boss;
import com.project.coalba.domain.workspace.dto.request.WorkspaceRequest;
import com.project.coalba.domain.workspace.dto.request.WorkspaceUpdateRequest;
import com.project.coalba.domain.workspace.entity.enums.PayType;
import com.project.coalba.domain.workspace.entity.enums.WorkType;
import com.project.coalba.global.audit.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Workspace extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workspace_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 11)
    private String phoneNumber;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, unique = true, length = 10)
    private String businessNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkType workType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PayType payType;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boss_id")
    private Boss boss;

    @Builder.Default
    @OneToMany(mappedBy = "workspace")
    private List<WorkspaceMember> workspaceMemberList = new ArrayList<>();

    public static Workspace create(WorkspaceRequest workspaceRequest, Boss boss) {
        return Workspace.builder()
                .name(workspaceRequest.getName())
                .phoneNumber(workspaceRequest.getPhoneNumber())
                .address(workspaceRequest.getAddress())
                .businessNumber(workspaceRequest.getBusinessNumber())
                .workType(workspaceRequest.getWorkType())
                .payType(workspaceRequest.getPayType())
                .imageUrl(workspaceRequest.getImageUrl())
                .boss(boss)
                .build();
    }

    public void update(WorkspaceUpdateRequest workspaceUpdateRequest) {
        this.name = workspaceUpdateRequest.getName();
        this.phoneNumber = workspaceUpdateRequest.getPhoneNumber();
        this.address = workspaceUpdateRequest.getAddress();
        this.imageUrl = workspaceUpdateRequest.getImageUrl();
    }
}
