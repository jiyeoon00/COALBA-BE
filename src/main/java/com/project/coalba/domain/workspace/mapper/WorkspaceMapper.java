package com.project.coalba.domain.workspace.mapper;

import com.project.coalba.domain.workspace.dto.request.WorkspaceCreateRequest;
import com.project.coalba.domain.workspace.dto.request.WorkspaceUpdateRequest;
import com.project.coalba.domain.workspace.dto.response.*;
import com.project.coalba.domain.workspace.entity.Workspace;
import com.project.coalba.domain.workspace.entity.WorkspaceMember;
import com.project.coalba.domain.workspace.service.dto.WorkspaceCreateServiceDto;
import com.project.coalba.domain.workspace.service.dto.WorkspaceUpdateServiceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface WorkspaceMapper {

    @Mappings({})
    WorkspaceCreateServiceDto toServiceDto(WorkspaceCreateRequest workspaceCreateRequest);

    @Mappings({})
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
