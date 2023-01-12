package com.project.coalba.domain.substituteReq.mapper;

import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.substituteReq.dto.response.PossibleStaffListResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SubstituteReqMapper {

    interface PossibleStaffListRef extends Supplier<List<Staff>> {}
    default PossibleStaffListResponse toDto(PossibleStaffListRef ref) {
        List<PossibleStaffListResponse.StaffResponse> staffList = ref.get().stream()
                .map(this::toSubDto)
                .collect(Collectors.toList());
        return new PossibleStaffListResponse(staffList);
    }

    @Mappings({
            @Mapping(source = "id", target = "staffId"),
            @Mapping(source = "realName", target = "name"),
            @Mapping(source = "imageUrl", target = "imageUrl")
    })
    PossibleStaffListResponse.StaffResponse toSubDto(Staff staff);
}
