package com.project.coalba.domain.auth.info.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleInfo {
    private String sub;
    private String email;
    private String name;
    private String picture;
}
