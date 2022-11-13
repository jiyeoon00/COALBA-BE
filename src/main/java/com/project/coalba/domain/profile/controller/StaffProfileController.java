package com.project.coalba.domain.profile.controller;

import com.project.coalba.domain.profile.dto.request.ProfileRequest;
import com.project.coalba.domain.profile.dto.response.ProfileResponse;
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

    @GetMapping
    public ProfileResponse getMyStaffProfile() {
        return staffProfileService.getMyStaffProfile();
    }

    @PostMapping
    public ResponseEntity<Void> saveMyStaffProfile(@Validated @RequestBody ProfileRequest profileRequest) {
        staffProfileService.saveMyStaffProfile(profileRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateMyStaffProfile(@Validated @RequestBody ProfileRequest profileRequest) {
        staffProfileService.updateMyStaffProfile(profileRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
