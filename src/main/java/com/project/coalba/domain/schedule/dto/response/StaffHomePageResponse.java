package com.project.coalba.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class StaffHomePageResponse {
    private List<HomeDateResponse> dateList;
    private StaffHomeSelectedSubPageResponse selectedScheduleListOfDate;
}
