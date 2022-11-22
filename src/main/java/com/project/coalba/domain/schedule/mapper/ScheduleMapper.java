package com.project.coalba.domain.schedule.mapper;

import com.project.coalba.domain.schedule.dto.request.ScheduleRequest;
import com.project.coalba.domain.schedule.entity.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "logicalStartTime", ignore = true),
            @Mapping(target = "logicalEndTime", ignore = true),
            @Mapping(target = "physicalStartTime", ignore = true),
            @Mapping(target = "physicalEndTime", ignore = true),
            @Mapping(target = "status", ignore = true),
            @Mapping(target = "staff", ignore = true),
            @Mapping(target = "workspace", ignore = true),
            @Mapping(target = "substituteReqList", ignore = true),
            @Mapping(target = "timecardReq", ignore = true),
    })
    Schedule toEntity(ScheduleRequest scheduleRequest);
}
