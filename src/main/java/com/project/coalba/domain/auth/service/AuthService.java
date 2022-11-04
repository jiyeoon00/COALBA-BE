package com.project.coalba.domain.auth.service;

import com.project.coalba.domain.auth.repository.UserRefreshTokenRepository;
import com.project.coalba.domain.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    @Transactional
    public Object login() {
        //TODO: 구글 or 네이버로부터 받아온 사용자 정보 저장 or 업데이트 후 token 발행
        return new Object();
    }

    @Transactional
    public Object reissue() {
        //TODO: refresh token 유효성 검사 후 token 재발행
        return new Object();
    }
}
