package com.project.coalba.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleUserInfoDto {
    private String sub;
    private String email;
    private String name;
    private String picture;
}
