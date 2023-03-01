package com.project.coalba.domain.auth.controller;

import com.project.coalba.domain.auth.dto.request.*;
import com.project.coalba.domain.auth.dto.response.*;
import com.project.coalba.domain.auth.entity.enums.*;
import com.project.coalba.domain.auth.info.*;
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
        SocialInfoProvider socialInfoProvider = SocialInfoProviderFactory.getSocialInfoProvider(provider);
        SocialInfo socialInfo = socialInfoProvider.getSocialInfo(authRequest.getSocialAccessToken(), authRequest.getSocialRefreshToken()); //외부 api
        return authService.login(socialInfo, role);
    }

    @PostMapping("/refresh")
    public TokenResponse reissue(@RequestBody TokenRequest tokenRequest) {
        return authService.reissue(tokenRequest.getAccessToken(), tokenRequest.getRefreshToken());
    }
}
