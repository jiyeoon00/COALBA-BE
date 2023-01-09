package com.project.coalba.domain.profile.service;

import com.project.coalba.domain.auth.entity.User;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.profile.repository.StaffProfileRepository;
import com.project.coalba.domain.profile.service.dto.ProfileCreateServiceDto;
import com.project.coalba.domain.profile.service.dto.ProfileUpdateServiceDto;
import com.project.coalba.global.utils.ProfileUtil;
import com.project.coalba.global.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class StaffProfileService {

    private final StaffProfileRepository staffProfileRepository;
    private final UserUtil userUtil;
    private final ProfileUtil profileUtil;

    public Staff getMyStaffProfile() {
        return profileUtil.getCurrentStaff();
    }

    @Transactional
    public void saveMyStaffProfile(ProfileCreateServiceDto serviceDto) {
        User user = userUtil.getCurrentUser();
        staffProfileRepository.save(serviceDto.toStaffEntity(user));
    }

    @Transactional
    public void updateMyStaffProfile(ProfileUpdateServiceDto serviceDto) {
        Staff staff = profileUtil.getCurrentStaff();
        staff.update(serviceDto.getRealName(), serviceDto.getPhoneNumber(), serviceDto.getBirthDate(), serviceDto.getImageUrl());
    }

    public List<Staff> getStaffListInWorkspaceAndPossibleForDateTimeRange(Long workspaceId, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        return staffProfileRepository.findAllByWorkspaceIdAndDateTimeRange(workspaceId, fromDateTime, toDateTime);
    }

    public List<Staff> getStaffListInWorkspace(Long workspaceId) {
        return staffProfileRepository.findAllByWorkspaceId(workspaceId);
    }

    public Staff getStaffByUserEmail(String email){
        return staffProfileRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("해당 이용자의 프로필이 존재하지 않습니다."));
    }

    public Staff getStaff(Long staffId) {
        return staffProfileRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("해당 이용자의 프로필이 존재하지 않습니다."));
    }
}
