package com.project.coalba.domain.profile.service;

import com.project.coalba.domain.auth.entity.User;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.profile.repository.StaffProfileRepository;
import com.project.coalba.domain.profile.service.dto.*;
import com.project.coalba.global.exception.*;
import com.project.coalba.global.utils.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StaffProfileService {
    private final StaffProfileRepository staffProfileRepository;
    private final UserUtil userUtil;
    private final ProfileUtil profileUtil;

    @Transactional(readOnly = true)
    public Staff getMyProfile() {
        return profileUtil.getCurrentStaff();
    }

    @Transactional
    public void saveMyProfile(ProfileCreateServiceDto serviceDto) {
        User user = userUtil.getCurrentUser();
        staffProfileRepository.save(serviceDto.toStaffEntity(user));
    }

    @Transactional
    public void updateMyProfile(ProfileUpdateServiceDto serviceDto) {
        Staff staff = profileUtil.getCurrentStaff();
        staff.update(serviceDto.getRealName(), serviceDto.getPhoneNumber(), serviceDto.getBirthDate(), serviceDto.getImageUrl());
    }

    @Transactional(readOnly = true)
    public Staff getStaff(Long staffId) {
        return staffProfileRepository.findById(staffId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STAFF_PROFILE_NOT_FOUND_BY_ID));
    }

    @Transactional(readOnly = true)
    public Staff getStaffWithEmail(String email){
        return staffProfileRepository.findByUserEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.STAFF_PROFILE_NOT_FOUND_BY_EMAIL));
    }

    @Transactional(readOnly = true)
    public List<Staff> getStaffListInWorkspace(Long workspaceId) {
        return staffProfileRepository.findAllByWorkspaceId(workspaceId);
    }

    @Transactional(readOnly = true)
    public List<Staff> getStaffListInWorkspaceAndPossibleForDateTimeRange(Long workspaceId, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        return staffProfileRepository.findAllByWorkspaceIdAndDateTimeRange(workspaceId, fromDateTime, toDateTime);
    }
}
