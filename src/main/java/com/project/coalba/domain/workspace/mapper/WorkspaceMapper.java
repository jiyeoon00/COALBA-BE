package com.project.coalba.domain.workspace.mapper;

import com.project.coalba.domain.workspace.dto.request.*;
import com.project.coalba.domain.workspace.dto.response.*;
import com.project.coalba.domain.workspace.entity.*;
import com.project.coalba.domain.workspace.service.dto.*;
import org.mapstruct.*;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface WorkspaceMapper {

    @Mappings({
            @Mapping(source = "workspace.name", target = "name"),
            @Mapping(source = "workspace.phoneNumber", target = "phoneNumber"),
            @Mapping(source = "workspace.address", target = "address"),
            @Mapping(source = "workspace.businessNumber", target = "businessNumber"),
            @Mapping(source = "workspace.workType", target = "workType"),
            @Mapping(source = "workspace.payType", target = "payType")
    })
    WorkspaceCreateServiceDto toServiceDto(WorkspaceCreateRequest workspaceCreateRequest);

    @Mappings({
            @Mapping(source = "workspace.name", target = "name"),
            @Mapping(source = "workspace.phoneNumber", target = "phoneNumber"),
            @Mapping(source = "workspace.address", target = "address")
    })
    WorkspaceUpdateServiceDto toServiceDto(WorkspaceUpdateRequest workspaceUpdateRequest);

    @Mappings(@Mapping(source = "id", target = "workspaceId"))
    WorkspaceOneResponse toDto(Workspace workspace);

    interface WorkspaceListRef extends Supplier<List<Workspace>> {}
    default WorkspaceListResponse toDto(WorkspaceListRef ref) {
        List<WorkspaceListResponse.WorkspaceResponse> workspaceList = ref.get().stream()
                .map(this::toSubDto)
                .collect(Collectors.toList());
        return new WorkspaceListResponse(workspaceList);
    }

    @Mappings(@Mapping(source = "id", target = "workspaceId"))
    WorkspaceListResponse.WorkspaceResponse toSubDto(Workspace workspace);

    default WorkspaceBriefListResponse toBriefDto(WorkspaceListRef ref) {
        List<WorkspaceBriefListResponse.WorkspaceResponse> workspaceList = ref.get().stream()
                .map(this::toBriefSubDto)
                .collect(Collectors.toList());
        return new WorkspaceBriefListResponse(workspaceList);
    }

    @Mappings(@Mapping(source = "id", target = "workspaceId"))
    WorkspaceBriefListResponse.WorkspaceResponse toBriefSubDto(Workspace workspace);

    interface WorkspaceMemberListRef extends Supplier<List<WorkspaceMember>> {}
    default WorkspaceMemberInfoListResponse toDto(WorkspaceMemberListRef ref) {
        List<WorkspaceMemberInfoListResponse.InfoResponse> staffInfoList = ref.get().stream()
                .map(this::toSubDto)
                .collect(Collectors.toList());
        return new WorkspaceMemberInfoListResponse(staffInfoList);
    }

    @Mappings({
            @Mapping(source = "staff.id", target = "staffId"),
            @Mapping(source = "staff.realName", target = "name"),
            @Mapping(source = "staff.phoneNumber", target = "phoneNumber"),
            @Mapping(source = "staff.birthDate", target = "birthDate"),
            @Mapping(source = "staff.imageUrl", target = "imageUrl")
    })
    WorkspaceMemberInfoListResponse.InfoResponse toSubDto(WorkspaceMember workspaceMember);
}
