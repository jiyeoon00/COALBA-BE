package com.project.coalba.domain.auth.controller;

import com.project.coalba.domain.auth.entity.User;
import com.project.coalba.domain.auth.token.UserPrincipal;
import com.project.coalba.domain.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/userinfo")
@RestController
public class UserController {
    private final UserRepository userRepository;

    //token 발급 후 테스트용 API (나중에 삭제할 예정)
    @GetMapping
    public User getUserinfo(@AuthenticationPrincipal UserPrincipal principal) {
        return userRepository.findById(principal.getUserId())
                .orElseThrow(() -> new RuntimeException("유저 정보 없음"));
    }
}
