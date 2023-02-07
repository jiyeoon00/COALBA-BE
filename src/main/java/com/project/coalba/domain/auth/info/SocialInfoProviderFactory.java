package com.project.coalba.domain.auth.info;

import com.project.coalba.domain.auth.entity.enums.Provider;
import com.project.coalba.domain.auth.info.impl.*;

public abstract class SocialInfoProviderFactory {
    public static SocialInfoProvider getSocialInfoProvider(Provider provider) {
        switch (provider) {
            case GOOGLE: return new GoogleInfoProvider();
            case NAVER: return new NaverInfoProvider();
            default: throw new IllegalArgumentException("Invalid Provider Type.");
        }
    }
}
