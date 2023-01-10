package com.project.coalba.domain.workspace.service.dto;

import com.project.coalba.domain.profile.entity.Boss;
import com.project.coalba.domain.workspace.entity.Workspace;
import com.project.coalba.domain.workspace.entity.enums.PayType;
import com.project.coalba.domain.workspace.entity.enums.WorkType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkspaceCreateServiceDto {
    private String name;
    private String phoneNumber;
    private String address;
    private String businessNumber;
    private WorkType workType;
    private PayType payType;
    private String imageUrl;

    public Workspace toEntity(Boss boss) {
        return Workspace.builder()
                .name(name)
                .phoneNumber(phoneNumber)
                .address(address)
                .businessNumber(businessNumber)
                .workType(workType)
                .payType(payType)
                .imageUrl(imageUrl)
                .boss(boss)
                .build();
    }
}
