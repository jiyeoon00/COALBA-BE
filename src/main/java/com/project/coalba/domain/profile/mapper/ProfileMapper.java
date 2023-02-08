package com.project.coalba.domain.profile.mapper;

import com.project.coalba.domain.profile.dto.request.*;
import com.project.coalba.domain.profile.dto.response.*;
import com.project.coalba.domain.profile.entity.*;
import com.project.coalba.domain.profile.service.dto.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    @Mappings({})
    ProfileResponse toDto(Boss boss);

    @Mappings({})
    ProfileResponse toDto(Staff staff);

    @Mappings({
            @Mapping(source = "profile.realName", target = "realName"),
            @Mapping(source = "profile.phoneNumber", target = "phoneNumber"),
            @Mapping(source = "profile.birthDate", target = "birthDate")
    })
    ProfileCreateServiceDto toServiceDto(ProfileCreateRequest profileCreateRequest);

    @Mappings({
            @Mapping(source = "profile.realName", target = "realName"),
            @Mapping(source = "profile.phoneNumber", target = "phoneNumber"),
            @Mapping(source = "profile.birthDate", target = "birthDate")
    })
    ProfileUpdateServiceDto toServiceDto(ProfileUpdateRequest profileUpdateRequest);
}
