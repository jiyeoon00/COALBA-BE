package com.project.coalba.domain.auth.controller;

import com.project.coalba.domain.auth.dto.request.*;
import com.project.coalba.domain.auth.dto.response.*;
import com.project.coalba.domain.auth.entity.enums.*;
import com.project.coalba.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestParam Provider provider, @RequestParam Role role, @Validated @RequestBody AuthRequest authRequest) {
        return authService.login(provider, role, authRequest.getAccessToken(), authRequest.getRefreshToken());
    }

    @PostMapping("/refresh")
    public TokenResponse refresh(@RequestBody TokenRequest tokenRequest) {
        return authService.reissue(tokenRequest.getAccessToken(), tokenRequest.getRefreshToken());
    }
}
