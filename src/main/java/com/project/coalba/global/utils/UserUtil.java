package com.project.coalba.global.utils;

import com.project.coalba.domain.auth.entity.User;
import com.project.coalba.domain.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserUtil {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        return userRepository.findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 이용자입니다."));
    }
}
