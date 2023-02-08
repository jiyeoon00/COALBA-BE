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
            @Mapping(source = "profileCreateRequest.realName", target = "realName"),
            @Mapping(source = "profileCreateRequest.phoneNumber", target = "phoneNumber"),
            @Mapping(source = "profileCreateRequest.birthDate", target = "birthDate"),
            @Mapping(source = "imageUrl", target = "imageUrl")
    })
    ProfileCreateServiceDto toServiceDto(ProfileCreateRequest profileCreateRequest, String imageUrl);

    @Mappings({
            @Mapping(source = "profileUpdateRequest.realName", target = "realName"),
            @Mapping(source = "profileUpdateRequest.phoneNumber", target = "phoneNumber"),
            @Mapping(source = "profileUpdateRequest.birthDate", target = "birthDate"),
            @Mapping(source = "imageUrl", target = "imageUrl")
    })
    ProfileUpdateServiceDto toServiceDto(ProfileUpdateRequest profileUpdateRequest, String imageUrl);
}
