package com.project.coalba.domain.auth.service;

import com.project.coalba.domain.auth.entity.User;
import com.project.coalba.domain.auth.entity.enums.Provider;
import com.project.coalba.domain.auth.entity.enums.Role;
import com.project.coalba.domain.auth.info.UserInfo;
import com.project.coalba.domain.auth.info.UserInfoFactory;
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
    public Object login(Provider provider, String token, Role role) {
        User socialUser = getSocialUser(provider, token, role), loginUser;
        String providerId = socialUser.getProviderId();
        boolean isNewUser;
        if (isSubscribedUser(providerId)) {
            loginUser = updateUser(socialUser);
            isNewUser = false;
        }
        else {
             loginUser = joinUser(socialUser);
             isNewUser = true;
        }

        //TODO: token 발행 후 DTO 생성해서 반환
        return new Object();
    }

    @Transactional
    public Object reissue() {
        //TODO: refresh token 유효성 검사 후 token 재발행
        return new Object();
    }

    private User getSocialUser(Provider provider, String token, Role role) {
        UserInfo userInfo = UserInfoFactory.getUserInfo(provider);
        return userInfo.getUser(token, role);
    }

    private boolean isSubscribedUser(String providerId) {
        return userRepository.findByProviderId(providerId).isPresent(); //영속성 컨텍스트에서 관리X
    }

    private User updateUser(User socialUser) {
        User loginUser = userRepository.findByProviderId(socialUser.getProviderId()).get();
        loginUser.updateSocialInfo(socialUser);
        return loginUser;
    }

    private User joinUser(User socialUser) {
        return userRepository.save(socialUser);
    }
}
