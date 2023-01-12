package com.project.coalba.domain.workspace.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkspaceUpdateServiceDto {
    private String name;
    private String phoneNumber;
    private String address;
    private String imageUrl;
}
