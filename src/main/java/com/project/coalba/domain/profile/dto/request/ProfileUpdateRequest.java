package com.project.coalba.domain.profile.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
public class ProfileUpdateRequest {

    @NotBlank @Size(max = 50)
    private String realName;

    @NotBlank @Pattern(regexp = "010[0-9]{8}")
    private String phoneNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @NotNull @Past
    private LocalDate birthDate;

    private String imageUrl;
}
