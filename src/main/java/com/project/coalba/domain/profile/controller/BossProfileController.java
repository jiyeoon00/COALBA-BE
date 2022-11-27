package com.project.coalba.domain.profile.controller;

import com.project.coalba.domain.profile.dto.request.ProfileRequest;
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
    public ProfileResponse getMyBossProfile() {
        Boss boss = bossProfileService.getMyBossProfile();
        return mapper.toDto(boss);
    }

    @PostMapping
    public ResponseEntity<Void> saveMyBossProfile(@Validated @RequestBody ProfileRequest profileRequest) {
        Boss boss = mapper.toBossEntity(profileRequest);
        bossProfileService.saveMyBossProfile(boss);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateMyBossProfile(@Validated @RequestBody ProfileRequest profileRequest) {
        bossProfileService.updateMyBossProfile(profileRequest.getRealName(), profileRequest.getPhoneNumber(),
                profileRequest.getBirthDate(), profileRequest.getImageUrl());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
