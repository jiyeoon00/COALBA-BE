package com.project.coalba.domain.auth.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    STAFF("ROLE_STAFF", "알바"), BOSS("ROLE_BOSS", "사장");

    private final String code;
    private final String name;
}
