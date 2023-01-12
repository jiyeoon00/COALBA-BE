package com.project.coalba.domain.profile.repository;

import com.project.coalba.domain.profile.entity.Boss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BossProfileRepository extends JpaRepository<Boss, Long> {

    @Query("select b from Boss b where b.user.id = :userId")
    Optional<Boss> findByUserId(@Param("userId") Long userId);

    @Query("select b from Boss b inner join Workspace w on b.id = w.boss.id where w.id = :workspaceId")
    Optional<Boss> findByWorkspaceId(@Param("workspaceId") Long workspaceId);
}
