package com.project.coalba.domain.profile.repository;

import com.project.coalba.domain.profile.entity.Boss;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BossProfileRepository extends JpaRepository<Boss, Long> {
}
