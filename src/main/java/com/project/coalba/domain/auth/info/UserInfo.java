package com.project.coalba.domain.auth.info;

import com.project.coalba.domain.auth.entity.User;
import com.project.coalba.domain.auth.entity.enums.Role;

public interface UserInfo {
    User getUser(String token, Role role);
}
