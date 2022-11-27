package com.project.coalba.domain.profile.mapper;

import com.project.coalba.domain.profile.dto.request.ProfileRequest;
import com.project.coalba.domain.profile.dto.response.ProfileResponse;
import com.project.coalba.domain.profile.entity.Boss;
import com.project.coalba.domain.profile.entity.Staff;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    @Mappings({})
    ProfileResponse toDto(Boss boss);

    @Mappings({})
    ProfileResponse toDto(Staff staff);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "workspaceList", ignore = true),
            @Mapping(target = "substituteReqList", ignore = true),
            @Mapping(target = "timecardReqList", ignore = true),
    })
    Boss toBossEntity(ProfileRequest profileRequest);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "workspaceMemberList", ignore = true),
            @Mapping(target = "receivedSubstituteReqList", ignore = true),
            @Mapping(target = "sentSubstituteReqList", ignore = true),
    })
    Staff toStaffEntity(ProfileRequest profileRequest);
}
