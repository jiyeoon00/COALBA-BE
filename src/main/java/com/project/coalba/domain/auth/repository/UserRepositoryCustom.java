package com.project.coalba.domain.auth.repository;

public interface UserRepositoryCustom {
    Boolean existsByProfile(Long userId);
}
