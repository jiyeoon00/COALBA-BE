package com.project.coalba.domain.profile.repository.custom;

import com.project.coalba.domain.profile.entity.Staff;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface StaffProfileRepositoryCustom {

    List<Staff> findAllByWorkspaceIdAndDateTime(Long workspaceId, LocalDate date, LocalTime startTime, LocalTime endTime);
}
