package com.project.coalba.domain.auth.repository;

import com.project.coalba.domain.auth.entity.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {
}
