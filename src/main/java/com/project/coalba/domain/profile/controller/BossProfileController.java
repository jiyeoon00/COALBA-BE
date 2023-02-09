package com.project.coalba.domain.profile.controller;

import com.project.coalba.domain.profile.dto.request.*;
import com.project.coalba.domain.profile.dto.response.*;
import com.project.coalba.domain.profile.entity.Boss;
import com.project.coalba.domain.profile.mapper.ProfileMapper;
import com.project.coalba.domain.profile.service.BossProfileService;
import com.project.coalba.global.s3.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/boss/profile")
@RestController
public class BossProfileController {
    private final BossProfileService bossProfileService;
    private final AwsS3Service awsS3Service;
    private final ProfileMapper mapper;

    @GetMapping
    public ProfileResponse getMyProfile() {
        Boss boss = bossProfileService.getMyProfile();
        return mapper.toDto(boss);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> saveMyProfile(@Validated @RequestPart("profile") ProfileCreateRequest profileCreateRequest,
                                              @RequestPart(required = false) MultipartFile imageFile) {
        String imageUrl = awsS3Service.uploadImage(imageFile);
        bossProfileService.saveMyProfile(mapper.toServiceDto(profileCreateRequest, imageUrl));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> updateMyProfile(@Validated @RequestPart("profile") ProfileUpdateRequest profileUpdateRequest,
                                                @RequestPart(required = false) MultipartFile imageFile) {
        String imageUrl = awsS3Service.replaceImage(imageFile, profileUpdateRequest.getPrevImageUrl());
        bossProfileService.updateMyProfile(mapper.toServiceDto(profileUpdateRequest, imageUrl));
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
