package com.project.coalba.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StaffWorkspaceDateResponse {

    private int day;

    private Boolean isMySchedule;
}
