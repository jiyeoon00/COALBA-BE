package com.project.coalba.domain.profile.repository.custom;

import com.project.coalba.domain.profile.entity.Staff;

import java.time.LocalDateTime;
import java.util.List;

public interface StaffProfileRepositoryCustom {

    List<Staff> findAllByWorkspaceIdAndDateTime(Long workspaceId, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
