package com.project.coalba.domain.profile.controller;

import com.project.coalba.domain.profile.dto.request.ProfileRequest;
import com.project.coalba.domain.profile.dto.response.ProfileResponse;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.profile.mapper.ProfileMapper;
import com.project.coalba.domain.profile.service.StaffProfileService;
import com.project.coalba.domain.profile.service.dto.ProfileServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/staff/profile")
@RestController
public class StaffProfileController {

    private final StaffProfileService staffProfileService;
    private final ProfileMapper mapper;

    @GetMapping
    public ProfileResponse getMyStaffProfile() {
        Staff staff = staffProfileService.getMyStaffProfile();
        return mapper.toDto(staff);
    }

    @PostMapping
    public ResponseEntity<Void> saveMyStaffProfile(@Validated @RequestBody ProfileRequest profileRequest) {
        ProfileServiceDto serviceDto = mapper.toServiceDto(profileRequest);
        staffProfileService.saveMyStaffProfile(serviceDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateMyStaffProfile(@Validated @RequestBody ProfileRequest profileRequest) {
        ProfileServiceDto serviceDto = mapper.toServiceDto(profileRequest);
        staffProfileService.updateMyStaffProfile(serviceDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
