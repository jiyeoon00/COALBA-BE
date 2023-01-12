package com.project.coalba.domain.profile.controller;

import com.project.coalba.domain.profile.dto.request.ProfileCreateRequest;
import com.project.coalba.domain.profile.dto.request.ProfileUpdateRequest;
import com.project.coalba.domain.profile.dto.response.ProfileResponse;
import com.project.coalba.domain.profile.entity.Staff;
import com.project.coalba.domain.profile.mapper.ProfileMapper;
import com.project.coalba.domain.profile.service.StaffProfileService;
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
    public ProfileResponse getMyProfile() {
        Staff staff = staffProfileService.getMyProfile();
        return mapper.toDto(staff);
    }

    @PostMapping
    public ResponseEntity<Void> saveMyProfile(@Validated @RequestBody ProfileCreateRequest profileCreateRequest) {
        staffProfileService.saveMyProfile(mapper.toServiceDto(profileCreateRequest));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateMyProfile(@Validated @RequestBody ProfileUpdateRequest profileUpdateRequest) {
        staffProfileService.updateMyProfile(mapper.toServiceDto(profileUpdateRequest));
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
