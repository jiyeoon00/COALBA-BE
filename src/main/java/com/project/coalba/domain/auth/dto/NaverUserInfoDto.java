package com.project.coalba.domain.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverUserInfoDto {
    private Response response;

    @Getter
    public static class Response {

        private String id;
        private String email;
        private String name;
        @JsonProperty(value = "profile_image")
        private String profileImage;
    }
}

