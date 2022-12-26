package com.project.coalba.domain.profile.mapper;

import com.project.coalba.domain.profile.dto.request.ProfileCreateRequest;
import com.project.coalba.domain.profile.dto.request.ProfileUpdateRequest;
import com.project.coalba.domain.profile.dto.response.ProfileResponse;
import com.project.coalba.domain.profile.entity.Boss;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.profile.service.dto.ProfileCreateServiceDto;
import com.project.coalba.domain.profile.service.dto.ProfileUpdateServiceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    @Mappings({})
    ProfileResponse toDto(Boss boss);

    @Mappings({})
    ProfileResponse toDto(Staff staff);

    @Mappings({})
    ProfileCreateServiceDto toServiceDto(ProfileCreateRequest profileCreateRequest);

    @Mappings({})
    ProfileUpdateServiceDto toServiceDto(ProfileUpdateRequest profileUpdateRequest);
}
