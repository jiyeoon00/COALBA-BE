package com.project.coalba.domain.profile.repository;

import com.project.coalba.domain.profile.entity.Boss;
import com.project.coalba.domain.profile.repository.custom.BossProfileRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BossProfileRepository extends JpaRepository<Boss, Long>, BossProfileRepositoryCustom {

    @Query("select b from Boss b where b.user.id = :userId")
    Optional<Boss> findByUserId(@Param("userId") Long userId);
}
