package com.project.coalba.domain.profile.service.dto;

import com.project.coalba.domain.auth.entity.User;
import com.project.coalba.domain.profile.entity.Boss;
import com.project.coalba.domain.profile.entity.Staff;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class ProfileServiceDto {

    private String realName;

    private String phoneNumber;

    private LocalDate birthDate;

    private String imageUrl;

    public Boss toBossEntity(User user) {
        return Boss.builder()
                .realName(realName)
                .phoneNumber(phoneNumber)
                .birthDate(birthDate)
                .imageUrl(imageUrl)
                .user(user)
                .build();
    }

    public Staff toStaffEntity(User user) {
        return Staff.builder()
                .realName(realName)
                .phoneNumber(phoneNumber)
                .birthDate(birthDate)
                .imageUrl(imageUrl)
                .user(user)
                .build();
    }
}
