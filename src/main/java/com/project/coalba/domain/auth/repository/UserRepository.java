package com.project.coalba.domain.auth.repository;

import com.project.coalba.domain.auth.entity.User;
import com.project.coalba.domain.auth.entity.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    Optional<User> findByProviderIdAndRole(String providerId, Role role);
}
