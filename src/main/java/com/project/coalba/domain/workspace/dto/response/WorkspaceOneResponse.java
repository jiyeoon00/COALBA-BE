package com.project.coalba.domain.workspace.dto.response;

import com.project.coalba.domain.workspace.entity.enums.PayType;
import com.project.coalba.domain.workspace.entity.enums.WorkType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkspaceOneResponse {
    private Long workspaceId;
    private String name;
    private String phoneNumber;
    private String address;
    private String businessNumber;
    private WorkType workType;
    private PayType payType;
    private String imageUrl;
}
