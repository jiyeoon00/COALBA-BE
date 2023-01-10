package com.project.coalba.domain.profile.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class ProfileUpdateServiceDto {
    private String realName;

    private String phoneNumber;

    private LocalDate birthDate;

    private String imageUrl;
}
