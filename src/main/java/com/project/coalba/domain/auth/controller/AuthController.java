package com.project.coalba.domain.auth.controller;

import com.project.coalba.domain.auth.dto.request.TokenRequest;
import com.project.coalba.domain.auth.dto.response.AuthResponse;
import com.project.coalba.domain.auth.dto.response.TokenResponse;
import com.project.coalba.domain.auth.entity.enums.Provider;
import com.project.coalba.domain.auth.entity.enums.Role;
import com.project.coalba.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestParam Provider provider, @RequestParam String token, @RequestParam Role role) {
        return authService.login(provider, token, role);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@RequestBody TokenRequest tokenRequest) {
        TokenResponse tokenResponse = authService.reissue(tokenRequest.getAccessToken(), tokenRequest.getRefreshToken());
        HttpStatus status = (tokenResponse == null) ? HttpStatus.UNAUTHORIZED : HttpStatus.OK;
        return ResponseEntity.status(status).body(tokenResponse);
    }
}
