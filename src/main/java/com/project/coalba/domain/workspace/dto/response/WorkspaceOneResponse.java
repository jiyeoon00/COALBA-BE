package com.project.coalba.domain.workspace.dto.response;

import com.project.coalba.domain.workspace.entity.Workspace;
import com.project.coalba.domain.workspace.entity.enums.PayType;
import com.project.coalba.domain.workspace.entity.enums.WorkType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter @Builder
public class WorkspaceOneResponse {

    private Long workspaceId;

    private String name;

    private String phoneNumber;

    private String address;

    private String businessNumber;

    private WorkType workType;

    private PayType payType;

    private String imageUrl;

    public static WorkspaceOneResponse create(Workspace workspace) {
        return WorkspaceOneResponse.builder()
                .workspaceId(workspace.getId())
                .name(workspace.getName())
                .phoneNumber(workspace.getPhoneNumber())
                .address(workspace.getAddress())
                .businessNumber(workspace.getBusinessNumber())
                .workType(workspace.getWorkType())
                .payType(workspace.getPayType())
                .imageUrl(workspace.getImageUrl())
                .build();
    }
}
