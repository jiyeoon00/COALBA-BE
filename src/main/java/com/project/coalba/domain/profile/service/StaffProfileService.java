package com.project.coalba.domain.profile.service;

import com.project.coalba.domain.auth.entity.User;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.profile.repository.StaffProfileRepository;
import com.project.coalba.domain.profile.service.dto.ProfileServiceDto;
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
    public void saveMyStaffProfile(ProfileServiceDto serviceDto) {
        User user = userUtil.getCurrentUser();
        staffProfileRepository.save(serviceDto.toStaffEntity(user));
    }

    @Transactional
    public void updateMyStaffProfile(ProfileServiceDto serviceDto) {
        Staff staff = profileUtil.getCurrentStaff();
        staff.update(serviceDto.getRealName(), serviceDto.getPhoneNumber(), serviceDto.getBirthDate(), serviceDto.getImageUrl());
    }

    public List<Staff> getStaffListInWorkspaceAndPossibleForDateTime(Long workspaceId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return staffProfileRepository.findAllByWorkspaceIdAndDateTime(workspaceId, startDateTime, endDateTime);
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
