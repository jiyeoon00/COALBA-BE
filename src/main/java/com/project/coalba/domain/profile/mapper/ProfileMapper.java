package com.project.coalba.domain.profile.mapper;

import com.project.coalba.domain.profile.dto.request.ProfileRequest;
import com.project.coalba.domain.profile.dto.response.ProfileResponse;
import com.project.coalba.domain.profile.entity.Boss;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.profile.service.dto.ProfileServiceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    @Mappings({})
    ProfileResponse toDto(Boss boss);

    @Mappings({})
    ProfileResponse toDto(Staff staff);

    @Mappings({})
    ProfileServiceDto toServiceDto(ProfileRequest profileRequest);
}
