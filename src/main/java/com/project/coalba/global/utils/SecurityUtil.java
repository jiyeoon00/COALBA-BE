package com.project.coalba.global.utils;

import com.project.coalba.domain.auth.token.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    private SecurityUtil() {}

    public static Long getCurrentUserId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new RuntimeException("Security Context에 인증 정보가 없습니다.");
        }
        return ((UserPrincipal) authentication.getPrincipal()).getUserId();
    }
}
