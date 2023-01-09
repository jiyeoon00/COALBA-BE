package com.project.coalba.domain.profile.controller;

import com.project.coalba.domain.profile.dto.request.ProfileCreateRequest;
import com.project.coalba.domain.profile.dto.request.ProfileUpdateRequest;
import com.project.coalba.domain.profile.dto.response.ProfileResponse;
import com.project.coalba.domain.profile.entity.Boss;
import com.project.coalba.domain.profile.mapper.ProfileMapper;
import com.project.coalba.domain.profile.service.BossProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/boss/profile")
@RestController
public class BossProfileController {

    private final BossProfileService bossProfileService;
    private final ProfileMapper mapper;

    @GetMapping
    public ProfileResponse getMyProfile() {
        Boss boss = bossProfileService.getMyProfile();
        return mapper.toDto(boss);
    }

    @PostMapping
    public ResponseEntity<Void> saveMyProfile(@Validated @RequestBody ProfileCreateRequest profileCreateRequest) {
        bossProfileService.saveMyProfile(mapper.toServiceDto(profileCreateRequest));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateMyProfile(@Validated @RequestBody ProfileUpdateRequest profileUpdateRequest) {
        bossProfileService.updateMyProfile(mapper.toServiceDto(profileUpdateRequest));
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
