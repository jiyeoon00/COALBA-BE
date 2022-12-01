package com.project.coalba.domain.substituteReq.repository;

import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.schedule.entity.Schedule;
import com.project.coalba.domain.substituteReq.entity.SubstituteReq;
import com.project.coalba.domain.workspace.entity.Workspace;
import lombok.*;

@Builder
@Data
public class DetailSubstituteReqDto {
    SubstituteReq substituteReq;
    Workspace workspace;
    Staff receiver;
    Staff sender;
    Schedule schedule;
}
