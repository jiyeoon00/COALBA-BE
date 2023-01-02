package com.project.coalba.domain.substituteReq.repository.dto;

import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.substituteReq.entity.SubstituteReq;
import com.project.coalba.domain.workspace.entity.Workspace;
import lombok.Data;

@Data
public class SubstituteReqDto {
    SubstituteReq substituteReq;
    Workspace workspace;
    Staff staff;
    Schedule schedule;
}
