package com.project.coalba.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class StaffHomePageResponse {
    private List<HomeDateResponse> dateList;

    private StaffHomeScheduleListResponse selectedScheduleListOfDate;
}
