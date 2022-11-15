package com.project.coalba.domain.profile.service;

import com.project.coalba.domain.auth.entity.User;
import com.project.coalba.domain.profile.dto.request.ProfileRequest;
import com.project.coalba.domain.profile.dto.response.ProfileResponse;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.profile.repository.StaffProfileRepository;
import com.project.coalba.global.utils.ProfileUtil;
import com.project.coalba.global.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StaffProfileService {

    private final StaffProfileRepository staffProfileRepository;
    private final UserUtil userUtil;
    private final ProfileUtil profileUtil;

    public ProfileResponse getMyStaffProfile() {
        Staff staff = profileUtil.getCurrentStaff();
        return ProfileResponse.builder()
                .realName(staff.getRealName())
                .phoneNumber(staff.getPhoneNumber())
                .birthDate(staff.getBirthDate())
                .imageUrl(staff.getImageUrl())
                .build();
    }

    @Transactional
    public void saveMyStaffProfile(ProfileRequest profileRequest) {
        User user = userUtil.getCurrentUser();
        Staff staff = Staff.create(profileRequest.getRealName(), profileRequest.getPhoneNumber(), profileRequest.getBirthDate(), profileRequest.getImageUrl(), user);
        staffProfileRepository.save(staff);
    }

    @Transactional
    public void updateMyStaffProfile(ProfileRequest profileRequest) {
        Staff staff = profileUtil.getCurrentStaff();
        staff.update(profileRequest.getRealName(), profileRequest.getPhoneNumber(), profileRequest.getBirthDate(), profileRequest.getImageUrl());
    }

    @Transactional
    public Staff getStaffByUserEmail(String email){
        return staffProfileRepository.getStaffByUserEmail(email);
    }
}
