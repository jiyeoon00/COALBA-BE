package com.project.coalba.global.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DefaultImageUtil {
    private static String profileImageUrl;
    private static String workspaceImageUrl;

    private DefaultImageUtil(@Value("${profile.default.imageUrl}") String profileImageUrl,
                             @Value("${workspace.default.imageUrl}") String workspaceImageUrl) {
        DefaultImageUtil.profileImageUrl = profileImageUrl;
        DefaultImageUtil.workspaceImageUrl = workspaceImageUrl;
    }

    public static String getProfileImageUrl() {
        return profileImageUrl;
    }

    public static String getWorkspaceImageUrl() {
        return workspaceImageUrl;
    }
}
