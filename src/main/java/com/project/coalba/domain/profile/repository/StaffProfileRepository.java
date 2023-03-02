package com.project.coalba.domain.profile.repository;

import com.project.coalba.domain.profile.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.*;

public interface StaffProfileRepository extends JpaRepository<Staff, Long>, StaffProfileRepositoryCustom {

    @Query("select s from Staff s where s.user.id = :userId")
    Optional<Staff> findByUserId(@Param("userId") Long userId);

    @Query("select s from Staff s join s.user u on u.email = :email")
    Optional<Staff> findByUserEmail(@Param("email") String email);

    @Query("select s from Staff s inner join WorkspaceMember wm on s.id = wm.staff.id where wm.workspace.id = :workspaceId order by s.realName asc, s.id asc")
    List<Staff> findAllByWorkspaceId(@Param("workspaceId") Long workspaceId);

    @Query("select s from Staff s inner join WorkspaceMember wm on s.id = wm.staff.id where wm.workspace.id = :workspaceId and wm.createdDate < :dateTime order by s.realName asc, s.id asc")
    List<Staff> findAllByWorkspaceIdAndBeforeDateTime(@Param("workspaceId") Long workspaceId, @Param("dateTime") LocalDateTime dateTime);
}
