package com.project.coalba.domain.schedule.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StaffWorkspaceDateResponse {

    private Integer day;

    private Boolean isMySchedule;
}
