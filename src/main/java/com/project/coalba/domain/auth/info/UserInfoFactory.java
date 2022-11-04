package com.project.coalba.domain.auth.info;

import com.project.coalba.domain.auth.entity.enums.Provider;
import com.project.coalba.domain.auth.info.impl.GoogleUserInfo;
import com.project.coalba.domain.auth.info.impl.NaverUserInfo;

public abstract class UserInfoFactory {

    public static UserInfo getUserInfo(Provider provider) {
        switch (provider) {
            case GOOGLE: return new GoogleUserInfo();
            case NAVER: return new NaverUserInfo();
            default: throw new IllegalArgumentException("Invalid Provider Type.");
        }
    }
}
