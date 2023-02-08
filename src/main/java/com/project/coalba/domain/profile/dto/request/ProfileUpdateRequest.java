package com.project.coalba.domain.profile.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
public class ProfileUpdateRequest {

    @Valid
    private Profile profile;
    private String imageUrl;

    @Getter
    public static class Profile {

        @NotBlank @Size(max = 50)
        private String realName;

        @NotBlank @Pattern(regexp = "010[0-9]{8}")
        private String phoneNumber;

        @NotNull @Past
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private LocalDate birthDate;
    }
}
